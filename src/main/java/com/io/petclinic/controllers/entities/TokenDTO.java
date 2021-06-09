package com.io.petclinic.controllers.entities;

public class TokenDTO {
    private String token;
    private String type;
    private Long userId;

    public TokenDTO(String type, Long userId) {
        this.type = type;
        this.userId = userId;
    }

    public TokenDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
