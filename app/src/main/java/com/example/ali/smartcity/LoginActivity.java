package com.example.ali.smartcity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText username, password;
    Button login;
    TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.register_password);
        login = (Button) findViewById(R.id.login);
        registerLink = (TextView) findViewById(R.id.register_link);

        registerLink.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == registerLink){
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
        }
        else if (view == login){

        }

    }
}
