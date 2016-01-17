package com.example.ben.ex03home;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Authors: Elad Mizrahi, Ben Nakash
 * ID's:    201550142,    303140057
 * Desc:    Home Exercise 03
 */

public class MainActivity extends AppCompatActivity  {

    private Spinner sprLocation;
    private ListView forcastListView;
    private final String API_KEY = "0c525c050fa2ad9ea4e722dfb1e189bc";
    private Context context;
    private ProgressDialog progressWeatherData, progressLocation;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location userLocation;
    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        //Init app elements.
        sprLocation = (Spinner) findViewById(R.id.sprLocation);
        forcastListView = (ListView) findViewById(R.id.forcastListView);

        // Get system services.
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Show progress dialog until receiving current location
        progressLocation = ProgressDialog.show(MainActivity.this,
                "Loading...", "Getting Your Location", true);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null)
                {
                    userLocation = location;

                    // Stop progress dialog of 'Receiving location'.
                    progressLocation.cancel();

                    fetchWeatherInfo();

                    // We want to receive location only one time, so we tell the location
                    // manager to stop receiving updates.
                    try {
                        locationManager.removeUpdates(locationListener);
                    }
                    catch (SecurityException e) {}
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };


        permissionManager = new PermissionManager
                (this, new PermissionManager.OnPermissionListener() {
            @Override
            public void OnPermissionChanged(boolean permissionGranted) {
                if (permissionGranted)
                {
                    // Start receiving location updates.
                    try {
                        locationManager.requestLocationUpdates
                                (LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
                    }
                    catch (SecurityException e) {}
                }
                else {
                    // Cancel the progress dialog of 'Receiving location'.
                    progressLocation.cancel();
                    Toast.makeText(context, "Error: Can't Get Permission",
                            Toast.LENGTH_SHORT).show();

                    fetchWeatherInfo();
                }
            }
        });
    }


    // This method receives the selected location from the spinner and gets the weather
    // according to it.
    private void fetchWeatherInfo()
    {
        sprLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getWeatherDetails(((TextView) view).getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Setting the default location to 'Current Location'.
        sprLocation.setSelection(9);
    }


    // This method sends a request to the weather API in order to receive a weather forecast
    // to the next 5 days.
    private void getWeatherDetails(String city)
    {
        String requestUrl = "http://api.openweathermap.org/data/2.5/forecast?";

        if(city.equals("Current Location")) {
            if (userLocation != null) {
                requestUrl += "lat=" + userLocation.getLatitude() + "&lon=" +
                        userLocation.getLongitude();
            }
        }
        else {
            requestUrl += "q=" + city;
        }

        requestUrl += "&units=metric&appid=" + API_KEY;

        // Showing progress dialog until receiving the forecast.
        progressWeatherData = ProgressDialog.show
                (MainActivity.this, "Loading...", "Fetching forecast data", true);

        // The next part is in charge of receiving a JSON object from the weather API and sends
        // the data to an adapter in order to parse the information and get it as 'view'.
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest( Request.Method.POST, requestUrl,
                new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject jsonResponse) {
                JSONArray weatherArr = null;
                try{
                    weatherArr = jsonResponse.getJSONArray("list");
                    forcastListView.setAdapter(new WeatherAdapter(weatherArr, context));
                    progressWeatherData.cancel();
                }catch (Exception e) {
                    handleError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError();
            }
        });

        queue.add(request);
    }


    private void handleError() {
        progressWeatherData.cancel();
        String error = "Error: Can't load weather!";
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }
}

