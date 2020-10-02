package com.example.eat_it.Model;

public class Driver {
    private String Phone, Email, Password,Name;

    public Driver() {
    }

    public Driver(String phone, String email, String password, String name) {
        Phone = phone;
        Email = email;
        Password = password;
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
