package com.example.eat_it.Model;

public class delivery_list {
    private String address;
    private String customer_Name;
    private String date;
    private String email;
    private String payment_Status;
    private String phone_Number;
    private String postal_code;
    private String status1;
    private String time;
    private String total_Amount;


    public delivery_list() {
    }

    public delivery_list(String address, String customer_Name, String date, String email, String payment_Status, String phone_Number, String postal_code, String status1, String time, String total_Amount) {
        this.address = address;
        this.customer_Name = customer_Name;
        this.date = date;
        this.email = email;
        this.payment_Status = payment_Status;
        this.phone_Number = phone_Number;
        this.postal_code = postal_code;
        this.status1 = status1;
        this.time = time;
        this.total_Amount = total_Amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomer_Name() {
        return customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        this.customer_Name = customer_Name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPayment_Status() {
        return payment_Status;
    }

    public void setPayment_Status(String payment_Status) {
        this.payment_Status = payment_Status;
    }

    public String getPhone_Number() {
        return phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        this.phone_Number = phone_Number;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotal_Amount() {
        return total_Amount;
    }

    public void setTotal_Amount(String total_Amount) {
        this.total_Amount = total_Amount;
    }
}
