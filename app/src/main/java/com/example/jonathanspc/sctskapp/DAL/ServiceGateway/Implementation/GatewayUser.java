package com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Implementation;

import com.example.jonathanspc.sctskapp.BE.BEUser;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Abstraction.IGatewayUser;

import java.util.ArrayList;

/**
 * Created by JonathansPC on 29-12-2016.
 */
public class GatewayUser implements IGatewayUser {
    private static GatewayUser instance;
    private ArrayList<BEUser> users;

    public static GatewayUser getInstance(){
        if(instance == null)
            instance = new GatewayUser();
        return instance;
    }

    private GatewayUser(){
        users = new ArrayList<BEUser>();
        users.add(new BEUser(1, "Jonathan", "Gjøl", "JonathanEmail@hotmail.dk", "jona1234", "123456"));

    }

    @Override
    public BEUser Login(String username, String password) {
        for(BEUser user : users){
            if(username.toUpperCase().equals(user.getUsername().toUpperCase())&& password.equals(user.getPassword())){
                return user;
            }
        }
        return null;
    }
}
