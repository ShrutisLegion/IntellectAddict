package com.dilsecoders.projectfirebase.model;

public class UserDetails {
    private String name;

    public UserDetails() {
    }
    public UserDetails(String name){

        this.name = name;
    }
    public String getName(){
        return  name;
    }
}