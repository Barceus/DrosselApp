package com.example.drosselapp;

public class LaundryItem {
    private int id;
    private int status;

    public LaundryItem(){

    }

    public LaundryItem(int id, int status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
