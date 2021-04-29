package com.example.pubber;
/*
 * DatabaseAccess.java 1.0 2019/05/03
 *
 * Copyright (c) 2019 Aberystwyth University.
 * All rights reserved.
 *
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Accesses the database and reads in values
 * @Author Troy Swan
 * @Version 1.0
 */
class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    /**
     * Creates a new instance of DatabaseHelper
     * @param context - The current version
     */
    private DatabaseAccess(Context context){
        this.openHelper=new DatabaseHelper(context);
    }

    /**
     * Gets the current instance of DatabaseAccess
     * @param context - The current version
     * @return the current instance
     */
    static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Opens a readable database
     */
    void open(){
        this.db=openHelper.getReadableDatabase();
    }

    /**
     * Closes the database if there is one open
     */
    void close(){
        if(db!=null){
            this.db.close();
        }
    }

    /**
     * counts the number of pubs
     * @return the number of pubs
     */
    int countPubs(){

        c = db.rawQuery("SELECT COUNT(*) FROM pubs;", null);
        c.moveToFirst();
        return c.getInt(0);
    }

    /**
     * Reads in the pubs from the database and stores them as 'Pub' objects
     * @param i - an incrementing value of the number of pubs (will change with each call)
     * @return a new pub object complete with values
     */
    Pub readIn(int i){
        Pub newPub = new Pub();
        boolean trueOrFalse = true;
            newPub.setId(i);
            c = db.rawQuery("SELECT town FROM pubs where id = '" + newPub.getId() + "';", null);
            c.moveToFirst();
            newPub.setTown(c.getString(0));
            c = db.rawQuery("SELECT pubName FROM pubs where id = '" + newPub.getId() + "';", null);
            c.moveToFirst();
            newPub.setName(c.getString(0));
            c = db.rawQuery("SELECT description FROM pubs where id ='" + i + "';", null);
            c.moveToFirst();
            newPub.setDescription(c.getString(0));
            c = db.rawQuery("SELECT gpsLongitude FROM pubs where id ='" + i + "';", null);
            c.moveToFirst();
            newPub.setLongitude(c.getDouble(0));
            c = db.rawQuery("SELECT gpsLatitude FROM pubs where id ='" + i + "';", null);
            c.moveToFirst();
            newPub.setLatitude(c.getDouble(0));
            c = db.rawQuery("SELECT dogFriendly FROM pubs where id ='" + i + "';", null);
            c.moveToFirst();
            trueOrFalse = (c.getInt(0) != 0);
            newPub.setDogFriendly(trueOrFalse);
            c = db.rawQuery("SELECT servesFood FROM pubs where id ='" + i + "';", null);
            c.moveToFirst();
            trueOrFalse = (c.getInt(0) != 0);
            newPub.setServesFood(trueOrFalse);
            c = db.rawQuery("SELECT realAle FROM pubs where id ='" + i + "';", null);
            c.moveToFirst();
            trueOrFalse = (c.getInt(0) != 0);
            newPub.setRealAle(trueOrFalse);
            c = db.rawQuery("SELECT danceFloor FROM pubs where id ='" + i + "';", null);
            c.moveToFirst();
            trueOrFalse = (c.getInt(0) != 0);
            newPub.setDanceFloor(trueOrFalse);
            c = db.rawQuery("SELECT liveMusic FROM pubs where id ='" + i + "';", null);
            c.moveToFirst();
            trueOrFalse = (c.getInt(0) != 0);
            newPub.setLiveMusic(trueOrFalse);
            c = db.rawQuery("SELECT poolTable FROM pubs where id ='" + i + "';", null);
            c.moveToFirst();
            trueOrFalse = (c.getInt(0) != 0);
            newPub.setPoolTable(trueOrFalse);

        /* A try and catch to check if there is an image, if there is none
        its set to null, otherwise the image will be converted into a byte array
        */
            try {
                c = db.rawQuery("SELECT picture FROM pubs where id ='" + i + "';", null);
                c.moveToFirst();
                byte[] byteArray = c.getBlob(0);
                Bitmap tempBM = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                newPub.setPicture(tempBM);
            } catch (Exception e) {
                newPub.setPicture(null);
            }
            return newPub;
    }
}
