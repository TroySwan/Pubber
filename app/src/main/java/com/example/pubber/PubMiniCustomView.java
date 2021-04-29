package com.example.pubber;
/*
 * PubMiniCustomView.java 1.0 2019/05/03
 *
 * Copyright (c) 2019 Aberystwyth University.
 * All rights reserved.
 *
 */
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adds functionality to have a smaller window with custom size
 * @Author Troy Swan
 * @Version 1.0
 */
public class PubMiniCustomView extends ConstraintLayout {

    View rootView;
    ImageView pubImage;
    TextView pubInfo;
    Boolean selected = false;
    Pub pub;

    /**
     * Initates context
     * @param context - the current version
     */
    public PubMiniCustomView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Established binding beween the Java and Xml
     * @param context - the current version
     */
    private void init(Context context){
        rootView = inflate(context, R.layout.pub_mini, this);
        pubInfo = rootView.findViewById(R.id.pubInfo);
        pubImage = rootView.findViewById(R.id.pubImage);
    }

    /**
     * Gets the pub image
     * @return the image
     */
    public ImageView getPubImage() {
        return pubImage;
    }

    /**
     * Gets the pubs info
     * @return the pub info
     */
    public TextView getPubInfo() {
        return pubInfo;
    }

    /** Gets if the mini is selected
     *
     * @return true if pub is selected
     */
    public Boolean getSelected() {
        return selected;
    }

    /**
     * Sets if the pub is selected
     * @param statement boolean
     */
    public void setSelecter(Boolean statement) {
        selected = statement;
    }

    /**
     * Gets the pub associated with the view
     * @return the pub
     */
    public Pub getPub() {
        return pub;
    }
    /**
     * Sets the pub associated with the view
     */
    public void setPub(Pub pub) {
        this.pub = pub;
    }
}
