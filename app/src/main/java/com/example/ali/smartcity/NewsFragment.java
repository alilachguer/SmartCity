package com.example.ali.smartcity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.smartcity.data.Channel;
import com.example.ali.smartcity.data.Forecast;
import com.example.ali.smartcity.data.InfoUser;
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

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment implements WeatherServiceCallback {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    CardView weather;
    Button news;
    DatabaseReference databaseReference;
    YahooWeatherService service;
    ImageView weatherIconImageView;
    TextView temperatureTextView, conditionTextView, locationTextView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        if(firebaseAuth.getCurrentUser() != null){
            firebaseUser = firebaseAuth.getCurrentUser();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("users");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_news, container, false);
        weatherIconImageView = (ImageView) view.findViewById(R.id.weather_icon);
        temperatureTextView = (TextView) view.findViewById(R.id.current_temperature_field);
        locationTextView = (TextView) view.findViewById(R.id.city_field);

        service = new YahooWeatherService(this);

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

        weather = view.findViewById(R.id.weather_card);
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), WeatherActivity.class));
            }
        });
        news = view.findViewById(R.id.news);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NewsActivity.class));
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void serviceSuccess(Channel channel) {
        Item item = channel.getItem();
        List<Forecast> forecastsList = item.getForecasts();

        int ressource = getResources().getIdentifier("drawable/icon_"+item.getCondition().getCode(),
                null, getActivity().getPackageName());

        Drawable weatherIconDrawable = getResources().getDrawable(ressource);
        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        locationTextView.setText(service.getLocation());
    }

    @Override
    public void serviceFailure(Exception exception) {
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
