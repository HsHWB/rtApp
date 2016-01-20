package com.example.sliding.mainview.Beans;

/**
 * 菜单内每个item
 */
public class MenuItem {

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
