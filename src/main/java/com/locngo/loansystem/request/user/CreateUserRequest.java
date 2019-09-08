package com.locngo.loansystem.request.user;

import com.locngo.loansystem.model.BaseEntity;
import com.locngo.loansystem.model.Status;

public class CreateUserRequest {

    private String username;

    private String password;

    private BaseEntity baseEntity;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BaseEntity getBaseEntity() {
        return baseEntity;
    }

    public void setBaseEntity(BaseEntity baseEntity) {
        this.baseEntity = baseEntity;
    }
}
