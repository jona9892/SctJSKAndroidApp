package com.example.jonathanspc.sctskapp.BE;

import java.io.Serializable;

/**
 * Created by JonathansPC on 28-12-2016.
 */
public class BEOrderDetail implements Serializable {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private int price;

    public BEOrderDetail(){

    }
    public BEOrderDetail(int id, int orderid, int productid, int quantity, int price){
        this.id = id;
        this.orderId = orderid;
        this.productId = productid;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
