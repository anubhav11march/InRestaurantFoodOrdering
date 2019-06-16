package com.anubhav11march.clientapp;

public class Food {

    String name , desc, price, image;

    public Food(){

    }

    public Food(String name, String desc, String price, String image){
        this.image = image;
        this.name = name;
        this.desc = desc;
        this.price = price;

    }

    public String getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
