package com.example.abrahamlaragranados.team3proj;

import java.util.Map;

public class UserLink {
    public String user;
    public String link;


    public UserLink(){}


    public UserLink(String user, String link ){
        this.user = user;
        this.link = link;

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public Map<String, Object> toMap() {
        return null;
    }
}