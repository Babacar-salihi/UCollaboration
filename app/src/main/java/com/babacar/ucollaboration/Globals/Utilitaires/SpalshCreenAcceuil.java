package com.babacar.ucollaboration.Globals.Utilitaires;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.babacar.ucollaboration.Globals.Activitys.Acceuil;
import com.babacar.ucollaboration.Globals.Models.Etudiant;
import com.babacar.ucollaboration.R;
import com.google.gson.Gson;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;


public class SpalshCreenAcceuil extends AppCompatActivity {

    private TextView mBienvenue, mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_creen_acceuil);

        mUserName = findViewById(R.id.splash_userName);
        loadCurrentUser(); // Permet de charger le dernier utilisateur connecté.
    }


    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent catalogue  = new Intent(getApplicationContext(), Acceuil.class);
                startActivity(catalogue);
                finish();
            }
        }, 5000); // 3s.

        animation(); // Méthode pour animer le texte (fondu et se deplace).
    }

    /**
     * Permet d'afficher les animations.
     */
    private void animation(){
        Animation bottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_bottom_init_position);
        mBienvenue = findViewById(R.id.splash_acceuilText);
        mBienvenue.setAnimation(bottom);
        Animation logoForme = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_forme_rotate);
        Animation logoTrait = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_trait_hideshow);
        Animation logoName = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_name_droit);

        ImageView mLogoForme = findViewById(R.id.splash_acceuilLogoForme);
        mLogoForme.setAnimation(logoForme);
        ImageView mLogoTrait = findViewById(R.id.splash_acceuilLogoTrait);
        mLogoTrait.setAnimation(logoTrait);
        ImageView mLogoName = findViewById(R.id.splash_acceuilLogoName);
        mLogoName.setAnimation(logoName);
    }


    /**
     * Sauvegarder le dernier utilisateur connecté.
     */
    private void loadCurrentUser(){

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("currentUser", MODE_PRIVATE);
        String json = sharedPreferences.getString("User", null);
        Log.d("SHAREDTEST", "LOAD: "+json);
        if(json != null){
            sCurrentUser = gson.fromJson(json, Etudiant.class);
            if(sCurrentUser != null){
                mUserName.setText(sCurrentUser.getPrenomEtu()+" "+sCurrentUser.getNomEtu());
                Animation aniUserName = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.hide_to_show);
                mUserName.setVisibility(View.VISIBLE);
                mUserName.setAnimation(aniUserName);
            }
        }
    }
}
