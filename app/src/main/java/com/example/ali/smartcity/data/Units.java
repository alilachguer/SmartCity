package com.example.ali.smartcity.data;

import org.json.JSONObject;

public class Units implements JsonPopulator {
    String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
