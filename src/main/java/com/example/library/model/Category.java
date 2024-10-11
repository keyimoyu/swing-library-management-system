package com.example.library.model;

import lombok.Data;

@Data
public class Category {
    private int id;  // 将 id 改为 int 类型
    private String name;

    public Category(int id, String name) {  // id 改为 int 类型
        this.id = id;
        this.name = name;
    }
}
