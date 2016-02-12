package com.example.sliding.mainview.Beans;

import java.io.Serializable;

/**
 * Created by huehn on 16/2/10.
 */
public class Food implements Serializable{

    private String foodPrice;
    private String foodName;
    private int foodId;

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }
}
