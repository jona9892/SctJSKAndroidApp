package com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Abstraction;

import java.util.ArrayList;

/**
 * Created by JonathansPC on 31-12-2016.
 */
public interface IGatewayOrder<T> {
    T add(T t);
    T getById(int id);
    ArrayList<T> getAll();
}
