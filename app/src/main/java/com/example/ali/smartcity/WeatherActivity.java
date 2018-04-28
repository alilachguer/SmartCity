package com.example.ali.smartcity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.smartcity.data.Channel;
import com.example.ali.smartcity.data.Item;
import com.example.ali.smartcity.services.WeatherServiceCallback;
import com.example.ali.smartcity.services.YahooWeatherService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class WeatherActivity extends AppCompatActivity implements WeatherServiceCallback {

    Toolbar toolbar;
    ImageView weatherIconImageView;
    TextView temperatureTextView, conditionTextView, locationTextView;
    YahooWeatherService service;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            firebaseUser = firebaseAuth.getCurrentUser();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("users");
        }


        RelativeLayout weather = (RelativeLayout) findViewById(R.id.weather_activity);
        toolbar = weather.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.weather);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        weatherIconImageView = (ImageView) findViewById(R.id.weather_icon);
        temperatureTextView = (TextView) findViewById(R.id.current_temperature_field);
        conditionTextView = (TextView) findViewById(R.id.condition_textView);
        locationTextView = (TextView) findViewById(R.id.city_field);
        progressBar = (ProgressBar) findViewById(R.id.loading_weather);

        service = new YahooWeatherService(this);
        //service.refreshWeather("montpellier");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    InfoUser user = child.getValue(InfoUser.class);
                    if(user.getE_mail().toString().equals(firebaseUser.getEmail().toString())){
                        service.refreshWeather(user.getCity());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void serviceSuccess(Channel channel) {
        progressBar.setVisibility(View.GONE);

        Item item = channel.getItem();

        int ressource = getResources().getIdentifier("drawable/icon_"+item.getCondition().getCode(),
                null, getPackageName());

        Drawable weatherIconDrawable = getResources().getDrawable(ressource);
        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        conditionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText(service.getLocation());

    }

    @Override
    public void serviceFailure(Exception exception) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
