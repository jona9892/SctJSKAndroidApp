package com.example.jonathanspc.sctskapp.DAL.DALC.Abstraction;

import java.util.ArrayList;

/**
 * Created by JonathansPC on 31-12-2016.
 */
public interface IDALCOrder<T> {
    T add(T t);
    T getById(int id);
    ArrayList<T> getAll();
}
