package com.seriesmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    private String userUUID;
    private String username;
    private String password;
    private List<Boolean> roles;

    //private String email;

    //private String auth;

    public User(String username, String password, boolean read, boolean write, boolean edit) {
        this.userUUID = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();
        this.roles.add(read);
        this.roles.add(write);
        this.roles.add(edit);
    }

    public User() {

    }

    public String getUserUUID() {
        return userUUID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Boolean> getRoles() {
        return roles;
    }
    @JsonIgnore
    public boolean getRole(int index) {
        return this.roles.get(index);
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<Boolean> roles) {
        this.roles = roles;
    }

    @JsonIgnore
    public void setRole(boolean role, int index) {
        this.roles.set(index, role);
    }



    /*public String getAuth() {return auth;}

    public void setAuth(String auth) {this.auth = auth;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}*/
}
