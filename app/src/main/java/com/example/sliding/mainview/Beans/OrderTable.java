package com.example.sliding.mainview.Beans;

import java.io.Serializable;

/**
 * Created by huehn on 16/2/9.
 */
public class OrderTable implements Serializable{
    private String tableName;
    private int idtable;
    private int tableState;
    private int tableNum;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getIdtable() {
        return idtable;
    }

    public void setIdtable(int idtable) {
        this.idtable = idtable;
    }

    public int getTableState() {
        return tableState;
    }

    public void setTableState(int tableState) {
        this.tableState = tableState;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }
}
