package com.example.ali.smartcity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText email, password;
    Button login;
    TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.register_password);
        login = (Button) findViewById(R.id.login);
        registerLink = (TextView) findViewById(R.id.register_link);

        registerLink.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    private void loginUser(){
        String loginEmail = email.getText().toString().trim();
        String loginPassword = password.getText().toString().trim();

        if(TextUtils.isEmpty(loginEmail) || TextUtils.isEmpty(loginPassword)){
            Toast.makeText(this, "email or password empty", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    public void onClick(View view) {

        if (view == registerLink){
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
        }
        else if (view == login){
            loginUser();
        }

    }
}
