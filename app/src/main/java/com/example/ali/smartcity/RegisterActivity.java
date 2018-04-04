package com.example.ali.smartcity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.security.spec.ECField;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText pseudo, password, confirmPass, mail;
    Button next, cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pseudo = (EditText) findViewById(R.id.register_pseudo);
        password = (EditText) findViewById(R.id.register_password);
        confirmPass = (EditText) findViewById(R.id.register_confirm);
        mail = (EditText) findViewById(R.id.register_mail);

        next = (Button) findViewById(R.id.register_next);
        cancel = (Button) findViewById(R.id.register_cancel);

        next.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == cancel){
            finish();
        }
        else if(view == next){
            Intent register2Intent = new Intent(this, Register2Activity.class);
            startActivity(register2Intent);
        }

    }
}
