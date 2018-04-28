package com.example.ali.smartcity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class Register2Activity extends AppCompatActivity implements View.OnClickListener{

    Spinner gender;
    EditText date, height, weight;
    Button register;
    InfoUser infoUser;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    PlaceAutocompleteFragment autocompleteFragment;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        date = (EditText) findViewById(R.id.birthdate_register2);
        height = (EditText) findViewById(R.id.height_register2);
        weight = (EditText) findViewById(R.id.weight_register2);
        register = (Button) findViewById(R.id.button_register2);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        gender = (Spinner) findViewById(R.id.gender_register2);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //Log.i(TAG, "Place: " + place.getName());
                Toast.makeText(getApplicationContext(), place.getName().toString(), Toast.LENGTH_SHORT).show();
                city = place.getName().toString();
            }
            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                //Log.i(TAG, "An error occurred: " + status);
            }
        });

        register.setOnClickListener(this);
    }

    public void registerUser2(){
        infoUser = (InfoUser) getIntent().getParcelableExtra(RegisterActivity.USER_INFO);

        infoUser.setCity(city);
        //infoUser.setCity("paris");
        infoUser.setDate(date.getText().toString().trim());
        infoUser.setHeight(height.getText().toString().trim());
        infoUser.setWeight(weight.getText().toString().trim());
        infoUser.setGender(gender.getSelectedItem().toString());

        firebaseAuth.createUserWithEmailAndPassword(infoUser.getE_mail(), infoUser.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            databaseReference.child("users").child(user.getUid()).setValue(infoUser);
                            Toast.makeText(getApplicationContext(), "user registered!!", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else {
                            Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage().toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

    @Override
    public void onClick(View view) {
        if(view == register){
            registerUser2();
        }
    }
}
