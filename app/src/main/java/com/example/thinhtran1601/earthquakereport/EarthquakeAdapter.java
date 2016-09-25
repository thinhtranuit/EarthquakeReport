package com.example.thinhtran1601.earthquakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.lang.String;

/**
 * Created by thinh on 17/09/2016.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> Earthquakes){
        super(context, R.layout.list_item,Earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        //Get Earthquake object from ArrayList<Earthquake>
        Earthquake earthquake = getItem(position);

        // Set the magnitude
        TextView magTextView = (TextView)view.findViewById(R.id.mag_text_view);
        magTextView.setText(String.valueOf(earthquake.getMagnitude()));

        //Set color to the Background of Magnitude TextView (the Circle)
        GradientDrawable background = (GradientDrawable)magTextView.getBackground();
        background.setColor(getColor(earthquake.getMagnitude()));

        //Split the String of Location
        String[] strings = earthquake.getLocation().split(" ");
        StringBuilder location = new StringBuilder();
        StringBuilder country = new StringBuilder();
        for(int i = 0; i < strings.length;i++){
            if (i < 3){
                location.append(strings[i]).append(" ");
            } else {
                country.append(strings[i]).append(" ");
            }
        }

        //Set location offset
        TextView locationTextView = (TextView)view.findViewById(R.id.location_offset_text_view);
        locationTextView.setText(location.toString());

        //Set primary location (e.g: name of city and country)
        TextView countryTextView = (TextView)view.findViewById(R.id.primary_location_text_view);
        countryTextView.setText(country.toString());


        //Set date
        TextView dateTextView = (TextView)view.findViewById(R.id.date_text_view);
        dateTextView.setText(new SimpleDateFormat("MMM dd, yyyy").format(earthquake.getDate()).toString());

        //Set time
        TextView timeTextView = (TextView)view.findViewById(R.id.time_text_view);
        timeTextView.setText(new SimpleDateFormat("h:mm aa").format(earthquake.getDate()).toString());

        return view;
    }

    /**
     * @param mag the Magnitude
     * @return the color code of TextView which depends on
     */
    private int getColor(float mag){
        if (mag >= 0.0 && mag <= 2.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude1);
        } else if (mag > 2.0 && mag <= 3.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude2);
        } else if (mag > 3.0 && mag <= 4.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude3);
        } else if (mag > 4.0 && mag <= 5.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude4);
        } else if (mag > 5.0 && mag <= 6.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude5);
        } else if (mag > 6.0 && mag <= 7.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude6);
        } else if (mag > 7.0 && mag <= 8.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude7);
        } else if (mag > 8.0 && mag <= 9.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude8);
        } else if (mag > 9.0 && mag <= 10.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude9);
        } else {
            return ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }
    }
}
