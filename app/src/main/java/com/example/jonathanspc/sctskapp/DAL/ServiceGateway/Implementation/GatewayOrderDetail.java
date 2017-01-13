package com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Implementation;

import com.example.jonathanspc.sctskapp.BE.BEOrderDetail;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Abstraction.IGatewayOrderDetail;

import java.util.ArrayList;

/**
 * Created by JonathansPC on 01-01-2017.
 */
public class GatewayOrderDetail implements IGatewayOrderDetail<BEOrderDetail> {

    private static GatewayOrderDetail instance;
    private ArrayList<BEOrderDetail> orderdetails;

    public static GatewayOrderDetail getInstance(){
        if(instance == null)
            instance = new GatewayOrderDetail();
        return instance;
    }

    private GatewayOrderDetail() {
        orderdetails = new ArrayList<BEOrderDetail>();
        orderdetails.add(new BEOrderDetail(1, 1, 1, 3, 60));
        orderdetails.add(new BEOrderDetail(2, 1, 2, 3, 60));
        orderdetails.add(new BEOrderDetail(3, 1, 3, 3, 60));
        //-------------------------------------------------
        orderdetails.add(new BEOrderDetail(4, 2, 10, 1, 20));
        orderdetails.add(new BEOrderDetail(5, 2, 12, 1, 20));
        orderdetails.add(new BEOrderDetail(6, 2, 3, 1, 20));
        orderdetails.add(new BEOrderDetail(7, 2, 6, 1, 6));
        orderdetails.add(new BEOrderDetail(8, 2, 7, 1, 6));
        orderdetails.add(new BEOrderDetail(9, 2, 8, 3, 6));
        //-------------------------------------------------
        orderdetails.add(new BEOrderDetail(10, 3, 1, 1, 20));
        orderdetails.add(new BEOrderDetail(11, 3, 9, 1, 6));
        //-------------------------------------------------
        orderdetails.add(new BEOrderDetail(12, 4, 1, 1, 20));
        orderdetails.add(new BEOrderDetail(13, 4, 10, 1, 20));
        orderdetails.add(new BEOrderDetail(14, 4, 13, 1, 6));
    }

    @Override
    public BEOrderDetail add(BEOrderDetail beOrderDetail) {
        orderdetails.add(beOrderDetail);
        return beOrderDetail;
    }

    @Override
    public BEOrderDetail getById(int id) {
        return orderdetails.get(id);
    }

    @Override
    public ArrayList<BEOrderDetail> getAll(int orderId) {
        ArrayList<BEOrderDetail> result = new ArrayList<>();
        for(BEOrderDetail od : orderdetails){
            if(od.getOrderId() == orderId){
                result.add(od);
            }
        }
        return result;
    }

}
