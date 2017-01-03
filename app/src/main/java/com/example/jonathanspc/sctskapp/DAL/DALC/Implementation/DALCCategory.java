package com.example.jonathanspc.sctskapp.DAL.DALC.Implementation;

import com.example.jonathanspc.sctskapp.BE.BECategory;
import com.example.jonathanspc.sctskapp.BE.BEUser;
import com.example.jonathanspc.sctskapp.DAL.DALC.Abstraction.IDALC;

import java.util.ArrayList;

/**
 * Created by JonathansPC on 30-12-2016.
 */
public class DALCCategory implements IDALC<BECategory> {

    private static DALCCategory instance;
    private ArrayList<BECategory> categories;

    public static DALCCategory getInstance(){
        if(instance == null)
            instance = new DALCCategory();
        return instance;
    }

    private DALCCategory(){
        categories = new ArrayList<BECategory>();
        categories.add(new BECategory(1,"Brød"));
        categories.add(new BECategory(2,"Drikkevarer"));
    }

    @Override
    public ArrayList<BECategory> getAll() {
        return categories;
    }

    @Override
    public BECategory getById(int id) {
        return null;
    }
}
