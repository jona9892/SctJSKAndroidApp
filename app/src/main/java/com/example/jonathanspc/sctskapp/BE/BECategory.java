package com.example.jonathanspc.sctskapp.BE;

import java.io.Serializable;

/**
 * Created by JonathansPC on 28-12-2016.
 */
public class BECategory implements Serializable {
    private int Id;
    private String Name;

    public BECategory(int id, String name){
        this.Id = id;
        this.Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
