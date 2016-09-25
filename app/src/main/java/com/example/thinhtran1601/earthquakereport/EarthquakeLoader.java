package com.example.thinhtran1601.earthquakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by thinh on 22/09/2016.
 */
public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    private String urlString;
    public EarthquakeLoader(Context context, String urlString) {
        super(context);
        this.urlString = urlString;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        return (ArrayList<Earthquake>) DataHandler.handle(urlString);
    }
}
