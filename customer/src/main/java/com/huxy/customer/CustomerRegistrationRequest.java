package com.huxy.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {

}
