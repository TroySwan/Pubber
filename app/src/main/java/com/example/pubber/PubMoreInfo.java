package com.example.pubber;
/*
 * PubMoreInfo.java 1.0 2019/05/03
 *
 * Copyright (c) 2019 Aberystwyth University.
 * All rights reserved.
 *
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

/**
 * Extends AppCompatActivity, sets the window for displaying pub info
 * @Author Troy Swan
 * @Version 1.0
 */
public class PubMoreInfo extends AppCompatActivity{
    private Pub pub = new Pub();
    private GoogleMap mMap;

    /**
     * Intiataes methods
     *
     * @param savedInstanceState - the saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_more_info);
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PubMoreInfo.super.onBackPressed();
            }
        });
        showPubInfo();
    }

    /**
     * Checks if the window has focus, if it does it will hide the system UI
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    /**
     * Hides the system;s UI
     */
    public void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    /**
     * Gets an isnatnce of the DatabaseAccess and reads in a pub
     *
     * @param pubId - the pub to be read in
     */
    public void pullPubFromDB(int pubId) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        pub = databaseAccess.readIn(pubId);
        databaseAccess.close();
    }

    /**
     * Displays the pub info
     */
    public void showPubInfo() {
        int pubId = (int) getIntent().getSerializableExtra("pub");
        pullPubFromDB(pubId);

        ImageView pubPic = findViewById(R.id.pubPic);
        TextView pubName = findViewById(R.id.pubName);

        pubPic.setImageBitmap(pub.getPicture());
        pubName.setText(pub.getName());

        ImageView dogFriendly = findViewById(R.id.dogFriendlyCMI);
        ImageView servesFood = findViewById(R.id.servesFoodCMI);
        ImageView realAle = findViewById(R.id.realAleCMI);
        ImageView danceFloor = findViewById(R.id.danceFloorCMI);
        ImageView liveMusic = findViewById(R.id.liveMusicCMI);
        ImageView poolTable = findViewById(R.id.poolTableCMI);

        if (pub.isDogFriendly()) {
            dogFriendly.setImageResource(R.drawable.check_yes);
        } else {
            dogFriendly.setImageResource(R.drawable.check_no);
        }

        if (pub.isServesFood()) {
            servesFood.setImageResource(R.drawable.check_yes);
        } else {
            servesFood.setImageResource(R.drawable.check_no);
        }

        if (pub.isRealAle()) {
            realAle.setImageResource(R.drawable.check_yes);
        } else {
            realAle.setImageResource(R.drawable.check_no);
        }

        if (pub.isDanceFloor()) {
            danceFloor.setImageResource(R.drawable.check_yes);
        } else {
            danceFloor.setImageResource(R.drawable.check_no);
        }

        if (pub.isLiveMusic()) {
            liveMusic.setImageResource(R.drawable.check_yes);
        } else {
            liveMusic.setImageResource(R.drawable.check_no);
        }

        if (pub.isPoolTable()) {
            poolTable.setImageResource(R.drawable.check_yes);
        } else {
            poolTable.setImageResource(R.drawable.check_no);
        }

    }

    /**
     * Opens up the Map activity showing the pubs location on the map
     *
     * @param view - the current view.
     */

    public void showMap(View view) {

        Intent showMap = new Intent(this, Map.class);
        showMap.putExtra("pubName", pub.getName());
        showMap.putExtra("pubLat", pub.getLatitude());
        showMap.putExtra("pubLong", pub.getLongitude());
        startActivity(showMap);


    }

    /**
     * Shows the review of the pub
     * @param view the current view
     */
    public  void showMoreInfo(View view){
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View infoView = inflater.inflate(R.layout.info_window,null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        final PopupWindow infoWindow = new PopupWindow(infoView, width, height, true);
        infoWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBehindWindow(infoWindow);
        Button ok = infoView.findViewById(R.id.okButton);
        TextView description = infoView.findViewById(R.id.description);
        description.setText(pub.getDescription());
        String filtersMet = "";
        if(pub.isDogFriendly()){ filtersMet = filtersMet.concat("Is Dog friendly, ");
        }
        if(pub.isServesFood()){ filtersMet = filtersMet.concat("Serves Food, ");
        }
        if(pub.isRealAle()){ filtersMet = filtersMet.concat("Has Real Ale, ");
        }
        if(pub.isDanceFloor()){ filtersMet = filtersMet.concat("Has A Dance Floor, ");
        }
        if(pub.isLiveMusic()){ filtersMet = filtersMet.concat("Has Live Music, ");
        }
        if(pub.isPoolTable()){ filtersMet = filtersMet.concat("Has A Pool Table.");
        }
        TextView filters = infoView.findViewById(R.id.filtersMet);
        filters.setText(filtersMet);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoWindow.dismiss();
            }
        });
    }

    /**
     * What you can see behind the front window will have its brightness
     * lowered
     * @param popupWindow - The window currently at the front
     */
    public void dimBehindWindow(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) container.getLayoutParams();
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.4f;
        windowManager.updateViewLayout(container, params);
    }

}