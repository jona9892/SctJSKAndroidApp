package com.example.jonathanspc.sctskapp.Proxy.ServiceGateway.Abstraction;

import java.util.ArrayList;

/**
 * Created by JonathansPC on 28-12-2016.
 */
public interface IUserGateway<T> {
    ArrayList<T> getAll();
    T Login(String username, String password);
}
