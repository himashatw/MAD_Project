package com.example.shoppingez.model;

public class Cart {
    private String price,catogery,pname,pquantity;

    public Cart(){

    }

    public Cart(String price, String pname, String pquantity) {
        this.price = price;
        this.catogery = catogery;
        this.pname = pname;
        this.pquantity = pquantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPquantity() {
        return pquantity;
    }

    public void setPquantity(String pquantity) {
        this.pquantity = pquantity;
    }
}
