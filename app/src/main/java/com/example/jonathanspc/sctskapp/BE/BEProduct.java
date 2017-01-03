package com.example.jonathanspc.sctskapp.BE;

import java.io.Serializable;

/**
 * Created by JonathansPC on 28-12-2016.
 */
public class BEProduct implements Serializable {
    private int Id;
    private String Title;
    private String Description;
    private String Image;
    private int Price;
    private BECategory Category;

    public BEProduct(int id, String title, String description, String image, int price, BECategory category){
        this.Id = id;
        this.Title = title;
        this.Description = description;
        this.Image = image;
        this.Price = price;
        this.Category = category;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public BECategory getCategory() {
        return Category;
    }

    public void setCategory(BECategory category) {
        this.Category = category;
    }
}
