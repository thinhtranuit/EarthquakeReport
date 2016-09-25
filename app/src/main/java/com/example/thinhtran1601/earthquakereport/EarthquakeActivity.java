package com.example.thinhtran1601.earthquakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    private static final String urlString =
            "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=30";
    private EarthquakeAdapter earthquakeAdapter;
    private TextView emptyTextView;
    private ProgressBar progressBar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Initial Views from activity_main such as ListView, TextView and ProgressBar
         */
        listView = (ListView) findViewById(R.id.listView);
        emptyTextView = (TextView) findViewById(R.id.empty_text_view);
        listView.setEmptyView(emptyTextView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        /**
         * Define an ConnectivityManager to check the Internet connection of user device,
         * If the Connection works well, start fetch data from web api and update UI.
         * Otherwise, show the message to user.
         */
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
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

            //Start Loader to get data from the Web
            getLoaderManager().initLoader(1, null, this);

        } else {
            emptyTextView.setText(R.string.no_internet);
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default)
        );

        Uri baseUri = Uri.parse(urlString);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "30");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", "time");

        return new EarthquakeLoader(this, uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {
        progressBar.setVisibility(View.GONE);
        emptyTextView.setText(R.string.no_earthquake);
        earthquakeAdapter.clear();
        if (data != null && !data.isEmpty()) {
            earthquakeAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        earthquakeAdapter.clear();
    }
}
