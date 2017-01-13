package com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Abstraction;

import com.example.jonathanspc.sctskapp.BE.BEUser;

/**
 * Created by JonathansPC on 29-12-2016.
 */
public interface IGatewayUser {
    BEUser Login(String username, String password);

}
