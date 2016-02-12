package com.example.sliding.mainview.Beans;

import java.io.Serializable;

/**
 * Created by huehn on 16/2/11.
 */
public class Item implements Serializable {
    private int itemId;
    private String itemName;
    private int tableId;
    private int foodId;
    private int foodNum;
    private int foodPrice;
    private String price;
    private String itemRemark;

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int food_id) {
        this.foodId = food_id;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int food_num) {
        this.foodNum = food_num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemRemark() {
        return itemRemark;
    }

    public void setItemRemark(String itemRemark) {
        this.itemRemark = itemRemark;
    }
}
