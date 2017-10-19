package com.example.user.lab3;

import android.app.Activity;


public class CellData extends Activity {
    private String price,item_name,type,message;
    private int item_icon_id;

    public int getId() {
        return id;
    }

    private int id;
    private Boolean flag;

    public CellData(String price, String item_name, String type, String message, int item_icon_id, int id)
    {
        this.price = price;
        this.item_name = item_name;
        this.type = type;
        this.message = message;
        this.item_icon_id = item_icon_id;
        this.flag = false;
        this.id = id;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getPrice() {
        return price;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public int getItem_icon_id() {
        return item_icon_id;
    }

    public Boolean getFlag() {
        return flag;
    }
}

