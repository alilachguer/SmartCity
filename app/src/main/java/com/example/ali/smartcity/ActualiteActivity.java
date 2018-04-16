package com.example.ali.smartcity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActualiteActivity extends AppCompatActivity {

    Button meteo,trafic,actualiteV,agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualite);

        meteo = (Button) findViewById(R.id.button9);
        trafic = (Button) findViewById(R.id.button10);
        actualiteV = (Button) findViewById(R.id.button11);
        agenda = (Button) findViewById(R.id.button12);

        meteo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent meteo = new Intent(ActualiteActivity.this,MeteoActivity.class);
                startActivity(meteo);    }
        });

        trafic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent trafic = new Intent(ActualiteActivity.this,TraficActivity.class);
                startActivity(trafic);    }
        });

        actualiteV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actualiteV = new Intent(ActualiteActivity.this,ActualiteVActivity.class);
                startActivity(actualiteV);    }
        });

        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent agenda = new Intent(ActualiteActivity.this,AgendaActivity.class);
                startActivity(agenda);    }
        });


















    }
}
