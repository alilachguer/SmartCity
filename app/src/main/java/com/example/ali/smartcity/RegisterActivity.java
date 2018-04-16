package com.example.ali.smartcity;

import android.content.Intent;
import android.preference.PreferenceGroup;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.security.spec.ECField;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText pseudo, password, confirmPass, mail;
    Button next, cancel;
    ProgressBar progressBar;

    InfoUser infoUser;
    FirebaseAuth firebaseAuth;

    public static final String USER_INFO = "USER_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        pseudo = (EditText) findViewById(R.id.register_pseudo);
        password = (EditText) findViewById(R.id.register_password);
        confirmPass = (EditText) findViewById(R.id.register_confirm);
        mail = (EditText) findViewById(R.id.register_mail);

        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);

        next = (Button) findViewById(R.id.register_next);
        cancel = (Button) findViewById(R.id.register_cancel);

        next.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }

    public void registerUser(){
        String email = mail.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confirmpass = confirmPass.getText().toString().trim();
        String username = pseudo.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) ||
                TextUtils.isEmpty(confirmpass) || TextUtils.isEmpty(username)){
            Toast.makeText(this, "empty field", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        if(pass.equals(confirmpass)){
            infoUser = new InfoUser(email, pass, username);
            firebaseAuth.createUserWithEmailAndPassword(infoUser.getE_mail(), infoUser.getPassword())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this,
                            "email and password registred successfully",
                            Toast.LENGTH_SHORT).show();
                    finish();
                    Intent register2 = new Intent(RegisterActivity.this, Register2Activity.class);
                    register2.putExtra(USER_INFO, infoUser);
                    startActivity(register2);
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this,
                            task.getException().getLocalizedMessage().toString(),
                            Toast.LENGTH_SHORT).show();
                }
                }
            });
        }
        else if(pass.length() < 6){
            Toast.makeText(this, "password too short", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "error wrong password", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        if(view == cancel){
            finish();
        }
        else if(view == next){
            registerUser();
        }

    }
}
