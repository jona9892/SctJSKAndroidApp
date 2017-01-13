package com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Implementation;

import com.example.jonathanspc.sctskapp.BE.BECategory;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Abstraction.IGateway;

import java.util.ArrayList;

/**
 * Created by JonathansPC on 30-12-2016.
 */
public class GatewayCategory implements IGateway<BECategory> {

    private static GatewayCategory instance;
    private ArrayList<BECategory> categories;

    public static GatewayCategory getInstance(){
        if(instance == null)
            instance = new GatewayCategory();
        return instance;
    }

    private GatewayCategory(){
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
