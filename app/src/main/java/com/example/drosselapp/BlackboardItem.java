package com.example.drosselapp;

public class BlackboardItem {
    private String name;
    private String location;
    private String smallDesc;
    private String description;
    private String type;
    private String date;
    private String price;
    private String user;
    private String phone;

    public BlackboardItem() {
    }

    public BlackboardItem(String name, String location, String smallDesc, String description, String type, String date, String price, String user, String phone) {
        this.name = name;
        this.location = location;
        this.smallDesc = smallDesc;
        this.description = description;
        this.type = type;
        this.date = date;
        this.price = price;
        this.user = user;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSmallDesc() {
        return smallDesc;
    }

    public void setSmallDesc(String smallDesc) { this.smallDesc = smallDesc; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
