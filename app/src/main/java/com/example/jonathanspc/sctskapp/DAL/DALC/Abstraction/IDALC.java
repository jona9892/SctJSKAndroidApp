package com.example.jonathanspc.sctskapp.DAL.DALC.Abstraction;

import com.example.jonathanspc.sctskapp.BE.BEProduct;

import java.util.ArrayList;

/**
 * Created by JonathansPC on 30-12-2016.
 */
public interface IDALC<T> {
    ArrayList<T> getAll();
    T getById(int id);
}
