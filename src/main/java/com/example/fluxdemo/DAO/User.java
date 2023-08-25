package com.example.fluxdemo.DAO;

import lombok.Data;

@Data
public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private Address address;

    public User(int userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
