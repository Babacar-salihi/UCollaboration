package com.babacar.ucollaboration.Globals.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Activitys.MainActivity;


public class Acceuil extends AppCompatActivity {

    private Button mBntConnexion, mBtnInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        referenceWidgets(); // Méthode permettent de référencer les widgets de "layout.activity_acceuil".
        listener(); // Méthode permettant de gérer les listeners sur le boutons.
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        this.mBntConnexion = findViewById(R.id.global_acceuil_btnConnex);
        this.mBtnInscription = findViewById(R.id.global_acceuil_btnInscript);
    }

    public void switcher(View view) {

        switch (view.getId()) {

            case R.id.acceuil_umarket :
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slider_right_init_position, R.anim.slider_out_left); break;

            case R.id.acceuil_uservice :
                break;
            case R.id.acceuil_umaps :
                startActivity(new Intent(getApplicationContext(), com.babacar.ucollaboration.UMaps.Activitys.MainActivity.class));
                overridePendingTransition(R.anim.slider_right_init_position, R.anim.slider_out_left); break;

            case R.id.acceuil_uinfo :
                break;
        }
    }



    /**
     * Permet de gérer les cliques sur les boutons.
     */
    private void listener() {

        this.mBntConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Connexion.class));
                overridePendingTransition(R.anim.slider_right_init_position, R.anim.slider_out_left);
            }
        });

        this.mBtnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Inscription.class));
                overridePendingTransition(R.anim.slider_right_init_position, R.anim.slider_out_left);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.slider_left_position, R.anim.slider_out_right);
    }
}
