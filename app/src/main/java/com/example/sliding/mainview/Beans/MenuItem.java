package com.example.sliding.mainview.Beans;

import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

/**
 * 菜单内每个item
 */
public class MenuItem implements Serializable {

    private String itemName;//菜的名字
    private String tableName;//属于哪个台
    private int itemId;//listview中的position
    private boolean isChoice;//是否被选中
    private int itemNum;//若被选中，个数为多少个
    private int money;//一份菜多少钱

    private int foodId;
    private int tableId;

    public MenuItem(){
        this.isChoice = false;
        this.itemNum = 0;
        this.money = 0;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setIsChoice(boolean isChoice) {
        this.isChoice = isChoice;
    }


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }
}
