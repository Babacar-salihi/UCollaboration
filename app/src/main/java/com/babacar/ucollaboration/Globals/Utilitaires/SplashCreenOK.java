package com.babacar.ucollaboration.Globals.Utilitaires;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Activitys.MainActivity;

public class SplashCreenOK extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_creen_ok);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent versAcceuil = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(versAcceuil);
                finish();
            }
        };

        new Handler().postDelayed(runnable, 3000);
    }

}
