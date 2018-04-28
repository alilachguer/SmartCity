package com.example.ali.smartcity.data;

import org.json.JSONObject;

public class Forecast implements JsonPopulator{
    int code, high, low;
    String date, day, description;


    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        description = data.optString("text");
        date = data.optString("date");
        day = data.optString("day");
        high = data.optInt("high");
        low = data.optInt("low");
    }

    public int getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public int getHigh() {
        return high;
    }

    public int getLow() {
        return low;
    }

    public String getDescription() {
        return description;
    }
}
