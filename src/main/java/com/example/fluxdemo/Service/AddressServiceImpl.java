package com.example.fluxdemo.Service;

import com.example.fluxdemo.DAO.Address;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
public class AddressServiceImpl implements AddressService {
    @Override
    public Address getAddressByUserId(int userid) {
        Address address = new Address("Ahmedabad","Gujarat","India");
        return address;
    }
    @Override
    public Mono<Address> getAddressByUserIdReactive(int userid) {
        Mono<Address> address = Mono.fromCallable(() -> new Address("Ahmedabad","Gujarat","India"));
        return address;
    }
}
