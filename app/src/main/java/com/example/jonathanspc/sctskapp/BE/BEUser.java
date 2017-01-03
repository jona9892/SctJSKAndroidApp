package com.example.jonathanspc.sctskapp.BE;

import java.io.Serializable;

/**
 * Created by JonathansPC on 28-12-2016.
 */
public class BEUser implements Serializable {
    private int Id;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Username;
    private String Password;

    public BEUser(){

    }

    public BEUser(int id, String firstName, String lastName,
                  String email, String username, String password){
        this.Id = id;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Email = email;
        this.Username = username;
        this.Password = password;

    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
