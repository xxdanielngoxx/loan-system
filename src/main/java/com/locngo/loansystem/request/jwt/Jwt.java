package com.locngo.loansystem.request.jwt;

public class Jwt {

    private String token;

    private Jwt(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public static Jwt of(String token) {
        return new Jwt(token);
    }
}
