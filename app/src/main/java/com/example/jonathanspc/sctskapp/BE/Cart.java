package com.example.jonathanspc.sctskapp.BE;

/**
 * Created by JonathansPC on 30-12-2016.
 */
public class Cart {
    private int id;
    private int productId;
    private String productTitle;
    private int productPrice;
    private String productImage;
    private int quantity;

    public Cart(int Id, int productid, String producttitle, int productprice, String productimage, int Quantity){
        this.id = Id;
        this.productId = productid;
        this.setProductTitle(producttitle);
        this.setProductPrice(productprice);
        this.setProductImage(productimage);
        this.quantity = Quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
