package com.example.fluxdemo.Service;

import com.example.fluxdemo.DAO.Address;
import reactor.core.publisher.Mono;

public interface AddressService {
    Address getAddressByUserId(int userid);
    Mono<Address> getAddressByUserIdReactive(int userid);
}
