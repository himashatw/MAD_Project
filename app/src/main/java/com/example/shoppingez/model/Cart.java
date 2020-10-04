package com.example.shoppingez.model;

public class Cart {
    private String pname,pquantity;
    private double Pprice;

    public Cart(){

    }

    public Cart(double price, String pname, String pquantity) {
        this.Pprice = price;
        this.pname = pname;
        this.pquantity = pquantity;
    }

    public double getPrice() {
        return Pprice;
    }

    public void setPrice(double price) {
        this.Pprice = price;
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
