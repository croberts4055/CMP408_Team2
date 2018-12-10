package com.example.abrahamlaragranados.team3proj;

public class User {

    public String email;
    public String name;
    public String Role;

    public User() {}

    public User(String email, String name, String Role) {
        this.email = email;
        this.name = name;
        this.Role =  Role;
    }


    public String getUserRole()
    {return  this.Role;}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //set the user role
    public void setUserRole(String role)
    {this.Role  = role;}
}
