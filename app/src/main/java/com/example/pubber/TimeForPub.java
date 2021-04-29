package com.example.pubber;
/*
 * TimeForPub.java 1.0 2019/05/03
 *
 * Copyright (c) 2019 Aberystwyth University.
 * All rights reserved.
 *
 */
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import java.util.Calendar;


public class TimeForPub extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute,true);
    }
}
