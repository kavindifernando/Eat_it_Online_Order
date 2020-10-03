package com.example.eat_it.Model;

public class CartList {
    private String  fid;
    private String  fname;
    private String  price;

    private String  quantity;
private String imageCart;
    public CartList(){

    }

    public CartList(String fid, String fname, String price, String quantity,String imageCart) {
        this.fid = fid;
        this.fname = fname;
        this.price = price;
        this.quantity = quantity;
        this.imageCart= imageCart;
    }

    public String getImageCart() {
        return imageCart;
    }

    public void setImageCart(String imageCart) {
        this.imageCart = imageCart;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
