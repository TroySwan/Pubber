package com.example.pubber;
/*
 * RandomPubTour.java 1.0 2019/05/03
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class RandomPubTour extends AppCompatActivity {

    ArrayList<Integer> pubIDList = new ArrayList<>();
    ArrayList<Pub> randomPubList = new ArrayList<>();
    Pub pub;

    /**
     * Sorts out the visual display of the window;
     * hides stuff, makes sure its fullscreen etc
     */
    int uiSettings = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    /**
     * Focuses the screen onto the window of the system,
     * it will hide the system UI
     * @param hasFocus - True or false value on whether the screen is being viewed
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    /**
     * Sets the content view to the associated xml, hides the UI
     * @param savedInstanceState the saved instance
     */
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_tour);

        pubIDList =  getIntent().getIntegerArrayListExtra("pubList");
        hideSystemUI();

        pullPubsBack();
        nextPub();
    }

    /**
     * Gets all the pubs back from the database because Pub objects cannot be passed from
     * one activity to another.
     */
    public void pullPubsBack(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        for(Integer integer : pubIDList){
            randomPubList.add(databaseAccess.readIn(integer));
        }
        databaseAccess.close();
    }

    /**
     * Takes the user to the next pub's information.
     */
    public void nextPub(){
        pub = randomPubList.get(0);
        ImageView pubPic = findViewById(R.id.pubPic2);
        TextView pubName = findViewById(R.id.pubName2);
        pubPic.setImageBitmap(pub.getPicture());
        pubName.setText(pub.getName());
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
     * onClick method for the tickbox so the user can go to the next pub or finish the tour.
     * @param view the current view.
     */
    public void visited(View view){
        if(randomPubList.size() > 1) {
            randomPubList.remove(0);
            nextPub();
        }
        else{
            congratulations();
        }
    }
    /**
     * Sets the content view to congratulate the user for finishing the tour.
     */
    public void congratulations(){
        setContentView(R.layout.congratulations);
    }
    /**
     * onClick method for the home button, takes the user back to the start page.
     * @param view the current view.
     */
    public void home(View view){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }

    public void showMap(View view) {

        Intent showMap = new Intent(this, Map.class);
        showMap.putExtra("pubName", pub.getName());
        showMap.putExtra("pubLat", pub.getLatitude());
        showMap.putExtra("pubLong", pub.getLongitude());
        startActivity(showMap);


    }
    /**
     * Hides the system's UI from the user
     */
    public void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiSettings);
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
