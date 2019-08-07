package com.example.travelmantics;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Travel {
    private String image;
    private String location;
    private String name;
    private int price;

    public Travel(){

    }
    public Travel(String image, String location, String name, int price) {
        this.image = image;
        this.location = location;
        this.name = name;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
