package com.example.admincollageapp.Model;

public class UserModel {

    String name , email, pass, key;

    public UserModel(String name, String email, String pass, String key) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.key = key;
    }

    public UserModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
