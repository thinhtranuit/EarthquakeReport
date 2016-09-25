package com.example.thinhtran1601.earthquakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String urlString =
            "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=3&limit=30";
    private EarthquakeAdapter earthquakeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.listView);

        earthquakeAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        listView.setAdapter(earthquakeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake earthquake = earthquakeAdapter.getItem(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(earthquake.getUrl()));
                startActivity(i);
            }
        });

        new EarthQuakeAsyncTask().execute(urlString);
    }

    private class EarthQuakeAsyncTask extends AsyncTask<String,Void,ArrayList<Earthquake>>{
        @Override
        protected ArrayList<Earthquake> doInBackground(String... params) {
            if (params.length < 1 || params[0] == null){
                return null;
            }
            return (ArrayList<Earthquake>) DataHandler.handle(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
            earthquakeAdapter.clear();
            if (earthquakes != null && !earthquakes.isEmpty()){
                earthquakeAdapter.addAll(earthquakes);
            }
        }
    }
}
