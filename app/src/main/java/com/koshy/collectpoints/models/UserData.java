package com.koshy.collectpoints.models;

public class UserData {
    String firstName ;
    String username;
    String points;

    public UserData() {
    }

    public UserData(String firstName, String username, String points) {
        this.firstName = firstName;
        this.username = username;
        this.points = points;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
