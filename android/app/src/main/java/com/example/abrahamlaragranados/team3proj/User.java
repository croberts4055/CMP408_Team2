package com.example.abrahamlaragranados.team3proj;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    public String email;
    public String name;
    public String Role;

    public User() {}

    public User(String email, String name, String Role) {
        this.email = email;
        this.name = name;
        this.Role =  Role;
    }


    protected User(Parcel in) {
        email = in.readString();
        name = in.readString();
        Role = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(Role);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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
