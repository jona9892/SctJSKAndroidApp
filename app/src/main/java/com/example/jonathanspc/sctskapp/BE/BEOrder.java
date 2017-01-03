package com.example.jonathanspc.sctskapp.BE;

import java.io.Serializable;

/**
 * Created by JonathansPC on 28-12-2016.
 */
public class BEOrder implements Serializable {
    private int Id = 0;
    private int UserId;
    private String OrderCreated;
    private String OrderDate;
    private int TotalPrice;
    private String TimeOfDay;
    public static int IDENTITY = 1;

    public BEOrder(int id, int userid, String ordercreated, String orderdate, int totalprice, String timofday){
        this.Id = id;
        this.UserId = userid;
        this.OrderCreated = ordercreated;
        this.OrderDate = orderdate;
        this.TotalPrice = totalprice;
        this.TimeOfDay = timofday;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getOrderCreated() {
        return OrderCreated;
    }

    public void setOrderCreated(String orderCreated) {
        OrderCreated = orderCreated;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getTimeOfDay() {
        return TimeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        TimeOfDay = timeOfDay;
    }
}
