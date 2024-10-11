package com.example.library.model;

import lombok.Data;
import lombok.Setter;

@Data
public class User {

    @Setter
    private String username;
    private String password;

    // 构造方法
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
