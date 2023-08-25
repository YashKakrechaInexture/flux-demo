package com.example.fluxdemo.Controller;

import com.example.fluxdemo.DAO.Address;
import com.example.fluxdemo.DAO.User;
import com.example.fluxdemo.Service.AddressService;
import com.example.fluxdemo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
public class FrontController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    int storage = 10;
    AtomicInteger count = new AtomicInteger(1);
    boolean running = false;
    Flux<Integer> flux;
    Disposable subscription;

    @GetMapping("/")
    public String dispense() {
        if (running) {
            running = false;
            subscription.dispose();
        } else {
            running = true;
            flux = Flux.range(count.get(), storage - count.get() + 1)
                    .delayElements(Duration.ofSeconds(1))
                    .doOnCancel(() -> System.out.println("Drink Stopped."))
                    .doOnComplete(() -> {
                        System.out.println("Drink Out of Stock.");
                        running = false;
                    });
            subscription = flux.subscribe(i -> {
                        System.out.println("Received: " + i + "L");
                        count.incrementAndGet();
                    }, e -> System.err.println("Error: " + e.getMessage())
            );
        }

        return "Running: " + running;
    }

    @GetMapping("/dispense")
    public Flux<Integer> dispenseOutput() {
        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1))
                .doOnCancel(() -> System.out.println("Streaming Canceled."))
                .doOnComplete(() -> System.out.println("Completed Streaming."));
    }

    @GetMapping(value = "/message", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> dispenseMessage() {
        String paragraph = "This is a paragraph, sending each string at a fixed amount of time similar to chatgpt.";
        String[] strings = paragraph.split(" ");
        Flux<String> wordFlux = Flux.fromArray(strings);
        return wordFlux.delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/user/{userid}")
    public User getUser(@PathVariable int userid) {
        User user = userService.getUserByUserId(userid);
        Address address = addressService.getAddressByUserId(userid);
        user.setAddress(address);
        return user;
    }

    @GetMapping("/userReactive/{userid}")
    public Mono<User> getUserReactive(@PathVariable int userid) throws InterruptedException {
        return Mono.zip(userService.getUserByUserIdReactive(userid), addressService.getAddressByUserIdReactive(userid))
                .map(tuple -> {
                    User user = tuple.getT1();
                    Address address = tuple.getT2();
                    user.setAddress(address);
                    return user;
                });
    }
}