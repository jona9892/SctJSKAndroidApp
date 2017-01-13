package com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Implementation;

import com.example.jonathanspc.sctskapp.BE.BEOrder;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Abstraction.IGatewayOrder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JonathansPC on 31-12-2016.
 */
public class GatewayOrder implements IGatewayOrder<BEOrder> {

    private static GatewayOrder instance;
    private HashMap<Integer,BEOrder> orders;
    private static int ID = 1;

    public static GatewayOrder getInstance(){
        if(instance == null)
            instance = new GatewayOrder();
        return instance;
    }

    private GatewayOrder() {
        orders = new HashMap<>();
        orders.put(ID++, new BEOrder(1, 1, "02/01/2017", "03/01/2017", 180, "09:50"));
        orders.put(ID++, new BEOrder(2, 1, "02/01/2017", "04/01/2017", 90, "09:50"));
        orders.put(ID++, new BEOrder(3, 1, "02/01/2017", "05/01/2017", 26, "09:50"));
        orders.put(ID++, new BEOrder(4, 1, "02/01/2017", "06/01/2017", 46, "09:50"));
    }

    @Override
    public BEOrder add(BEOrder order) {
        order.setId(ID++);
        orders.put(ID++, order);
        return order;
    }

    @Override
    public BEOrder getById(int id) {
        return orders.get(id);
    }

    @Override
    public ArrayList<BEOrder> getAll() {
        ArrayList<BEOrder> result = new ArrayList<>();
        for(Integer id : orders.keySet()){
            result.add(orders.get(id));
        }
        return result;
    }
}
