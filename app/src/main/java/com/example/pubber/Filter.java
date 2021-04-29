package com.example.pubber;
/*
 * Filter.java 1.0 2019/05/03
 *
 * Copyright (c) 2019 Aberystwyth University.
 * All rights reserved.
 *
 */
/**
 * Allows you to set filters and get whether an option
 * is selected as true or false
 * @Author Troy Swan
 * @Version 1.0
 */
public class Filter {
    private boolean dogFriendly;
    private boolean servesFood;
    private boolean realAle;
    private boolean danceFloor;
    private boolean liveMusic;
    private boolean poolTable;

    /**
     * Checks to see if the filter for 'isDogFriendly' is set
     * to true or false
     * @return True if it allows dogs, false if it doesn't
     */
    public boolean isDogFriendly() {
        return dogFriendly;
    }

    /**
     * Can choose to set filter option to true or false
     * @param dogFriendly - the new value
     */
    public void setDogFriendly(boolean dogFriendly) {
        this.dogFriendly = dogFriendly;
    }

    /**
     * Checks to see if the filter for 'isServesFood' is set
     * to true or false
     * @return True if it serves food, false if it doesn't
     */
    public boolean isServesFood() {
        return servesFood;
    }

    /**
     * Can choose to set filter option to true or false
     * @param servesFood - the new value
     */
    public void setServesFood(boolean servesFood) {
        this.servesFood = servesFood;
    }

    /**
     * Checks to see if the filter for 'isRealAle' is set
     * to true or false
     * @return True if it serves real ale, false if it doesn't
     */
    public boolean isRealAle() {
        return realAle;
    }

    /**
     * Can choose to set filter option to true or false
     * @param realAle - the new value
     */
    public void setRealAle(boolean realAle) {
        this.realAle = realAle;
    }

    /**
     * Checks to see if the filter for 'isDanceFloor' is set
     * to true or false
     * @return True if it does have a dance floor, false if it doesn't
     */
    public boolean isDanceFloor() {
        return danceFloor;
    }

    /**
     * Can choose to set the filter option to true or false
     * @param danceFloor - the new value
     */
    public void setDanceFloor(boolean danceFloor) {
        this.danceFloor = danceFloor;
    }

    /**
     * Checks to see if the filter for 'isLiveMusic' is set
     * to true or false
     * @return True if it does have live music, false if it doesn't
     */
    public boolean isLiveMusic() {
        return liveMusic;
    }

    /**
     * Can choose to set the filter option to true or false
     * @param liveMusic - the new value
     */
    public void setLiveMusic(boolean liveMusic) {
        this.liveMusic = liveMusic;
    }

    /**
     * Checks to see if the filter for (isPoolTable) is set
     * to true or false
     * @return True if it does have a pool table, false if it doesn't
     */
    public boolean isPoolTable() {
        return poolTable;
    }

    /**
     * Can choose to set the filter option to true or false
     * @param poolTable - the new value
     */
    public void setPoolTable(boolean poolTable) {
        this.poolTable = poolTable;
    }
}
