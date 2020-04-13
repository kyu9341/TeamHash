package net.sllipp.controller;

public class User {
    String userId;
    String password;
    String name;
    String email;

    public String getUserId() {
        return userId;
    }


    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}