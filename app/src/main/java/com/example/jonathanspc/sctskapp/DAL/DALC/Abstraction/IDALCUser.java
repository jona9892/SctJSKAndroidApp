package com.example.jonathanspc.sctskapp.DAL.DALC.Abstraction;

import com.example.jonathanspc.sctskapp.BE.BEUser;

/**
 * Created by JonathansPC on 29-12-2016.
 */
public interface IDALCUser {
    BEUser Login(String username, String password);

}
