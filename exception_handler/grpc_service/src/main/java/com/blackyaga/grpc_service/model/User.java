package com.blackyaga.grpc_service.model;


public class User {
    private String username;
    private String password;

    public User() {
        this.generator();
    }

    private void generator(){
        this.username="username";
        this.password="password";
    }

    public boolean check(String username,String password){
        if (!this.password.equals(password))
            return false;
        if (!this.username.equals(username))
            return false;
        return true;
    }

}
