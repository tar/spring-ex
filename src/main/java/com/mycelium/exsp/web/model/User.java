package com.mycelium.exsp.web.model;

public class User {
    private String _name;
    private String _email;
    
    public String getName(){
        return _name;
    }
    public String getEmail(){
        return _email;
    }
    
    public void setName(String name){
        _name = name;
    }
    public void setEmail(String email){
        _email = email;
    }
    
}
