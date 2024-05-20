package org.example;

import java.net.SocketTimeoutException;

public class User {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    void displayOptions() {

    }
    void show(){
        System.out.println("name"+ this.username);
        System.out.println("password"+ this.password);
    }

}