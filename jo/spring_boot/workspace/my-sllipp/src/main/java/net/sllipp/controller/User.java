package net.sllipp.controller;

public class User {
    String userId;
    String password;
    String name;
    String email;

   

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId(){
        return this.userId;
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