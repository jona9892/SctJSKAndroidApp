package com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Abstraction;

import java.util.ArrayList;

/**
 * Created by JonathansPC on 03-01-2017.
 */
public interface IGatewayOrderDetail<T> {
    T add(T t);
    T getById(int id);
    ArrayList<T> getAll(int orderId);
}
