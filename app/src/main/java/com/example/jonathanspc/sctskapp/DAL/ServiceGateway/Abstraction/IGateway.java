package com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Abstraction;

import com.example.jonathanspc.sctskapp.BE.BEProduct;

import java.util.ArrayList;

/**
 * Created by JonathansPC on 30-12-2016.
 */
public interface IGateway<T> {
    ArrayList<T> getAll();
    T getById(int id);
}
