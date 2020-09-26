package com.example.eat_it.Model;

public class Foods {
    private  String fname;
    private  String description;
    private  String price;
    private  String image;
    private  String category;
    private  String fid;
    private  String date;
    private  String time;

    public Foods(){

    }

    public Foods(String fname, String description, String price, String image, String category, String fid, String date, String time) {
        this.fname = fname;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.fid = fid;
        this.date = date;
        this.time = time;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
