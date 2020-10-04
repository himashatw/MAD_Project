package com.example.shoppingez;

public class User {

    private String FirstName;
    private String LastName;
    private String Email;
    private Integer Telephone;
    private String Password;

    public User() {
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Integer getTelephone() {
        return Telephone;
    }

    public void setTelephone(Integer telephone) {
        Telephone = telephone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
