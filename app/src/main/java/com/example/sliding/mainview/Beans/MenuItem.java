package com.example.sliding.mainview.Beans;

import android.widget.TextView;

/**
 * 菜单内每个item
 */
public class MenuItem {

    private TextView itemNameText;
    private TextView tableNameText;
    private TextView itemIdText;
    private String itemName;
    private String tableName;
    private int itemId;

    private boolean isChoice;//是否被选中
    private int num;//若被选中，个数为多少个
    private int money;//一份菜多少钱

    public MenuItem(){
        this.isChoice = false;
        this.num = 0;
        this.money = 0;
    }

    public TextView getItemNameText() {
        return itemNameText;
    }

    public void setItemNameText(TextView itemNameText) {
        this.itemNameText = itemNameText;
    }

    public TextView getTableNameText() {
        return tableNameText;
    }

    public void setTableNameText(TextView tableNameText) {
        this.tableNameText = tableNameText;
    }

    public TextView getItemIdText() {
        return itemIdText;
    }

    public void setItemIdText(TextView itemIdText) {
        this.itemIdText = itemIdText;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
