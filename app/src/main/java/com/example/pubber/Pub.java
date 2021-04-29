package com.example.pubber;

/*
 * Pub.java 1.0 2019/05/03
 *
 * Copyright (c) 2019 Aberystwyth University.
 * All rights reserved.
 *
 */

import android.graphics.Bitmap;

/**
 * Creates pub objects which stores all the data about a given pub,
 * which has been read in from the database
 */
public class Pub{

    private int id;
    private String town;
    private String name;
    private String description;
    private double longitude;
    private double latitude;
    private boolean dogFriendly;
    private boolean servesFood;
    private boolean realAle;
    private boolean danceFloor;
    private boolean liveMusic;
    private boolean poolTable;
    private Bitmap picture;
    private int hour;
    private int minute;

    /**
     * Gets the ID no. of a pub
     * @return the ID no
     */
    int getId() {
        return id;
    }

    /**
     * Sets the ID no. of a pub
     * @param id - the value for it to be set to
     */
    void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the town the pub is located in
     * @return the town
     */
    String getTown() {
        return town;
    }

    /**
     * Sets the town that the pub is located in
     * @param town - the value for it to be set to
     */
    void setTown(String town) {
        this.town = town;
    }

    /**
     * Gets the name of the pub
     * @return the name of the pub
     */
    String getName() {
        return name;
    }

    /**
     * Sets a new name for the pub
     * @param name - the new name to be set
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the descritpion of the pub
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets a new descirption
     * @param description - the new descritpion to be set
     */
    void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the longitude of the pub
     * @return the longitiude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude of the pub
     * @param longitude - the longitude to be set
     */
    void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets the latitude of the pub
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude of the pub
     * @param latitude - the latitude to be set
     */
    void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets whether the pub accepts dogs
     * @return True if it does, false if it doesn't
     */
    public boolean isDogFriendly() {
        return dogFriendly;
    }

    /**
     * Sets whether the pub accepts dogs
     * @param dogFriendly - the new value
     */
    void setDogFriendly(boolean dogFriendly) {
        this.dogFriendly = dogFriendly;
    }

    /**
     * Gets whether the pub serves food
     * @return True if it does, false if it doesn't
     */
    public boolean isServesFood() {
        return servesFood;
    }

    /**
     * Sets whether the pub serves food
     * @param servesFood - the new value
     */
    void setServesFood(boolean servesFood) {
        this.servesFood = servesFood;
    }

    /**
     * Gets whether the pub serves real ale
     * @return True if it does, false if it doesn't
     */
    public boolean isRealAle() {
        return realAle;
    }

    /**
     * Sets whether the pub serves real ale
     * @param realAle - the new value
     */
    void setRealAle(boolean realAle) {
        this.realAle = realAle;
    }

    /**
     * Gets if the pub has a dance floor
     * @return True if to does, false if it doesn't
     */
    public boolean isDanceFloor() {
        return danceFloor;
    }

    /**
     * Sets if the pub has a dance floor
     * @param danceFloor - the new value
     */
    void setDanceFloor(boolean danceFloor) {
        this.danceFloor = danceFloor;
    }

    /**
     * Gets whether the pub has live music
     * @return True if it does, false if it doesn't
     */
    public boolean isLiveMusic() {
        return liveMusic;
    }

    /**
     * Sets whether the pub has live music
     * @param liveMusic - the new value
     */
    void setLiveMusic(boolean liveMusic) {
        this.liveMusic = liveMusic;
    }

    /**
     * Gets whether the pub has a pool table
     * @return True if it does, false if it doesn't
     */
    public boolean isPoolTable() {
        return poolTable;
    }

    /**
     * Sets whether the pub has a pool table
     * @param poolTable - the new value
     */
    void setPoolTable(boolean poolTable) {
        this.poolTable = poolTable;
    }

    /**
     * Gets the image for the pub, if there is none it wll return null
     * @return the image as a bitmap or null
     */
    public Bitmap getPicture() {
        return picture;
    }

    /**
     * Sets the image for a pub
     * @param picture - the bitmap of the image
     */
    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    /**
     * Gets the hour to be at the pub
     * @return the hour to be at the pub
     */

    public int getHour() {
        return hour;
    }

    /**
     *  Sets the hour to be at the pub
     * @param hour - the hour to be at the pub
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * Gets the minute to be at the pub
     * @return the minute to be at the pub
     */
    public int getMinute() {
        return minute;
    }

    /**
     *  Sets the minute to be at the pub
     * @param minute the minute to be at the pub
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }
}
