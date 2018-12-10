package com.example.abrahamlaragranados.team3proj;

public class Video {

    public String url;
    public String description;
    public String name;


    public Video() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Video(String url) {

        this.url = url;
    }
}
