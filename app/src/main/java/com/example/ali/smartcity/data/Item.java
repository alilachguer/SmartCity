package com.example.ali.smartcity.data;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Item implements JsonPopulator {
    Condition condition;
    //List<Forecast> forecasts;

    public Condition getCondition() {
        return condition;
    }
/****
    public List<Forecast> getForecasts() {
        return forecasts;
    }
 **/
    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));

    //    forecasts = new ArrayList<Forecast>();
    //    forecasts = (List<Forecast>) data.optJSONArray("forecast");
    }
}
