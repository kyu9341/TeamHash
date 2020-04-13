package com.springboot.thymeleaf.controller;

public class User {
    public String userId;
    public String password;
    public String name;
    public String email;

   

    public void setUserId(String userId) {
        this.userId = userId;
    }

  

    public void setPassword(String password) {
        this.password = password;
    }

  
    public void setName(String name) {
        this.name = name;
    }

  
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", name=" + name + ", password=" + password + ", userId=" + userId + "]";
    }

    
}