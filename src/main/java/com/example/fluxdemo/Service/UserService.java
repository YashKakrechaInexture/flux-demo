package com.example.fluxdemo.Service;

import com.example.fluxdemo.DAO.User;
import reactor.core.publisher.Mono;

public interface UserService {
    User getUserByUserId(int userid);
    Mono<User> getUserByUserIdReactive(int userid);
}
