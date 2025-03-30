package com.example.FoodMicroservice_DM2.Entity;

public class Food {
    private int foodId;
    private String food_name;
    private String food_description;
    private byte[] food_pic;
    private double food_price;
    private String food_category;

    public Food(int foodId, String food_name, String food_description, byte[] food_pic, double food_price, String food_category) {
        this.foodId = foodId;
        this.food_name = food_name;
        this.food_description = food_description;
        this.food_pic = food_pic;
        this.food_price = food_price;
        this.food_category = food_category;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return food_name;
    }

    public void setFoodName(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_description() {
        return food_description;
    }

    public void setFood_description(String food_description) {
        this.food_description = food_description;
    }

    public byte[] getFood_pic() {
        return food_pic;
    }

    public void setFood_pic(byte[] food_pic) {
        this.food_pic = food_pic;
    }

    public double getFood_price() {
        return food_price;
    }

    public void setFood_price(double food_price) {
        this.food_price = food_price;
    }

    public String getFood_category() {
        return food_category;
    }

    public void setFood_category(String food_category) {
        this.food_category = food_category;
    }


}
