package com.example.jonathanspc.sctskapp.DAL.DALC.Implementation;

import com.example.jonathanspc.sctskapp.BE.BEUser;
import com.example.jonathanspc.sctskapp.DAL.DALC.Abstraction.IDALCUser;

import java.util.ArrayList;

/**
 * Created by JonathansPC on 29-12-2016.
 */
public class DALCUser implements IDALCUser {
    private static DALCUser instance;
    private ArrayList<BEUser> users;

    public static DALCUser getInstance(){
        if(instance == null)
            instance = new DALCUser();
        return instance;
    }

    private DALCUser(){
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
