package com.example.thinhtran1601.earthquakereport;

import java.util.Date;

/**
 * Created by thinh on 17/09/2016.
 */
public class Earthquake {
    private float magnitude;
    private String location;
    private Date date;
    private String url;

    public Earthquake(float mag, String loc, long l, String url){
        this.location = loc;
        this.magnitude = mag;
        this.date = new Date(l);
        this.url = url;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }
}
