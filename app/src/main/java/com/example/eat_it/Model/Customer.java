package com.example.eat_it.Model;

public class Customer {
    private String email,name,password,phone,image;
    public Customer(){

    }

    public Customer(String email, String name, String password, String phone, String image) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.image = image;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
