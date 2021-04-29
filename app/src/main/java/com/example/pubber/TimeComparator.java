package com.example.pubber;
/*
 * TimeComparator.java 1.0 2019/05/03
 *
 * Copyright (c) 2019 Aberystwyth University.
 * All rights reserved.
 *
 */
import java.util.Comparator;

/**
 * Time comparator to compare the pubs by their times
 */
public class TimeComparator implements Comparator {
    @Override
    public int compare(Object p1, Object p2) {
        PubMiniCustomView k1 = (PubMiniCustomView) p1;
        PubMiniCustomView k2 = (PubMiniCustomView) p2;
        Pub o1 = k1.getPub();
        Pub o2 = k2.getPub();
        return (o1.getHour()*60 + o1.getMinute()) - (o2.getHour()*60 + o2.getMinute());
    }

}
