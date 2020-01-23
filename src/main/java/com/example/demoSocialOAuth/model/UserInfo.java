package com.example.demoSocialOAuth.model;


public class UserInfo {

   private String firstName;

   private String email;

    public UserInfo(String firstName,String email) {
        this.firstName = firstName;

        this.email = email;

    }

    public UserInfo(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
