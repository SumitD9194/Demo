package com.h9.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private boolean isAdmin;

    public User(int id, String name, String email, String password, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getId()
    { 
    	return id; 
    }
    public String getName() 
    { 
    	return name; 
    }
    public String getEmail() 
    { 
    	return email; 
    }
    public String getPassword() 
    { 
    	return password; 
    }
    public boolean isAdmin() 
    { 
    	return isAdmin; 
    }
}

