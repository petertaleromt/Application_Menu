package com.example.myapplication_menu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.myapplication_menu.MainActivity;
import com.example.myapplication_menu.R;

public class Splash extends AppCompatActivity {

    private int time = 5000;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                progressBar.incrementProgressBy(20);
            }
        };
        for(int i=0; i<5; i++){
            new Handler().postDelayed(runnable,(i+1)*1000);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },time);


    }
}