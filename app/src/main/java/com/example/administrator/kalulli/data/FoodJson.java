package com.example.administrator.kalulli.data;

/**
 * Created by Administrator on 2019/4/1.
 */

public class FoodJson {
    private String foodname;
    private String number;
    private String picture_url;
    private String description;

    public FoodJson(String foodname, String number, String picture_url, String description) {
        this.foodname = foodname;
        this.number = number;
        this.picture_url = picture_url;
        this.description = description;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
