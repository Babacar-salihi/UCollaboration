package com.babacar.ucollaboration.Globals.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Activitys.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;


public class Acceuil extends AppCompatActivity {

    private LinearLayout mUserSpace; // Expace regroupant: Connexion, Inscription, Compte.
    private CircleImageView mUserProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        referenceWidgets(); // Méthode permettent de référencer les widgets de "layout.activity_acceuil".
        listener(); // Méthode permettant de gérer les listeners sur le boutons.
        showProfilePic(); // Méthode permettant de gérer l'affichage du profile de l'utilisateur.
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        this.mUserSpace = findViewById(R.id.acceuil_space_user);
        this.mUserProfilePic = findViewById(R.id.acceuil_userPP);
    }

    /**
     * Permet de gérer l'affichage du profile de l'utilisateur.
     */
    private void showProfilePic() {

        if (sCurrentUser != null)
            Glide.with(getApplicationContext())
                .load(sCurrentUser.getPhoto())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mUserProfilePic);
    }

    /**
     * Permet de choisir parmis les quatres applications.
     * @param view
     */
    public void switcher(View view) {

        switch (view.getId()) {

            case R.id.acceuil_umarket :
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slider_right_init_position, R.anim.slider_out_left); break;

            case R.id.acceuil_uservice :
                startActivity(new Intent(getApplicationContext(), com.babacar.ucollaboration.UService.Activitys.MainActivity.class));
                overridePendingTransition(R.anim.slider_right_init_position, R.anim.slider_out_left); break;
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

        mUserSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), UserSpace.class));
            }
        });
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.slider_left_position, R.anim.slider_out_right);
    }

    /**
     * Méthode qui s'execute quand l'activité passe en arriére plan.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ON_TEST", "PAUSE");
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("currentUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("User", gson.toJson(sCurrentUser));
        editor.apply();
    }
}
