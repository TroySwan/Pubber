package com.example.pubber;

/*
 * MainActivity.java 1.0 2019/05/03
 *
 * Copyright (c) 2019 Aberystwyth University.
 * All rights reserved.
 *
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Sorts out filter functionality, create lists which uphold to
 * filter options
 * @Author Troy Swan
 * @Version 1.0
 */
public class MainActivity extends AppCompatActivity {
    int numberOfPubs;
    Filter filter = new Filter();
    ArrayList<Pub> masterPubList = new ArrayList<>();
    ArrayList<Pub> filteredPubList = new ArrayList<>();
    ArrayList<Pub> pubsToRemove = new ArrayList<>();
    ArrayList<Pub> randomPubList = new ArrayList<>();

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.deleteDatabase("pubber.db");
        createMasterList();
        showPubList();

        ImageButton filterButton = findViewById(R.id.filterButton);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterMenu(view, false);
            }
        });
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
     * Adds all the pubs to the filtered list and works through them
     * moving the ones which which don't correspond with the filters
     * to the pubsToRemove list
     * @param view - the current window
     */
    public void filterList(View view){
        filteredPubList.clear();
        filteredPubList.addAll(masterPubList);
        pubsToRemove.clear();

        CheckBox dogFriendly = view.findViewById(R.id.dogFriendlyCheck);
        CheckBox danceFloor = view.findViewById(R.id.danceFloorCheck);
        CheckBox liveMusic = view.findViewById(R.id.liveMusicCheck);
        CheckBox poolTable = view.findViewById(R.id.poolTableCheck);
        CheckBox servesFood = view.findViewById(R.id.servesFoodCheck);
        CheckBox realAle = view.findViewById(R.id.realAleCheck);

        filter.setDanceFloor(danceFloor.isChecked());
        filter.setLiveMusic(liveMusic.isChecked());
        filter.setPoolTable(poolTable.isChecked());
        filter.setDogFriendly(dogFriendly.isChecked());
        filter.setServesFood(servesFood.isChecked());
        filter.setRealAle(realAle.isChecked());

        for(Pub pub : filteredPubList){
            if((filter.isDogFriendly() != pub.isDogFriendly() && filter.isDogFriendly())){
                pubsToRemove.add(pub);
            }
            if((filter.isDanceFloor() != pub.isDanceFloor() && filter.isDanceFloor())){
                pubsToRemove.add(pub);
            }
            if((filter.isLiveMusic() != pub.isLiveMusic() && filter.isLiveMusic())){
                pubsToRemove.add(pub);
            }
            if((filter.isPoolTable() != pub.isPoolTable() && filter.isPoolTable())){
                pubsToRemove.add(pub);
            }
            if((filter.isServesFood() != pub.isServesFood() && filter.isServesFood())){
                pubsToRemove.add(pub);
            }
            if((filter.isRealAle() != pub.isRealAle() && filter.isRealAle())){
                pubsToRemove.add(pub);
            }
        }
        filteredPubList.removeAll(pubsToRemove);
        showPubList();

    }

    /**
     * Hides the system's UI from the user
     */
    public void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiSettings);
    }

    /**
     * Reads in the information retrieved from the database and add them to the
     * master list (the list that contains all the pubs in an area)
     */
    public void createMasterList(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        numberOfPubs = databaseAccess.countPubs();
        for (int i = 1; i < numberOfPubs+1; i++){
            Pub newPub = databaseAccess.readIn(i);
            masterPubList.add(newPub);
        }
        databaseAccess.close();
        filteredPubList.addAll(masterPubList);
    }

    /**
     * Opens a window to view the pubs details
     * @param i - increments as it reads through the list of pubs
     */
    public void goToPub(int i){
        Intent showPub = new Intent(this, PubMoreInfo.class);
        showPub.putExtra("pub", i);
        startActivity(showPub);
    }

    /**
     * Displays the list of pubs visually and adds a listener to each pub,
     * which takes you to the pubs details window
     */
    public void showPubList(){
        LinearLayout verticalContainer = findViewById(R.id.verticalContainer);
        verticalContainer.removeAllViews();
        for (Pub pub : filteredPubList){
            PubMiniCustomView newPub = new PubMiniCustomView(this);
            ImageView pubPic = newPub.getPubImage();
            pubPic.setImageBitmap(pub.getPicture());
            TextView pubName = newPub.getPubInfo();
            pubName.setText(pub.getName());
            verticalContainer.addView(newPub);
            final int pubID = pub.getId();
            newPub.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    goToPub(pubID);

                }
            });
        }
    }

    /**
     * Displays the filter menu
     * @param view the current view.
     */
    public void showFilterMenu(final View view, final Boolean plannedPubTour) {


        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View filterView = inflater.inflate(R.layout.filter_window,null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        final PopupWindow filterWindow = new PopupWindow(filterView, width, height, true);
        filterWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBehindWindow(filterWindow);
        Button ok = filterView.findViewById(R.id.okButton);

        CheckBox dogFriendly = filterView.findViewById(R.id.dogFriendlyCheck);
        CheckBox danceFloor = filterView.findViewById(R.id.danceFloorCheck);
        CheckBox liveMusic = filterView.findViewById(R.id.liveMusicCheck);
        CheckBox poolTable = filterView.findViewById(R.id.poolTableCheck);
        CheckBox servesFood = filterView.findViewById(R.id.servesFoodCheck);
        CheckBox realAle = filterView.findViewById(R.id.realAleCheck);

        dogFriendly.setChecked(filter.isDogFriendly());
        danceFloor.setChecked(filter.isDanceFloor());
        liveMusic.setChecked(filter.isLiveMusic());
        poolTable.setChecked(filter.isPoolTable());
        servesFood.setChecked(filter.isServesFood());
        realAle.setChecked(filter.isRealAle());




        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!plannedPubTour) {
                    filterList(filterView);
                    filterWindow.dismiss();
                }
                else{
                    filterList(filterView);
                    filterWindow.dismiss();
                    goToPPT(view);
                }
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

    /**
     * Displays the filter menu with the alternate condition for proceeding to a tour.
     * @param view the current view.
     */
    public void plannedPubTour(View view){
        showFilterMenu(view, true);
    }

    /**
     * Takes the user to the PlannedPubTour activity.
     * @param view the current view.
     */
    public void goToPPT(View view){
        Intent planned = new Intent(this, PlannedPubTour.class);
        ArrayList<Integer> pubIDList = new ArrayList<>();
        for(Pub pub:filteredPubList ){
            int id = pub.getId();
            pubIDList.add(id);
        }
        planned.putExtra("pubList",pubIDList);
        startActivity(planned);
    }

    /**
     * Shows the filter menu then asks the user the number of pubs they wish to visit for a random pub tour
     * @param view the current view.
     */
    public void randomPubTour(View view){
        showFilterBeforeRandom(view);
    }


    public void showFilterBeforeRandom(final View view){

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View filterView = inflater.inflate(R.layout.filter_window,null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        final PopupWindow filterWindow = new PopupWindow(filterView, width, height, true);
        filterWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBehindWindow(filterWindow);
        Button ok = filterView.findViewById(R.id.okButton);

        CheckBox dogFriendly = filterView.findViewById(R.id.dogFriendlyCheck);
        CheckBox danceFloor = filterView.findViewById(R.id.danceFloorCheck);
        CheckBox liveMusic = filterView.findViewById(R.id.liveMusicCheck);
        CheckBox poolTable = filterView.findViewById(R.id.poolTableCheck);
        CheckBox servesFood = filterView.findViewById(R.id.servesFoodCheck);
        CheckBox realAle = filterView.findViewById(R.id.realAleCheck);

        dogFriendly.setChecked(filter.isDogFriendly());
        danceFloor.setChecked(filter.isDanceFloor());
        liveMusic.setChecked(filter.isLiveMusic());
        poolTable.setChecked(filter.isPoolTable());
        servesFood.setChecked(filter.isServesFood());
        realAle.setChecked(filter.isRealAle());




        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList(filterView);
                filterWindow.dismiss();
                showNumberMenu(view);
            }
        });}


    /**
     * Displays the number menu for the user to choose how many pubs they wish to visit
     * @param view the current view
     */
    public void showNumberMenu(final View view){

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View numberView = inflater.inflate(R.layout.number_random,null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        Button ok = numberView.findViewById(R.id.okRandomButton);
        final EditText numberOfPubs = numberView.findViewById(R.id.pubNumber);

        final PopupWindow filterWindow = new PopupWindow(numberView, width, height, true);
        filterWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBehindWindow(filterWindow);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer numberOfDesiredPubs = null;

                // If the user tries to break the program they cant because of the power of TOAST!
                try {
                    numberOfDesiredPubs = Integer.parseInt(numberOfPubs.getText().toString());
                    if (numberOfDesiredPubs > filteredPubList.size() || numberOfDesiredPubs == 0) {
                        Context context = getApplicationContext();
                        CharSequence text = "There aren't that many pubs matching your criteria!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        Collections.shuffle(filteredPubList);
                        for (int i = 0; i < numberOfDesiredPubs; i++) {
                            randomPubList.add(filteredPubList.get(i));
                        }
                        goToRPT(view);
                    }
                }
                catch (Exception e){
                    Context context = getApplicationContext();
                    CharSequence text = "You didn't type how many pubs you wanted to visit!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });
    }

    /**
     * Takes the user to the RandomPubTour activity.
     * @param view the current view.
     */
    public void goToRPT(View view){
        Intent random = new Intent(this,RandomPubTour.class);
        ArrayList<Integer> pubIDList = new ArrayList<>();
        for(Pub pub:randomPubList ){
            int id = pub.getId();
            pubIDList.add(id);
        }

       random.putExtra("pubList",pubIDList);
        startActivity(random);
    }

}
