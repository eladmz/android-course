package com.example.ben.ex03home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;

/**
 * Authors: Elad Mizrahi, Ben Nakash
 * ID's:    201550142,    303140057
 * Desc:    Home Exercise 03
 */

public class WeatherAdapter extends BaseAdapter {

    private Context context;
    private String[] date, time, temp, forecastDesc, forecastImage;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    // Parse all the needed information from the JSON object into different details such as date,
    // hour, temperature etc.
    public WeatherAdapter(JSONArray responseArr, Context context) {
        this.context = context;
        int arrLength = 0;
        if(responseArr != null) {
            arrLength = responseArr.length();
            date = new String[arrLength];
            time = new String[arrLength];
            temp = new String[arrLength];
            forecastDesc = new String[arrLength];
            forecastImage = new String[arrLength];

            for(int i=0 ; i<arrLength ; i++) {
                try {
                    JSONObject weatherJSON = responseArr.getJSONObject(i);
                    long dateInMillis = weatherJSON.getLong("dt")*1000;
                    date[i] = dateFormat.format(dateInMillis);
                    time[i] = timeFormat.format(dateInMillis);
                    temp[i] = convertTemp(weatherJSON.getJSONObject("main").getString("temp"));
                    forecastDesc[i] = weatherJSON.getJSONArray("weather").getJSONObject(0).
                            getString("description");
                    forecastImage[i] = weatherJSON.getJSONArray("weather").getJSONObject(0).
                            getString("icon");
                }
                catch(JSONException e) {}
            }
        }
    }


    // This method is in charge of taking all the received data and display it in the list view.
    // It returns one view at each time, that represents one row.
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView  txtDate, txtTime, txtTemp, txtForecastDesc;
        ImageView imgForecast;
        View row;

        row = LayoutInflater.from(this.context).inflate(R.layout.weather_row, parent, false);

        // Connecting the views.
        txtDate = (TextView)row.findViewById(R.id.txtDate);
        txtTime = (TextView)row.findViewById(R.id.txtTime);
        txtTemp = (TextView)row.findViewById(R.id.txtTemp);
        txtForecastDesc = (TextView)row.findViewById(R.id.txtForecastDesc);
        imgForecast = (ImageView)row.findViewById(R.id.imgForecast);

        // Insert the information into the views.
        txtDate.setText(date[position]);
        txtTime.setText(time[position]);
        txtTemp.setText(temp[position]);
        txtForecastDesc.setText(forecastDesc[position]);
        Picasso.with(this.context).load("http://openweathermap.org/img/w/" +
                forecastImage[position] + ".png").into(imgForecast);

        return row;
    }

    // The API receive the temperature as a double. So we round it down and converts it
    // into a string, while adding 'c' to it to represent it as 'Celsius'.
    private String convertTemp(String tempJSON) {
        try {
            int roundTemp = (int)Double.parseDouble(tempJSON);
            return roundTemp + "c";
        }
        catch (NumberFormatException e){
            return "N/A";
        }
    }

    // returns the number of views within the adapter.
    @Override
    public int getCount() {
        return date.length;
    }

    // Not in use, had to implement.
    public Object getItem(int position) {
        return null;
    }

    // Not in use, had to implement.
    public long getItemId(int position) {
        return 0;
    }

}
