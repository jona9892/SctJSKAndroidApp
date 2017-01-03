package com.example.jonathanspc.sctskapp.BE;

import java.io.Serializable;

/**
 * Created by JonathansPC on 29-12-2016.
 */
public class Credentials implements Serializable {
    private String username;
    private String password;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
