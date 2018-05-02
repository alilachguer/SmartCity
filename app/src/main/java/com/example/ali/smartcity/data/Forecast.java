package com.example.ali.smartcity.data;

import org.json.JSONObject;

public class Forecast implements JsonPopulator{
    int code, high, low;
    String date, day, description;


    public void setCode(int code) {
        this.code = code;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
