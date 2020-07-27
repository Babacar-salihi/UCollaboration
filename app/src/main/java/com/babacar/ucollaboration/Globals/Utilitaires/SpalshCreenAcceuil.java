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
        animation(); // Méthode pour animer le texte (fondu et se deplace).
        loadCurrentUser(); // Permet de charger le dernier utilisateur connecté.
    }


    @Override
    protected void onStart() {
        super.onStart();

        declencheur(); // Méthode pour déclencher le countDownTimer.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent catalogue  = new Intent(getApplicationContext(), Acceuil.class);
                startActivity(catalogue);
                finish();
            }
        }, 3000); // 3s.
    }

    /**
     * Permet d'afficher les animations.
     */
    private void animation(){
        Animation bottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_bottom_init_position);
        mBienvenue = findViewById(R.id.splash_acceuilText);
        mBienvenue.setAnimation(bottom);
        Animation logo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fondu_logo);
        ImageView mImageView = findViewById(R.id.splash_acceuilLogo);
        mImageView.setAnimation(logo);
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

    /**
     * Permet de déclencher la méthode startTimer qui est dans l'activité Panier/RecyclerViewPanier.
     */
    private void declencheur() {

        //RecyclerViewPanier.startMinuteur();
    }

}
