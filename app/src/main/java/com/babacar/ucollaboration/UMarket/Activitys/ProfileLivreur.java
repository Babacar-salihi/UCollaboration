package com.babacar.ucollaboration.UMarket.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.babacar.ucollaboration.Globals.Models.Etudiant;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UService.Models.Bosseur;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sRefUService;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sReference;

public class ProfileLivreur extends AppCompatActivity {

    private String mIdLivreur;
    public EasyFlipView mFlipper;
    public View mOnLine;
    public ImageView mFrontPP, mBackPP;
    public TextView mFrontName, mFrontPress, mBackPress, mBackAdr, mBackTel, mBackEmail;
    public RatingBar mRatingBar;
    public CardView mBtnCall, mBtnMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umarket_activity_profile_livreur);

        mIdLivreur = getIntent().getStringExtra("idLivreur");
        referencerWidgets(); // Méthode permettant de réferecer les widgets.
    }

    /**
     * Permet de réferencer les widgets
     */
    private void referencerWidgets() {

        this.mFlipper = findViewById(R.id.flipperLivreur);

        this.mOnLine = findViewById(R.id.uservice_onLine);
        this.mFrontPP = findViewById(R.id.front_carte_image);
        this.mFrontName = findViewById(R.id.front_carte_name);
        this.mFrontPress = findViewById(R.id.front_carte_profess);

        this.mBackPP = findViewById(R.id.back_carte_image);
        this.mBackPress = findViewById(R.id.back_carte_profess);
        this.mBackAdr = findViewById(R.id.back_carte_adr);
        this.mBackTel = findViewById(R.id.back_carte_tel);
        this.mBackEmail = findViewById(R.id.back_carte_email);

        this.mBtnCall = findViewById(R.id.uservice_btn_call_bosseur);
        this.mBtnMsg = findViewById(R.id.uservice_btn_whats_bosseur);

        this.mRatingBar = findViewById(R.id.front_carte_noterBosseur);
    }

    @Override
    protected void onStart() {
        super.onStart();

        sRefUService.child(mIdLivreur).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final Bosseur bosseur = dataSnapshot.getValue(Bosseur.class);
                Log.d("Bosseiiie", bosseur.toString());
                sReference.child("Etudiants")
                        .child(mIdLivreur)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Etudiant etudiant = dataSnapshot.getValue(Etudiant.class);
                                inflateLiv(etudiant, bosseur); // Méthode pour afficher les livreurs.
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        })
                ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Permet de récupérer les informations sur le livreur et les afficher.
     * @param etudiant
     */
    private void inflateLiv(final Etudiant etudiant, Bosseur bosseur) {

        /* Front */
        mFrontName.setText(etudiant.getPrenomEtu()+" "+etudiant.getNomEtu());
        mFrontPress.setText(bosseur.getCategorieSocioProf());
        Glide.with(getApplicationContext()).load(etudiant.getPhoto()).into(mFrontPP);
        //like(holder, bosseur); // Permet de noter le bosseur.
        if (bosseur.isOnLine())
            mOnLine.setVisibility(View.VISIBLE);

        /* Back */
        Glide.with(getApplicationContext()).load(etudiant.getPhoto()).into(mBackPP);
        mBackPress.setText(bosseur.getCategorieSocioProf());
        mBackAdr.setText(etudiant.getNewAdresse());
        mBackTel.setText("+221 "+etudiant.getNumTelephoneEtu());
        mBackEmail.setText(etudiant.getEmail());

        /* Appeler le bosseur */
        mBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callBosseur(etudiant.getNumTelephoneEtu());
            }
        });

        /* WhatsApp */
        mBtnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                whatsApp(etudiant.getNumTelephoneEtu());
            }
        });

        mFlipper.setFlipOnceEnabled(true);
        mFlipper.setAutoFlipBack(true);
        mFlipper.setAutoFlipBackTime(1000);
    }

    /**
     * Permet d'appeler le bosseur pour bénéficier de ses services.
     * @param numLivreur
     */
    private void callBosseur(int numLivreur) {

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 456);
        } else {
            Intent callBosseur = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+221"+numLivreur));
            callBosseur.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callBosseur);
        }
    }

    /**
     * Permet de joindre le bosseur sur WhatsApp
     * @param numLivreur
     */
    private void whatsApp(int numLivreur) {

        if (appIsInstalled()) {

            Intent whatsApp = new Intent(Intent.ACTION_VIEW);
            whatsApp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            whatsApp.setData(Uri.parse("https://api.whatsapp.com/send?phone=+221"+numLivreur+"text=Salut"));
            startActivity(whatsApp);
        } else {

            Toast.makeText(getApplicationContext(), "WhatsApp n'est pas installé!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Permet d'ouvrir whatsApp pour contacter le vendeur.
     * @return
     */
    private boolean appIsInstalled() {

        PackageManager packageManager = getPackageManager();
        boolean isInstall;
        try {

            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            isInstall = true;
        } catch (PackageManager.NameNotFoundException e) {

            isInstall = false;
        }

        return isInstall;
    }
}
