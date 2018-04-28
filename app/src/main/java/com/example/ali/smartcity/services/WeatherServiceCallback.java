package com.example.ali.smartcity.services;

import com.example.ali.smartcity.data.Channel;

public interface WeatherServiceCallback {

    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
