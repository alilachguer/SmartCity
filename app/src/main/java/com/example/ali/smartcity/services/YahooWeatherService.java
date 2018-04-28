package com.example.ali.smartcity.services;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.ali.smartcity.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class YahooWeatherService {

    private WeatherServiceCallback callback;
    private String location;
    private Exception error;
    private String temperatureUnit = "C";

    private String getTemperatureUnit() {
        return temperatureUnit;
    }

    public YahooWeatherService(WeatherServiceCallback weatherServiceCallback){
        this.callback = weatherServiceCallback;
    }

    public String getLocation() {
        return location;
    }

    public void refreshWeather(final String loc){
        this.location = loc;
        new AsyncTask<String, Void, Channel>() {
            @Override
            protected Channel doInBackground(String[] locations) {
                String location = locations[0];
                Channel channel = new Channel();

                String unit = getTemperatureUnit().equalsIgnoreCase("f") ? "f" : "c";

                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='" + unit + "'", location);
                String endPoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(endPoint);
                    URLConnection connection = url.openConnection();
                    connection.setUseCaches(false);
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }
                    reader.close();

                    JSONObject data = new JSONObject(result.toString());
                    JSONObject queryResult = data.optJSONObject("query");
                    int count = queryResult.optInt("count");
                    if(count == 0){
                        error = new LocationWeatherException("no information found for " + location);
                        return null;
                    }

                    JSONObject channelJSON = queryResult.optJSONObject("results").optJSONObject("channel");
                    channel.populate(channelJSON);

                    return channel;


                } catch (Exception e) {
                    error = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Channel channel) {
                if(channel == null && error != null){
                    callback.serviceFailure(error);
                } else {
                    callback.serviceSuccess(channel);
                }
            }
        }.execute(location);
    }

    public class LocationWeatherException extends Exception{
        public LocationWeatherException(String message) {
            super(message);
        }
    }
}
