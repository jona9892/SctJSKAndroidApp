package com.example.jonathanspc.sctskapp.DAL.DALC.Abstraction;

import java.util.ArrayList;

/**
 * Created by JonathansPC on 03-01-2017.
 */
public interface IDALCOrderDetail<T> {
    T add(T t);
    T getById(int id);
    ArrayList<T> getAll(int orderId);
}
