package com.example.pubber;
/*
 * PlannedPubTour.java 1.0 2019/05/03
 *
 * Copyright (c) 2019 Aberystwyth University.
 * All rights reserved.
 *
 */
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
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
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Extends AppCompatActivity, Implements TimePickerDialog.OnTimeSetListener.
 * This class is for Planned Pub Tours.
 * @Author Troy Swan
 * @Version 1.0
 */
public class PlannedPubTour extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    ArrayList<Integer> pubIDList = new ArrayList<>();
    ArrayList<Pub> plannedPubList = new ArrayList<>();
    ArrayList<PubMiniCustomView> tourList = new ArrayList<>();
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
     * Runs methods once the activity is started/created
     * @param savedInstanceState - the current instance saved
     */

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planned_pub_tour);

        pubIDList =  getIntent().getIntegerArrayListExtra("pubList");
        hideSystemUI();

        ImageButton backButton = findViewById(R.id.backToMainButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlannedPubTour.super.onBackPressed();
            }
        });
        pullPubsBack();
        showPubs();
    }

    /**
     * Gets all the pubs back from the database because Pub objects cannot be passed from
     * one activity to another.
     */
    public void pullPubsBack(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        for(Integer integer : pubIDList){
            plannedPubList.add(databaseAccess.readIn(integer));
        }
        databaseAccess.close();
    }

    /**
     * Displays the pubs in a list formatted in the custom view pub_mini.xml
     */
    public void showPubs(){
        LinearLayout list = findViewById(R.id.verticalTourContainer);
        for(Pub pub : plannedPubList){
            final PubMiniCustomView newPub = new PubMiniCustomView(this);
            newPub.setPub(pub);
            ImageView pubPic = newPub.getPubImage();
            pubPic.setImageBitmap(pub.getPicture());
            TextView pubName = newPub.getPubInfo();
            pubName.setText(pub.getName());
            list.addView(newPub);

            newPub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConstraintLayout background = newPub.findViewById(R.id.pubConstraint);
                    if(newPub.getSelected()){
                        newPub.setSelecter(false);
                        background.setBackgroundColor(Color.parseColor("#F5F5F5"));
                        tourList.remove(newPub);
                    }
                    else {
                        newPub.setSelecter(true);
                        background.setBackgroundColor(Color.parseColor("#C5E1A5"));

                        tourList.add(newPub);

                        DialogFragment chooseTime = new TimeForPub();
                        chooseTime.show(getSupportFragmentManager(),"time picker");



                    }

                }
            });
        }
    }

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
     * Changes the content view to start the tour
     */
    public void addPubsToPlannedTour(View view){
        if(tourList.size() > 0) {
            setContentView(R.layout.pub_tour);
            Collections.sort(tourList, new TimeComparator());
            nextPub(view);
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "0 Pubs Selected!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * Takes the user to the next pub's information.
     * @param view the current view.
     */
    public void nextPub(View view){
        pub = tourList.get(0).getPub();
        ImageView pubPic = findViewById(R.id.pubPic2);
        TextView pubName = findViewById(R.id.pubName2);
        TextView timeToBeAtPub = findViewById(R.id.timeToBeAtPub);
        pubPic.setImageBitmap(pub.getPicture());
        pubName.setText(pub.getName());
        String time = String.format("%02d:%02d", pub.getHour(),pub.getMinute());
        String timeLine = "TIME TO BE AT PUB: ".concat(time);
        timeToBeAtPub.setText(timeLine);
    }

    /**
     * onClick method for the tickbox so the user can go to the next pub or finish the tour.
     * @param view the current view.
     */
    public void visited(View view){
        if(tourList.size() > 1) {
            tourList.remove(0);
            nextPub(view);
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

    /**
     * Shows the pub on the map.
     * @param view the current view.
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
     * Hides the system's UI from the user
     */
    public void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiSettings);
    }

    /**
     * Assigns times to a pub when the time dialog is set.
     * @param view the current view
     * @param hourOfDay the users inputted hour
     * @param minute the users inputted time
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        int size = tourList.size();
        tourList.get(size-1).getPub().setHour(hourOfDay);
        tourList.get(size-1).getPub().setMinute(minute);
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
