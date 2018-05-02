package com.example.ali.smartcity.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Item implements JsonPopulator {
    Condition condition;
    List<Forecast> forecasts = new ArrayList<Forecast>();

    public Condition getCondition() {
        return condition;
    }
    public List<Forecast> getForecasts() {
        return forecasts;
    }
    @Override
    public void populate(JSONObject data){
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));

        JSONArray array = data.optJSONArray("forecast");
        if(array != null){
            for (int i=0; i<array.length(); i++){

                Forecast forecast = new Forecast();
                try {
                    forecast.setCode(array.getJSONObject(i).optInt("code"));
                    forecast.setDate(array.getJSONObject(i).optString("date"));
                    forecast.setDay(array.getJSONObject(i).optString("day"));
                    forecast.setDescription(array.getJSONObject(i).optString("text"));
                    forecast.setHigh(array.getJSONObject(i).optInt("high"));
                    forecast.setLow(array.getJSONObject(i).optInt("low"));

                    forecasts.add(forecast);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
