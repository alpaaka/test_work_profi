package ru.alpaaka.testprofi.data.model;

public class Friend {

    private String firstName;
    private String lastName;
    private String photoUrl;

    public Friend(String firstName, String lastName, String photoUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoUrl = photoUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}