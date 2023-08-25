package com.example.fluxdemo.Service;

import com.example.fluxdemo.DAO.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUserId(int userid) {
        User user = new User(userid,"John","Doe");
        return user;
    }
    @Override
    public Mono<User> getUserByUserIdReactive(int userid) {
        Mono<User> user = Mono.fromCallable(() -> new User(userid,"John","Doe"));
        return user;
    }
}
