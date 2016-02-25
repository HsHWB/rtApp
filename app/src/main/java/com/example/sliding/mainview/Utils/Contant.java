package com.example.sliding.mainview.Utils;

/**
 * Created by huehn on 16/2/12.
 */
public class Contant {
    public static String REGISTER = "http://www.hswbin.com:8080/rtService/useapi/register?name=%s&password=%s";
    public static String CHANGE_PASSWORD = "http://www.hswbin.com:8080/rtService/useapi/changePassword?name=%s&password=%s";
    public static String LOGIN = "http://www.hswbin.com:8080/rtService/useapi/login?name=%s&password=%s";
    public static String GET_EMPTY_TABLE = "http://www.hswbin.com:8080/rtService/useapi/getTableByState?tableState=%d";
    public static String GET_NOTED_TABLE = "http://www.hswbin.com:8080/rtService/useapi/getTableByState?tableState=%d";
    public static String ORDER = "http://www.hswbin.com:8080/rtService/useapi/order";
    //    public static String ORDER = "http://192.168.199.173:8080/rtService/useapi/order?order=%s";
    public static String GET_TABLES = "http://www.hswbin.com:8080/rtService/useapi/getData?table=%s";
    public static String GET_ITEMS = "http://www.hswbin.com:8080/rtService/useapi/returnItem?idtable=%d";
    public static String PAY = "http://www.hswbin.com:8080/rtService/useapi/closeTable?tableId=%d";
    public static String GET_PRICE = "http://www.hswbin.com:8080/rtService/useapi/returnPrice?tableId=%d";
    public static String INSERT_NEW_TABLES = "http://www.hswbin.com:8080/rtService/useapi/insertNewTable?tableName=%s&tableNum=%d";
    public static String DELETE_TABLES = "http://www.hswbin.com:8080/rtService/useapi/deleteTable?tableId=%d";
    public static String UPDATE_TABLES = "http://www.hswbin.com:8080/rtService/useapi/updateTable?idtable=%d&tableName=%s";
    public static String INSERT_NEW_FOOD = "http://www.hswbin.com:8080/rtService/useapi/insertNewFood?foodName=%s&foodPrice=%s";
    public static String UPDATE_FOOD = "http://www.hswbin.com:8080/rtService/useapi/updateFood?foodName=%s&foodPrice=%s&foodId=%d";
    public static String DELETE_FOOD = "http://www.hswbin.com:8080/rtService/useapi/deleteFood?foodId=%d";
}
