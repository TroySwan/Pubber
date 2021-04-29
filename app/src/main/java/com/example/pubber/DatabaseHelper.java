package com.example.pubber;
/*
 * DatabaseHelper.java 1.0 2019/05/03
 *
 * Copyright (c) 2019 Aberystwyth University.
 * All rights reserved.
 *
 */
import android.content.Context;


import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Extends SQLiteAssdtHelper, helps create the database and maintain it
 * @Author Troy Swan
 * @Version 1.0
 */
class DatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME="pubber.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Sets the database name and version
     * @param context - The current version
     */
    DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

}
