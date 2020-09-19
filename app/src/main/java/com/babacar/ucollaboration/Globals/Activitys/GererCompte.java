package com.babacar.ucollaboration.Globals.Activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.babacar.ucollaboration.Globals.Models.Etudiant;
import com.babacar.ucollaboration.Globals.Utilitaires.SplashCreenOK;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Activitys.ProfileLivreur;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.upDateUserProfile;

public class GererCompte extends AppCompatActivity {

    private CircleImageView mUserPP;
    private TextView mPrenom, mNom, mEmail, mPwd, mNumTel, mAdresse;
    private static final int REQUEST_CODE = 980;
    private Uri mImageUri ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_compte);

        referenceWidgets(); // Méthode pour référencer les widgets.
        inflateInfo(); // Méthode pour remplir les zonez de testes par les informations de l'utilisteur connecter.
        choisirPhoto(); // Méthode pour choisir une nouvelle photo de profile.

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        this.mUserPP = findViewById(R.id.gererCompte_pp_user);
        this.mPrenom = findViewById(R.id.gererCompte_prenom_user);
        this.mNom = findViewById(R.id.gererCompte_nom_user);
//        this.mEmail = findViewById(R.id.gererCompte_email_user);
        this.mPwd = findViewById(R.id.gererCompte_pwd_user);
        this.mNumTel = findViewById(R.id.gererCompte_tel_user);
        this.mAdresse = findViewById(R.id.gererCompte_adresse_user);
    }

    /**
     * Permet de remplir les zonez de testes par les informations de l'utilisteur connecter.
     */
    private void inflateInfo() {

        // user profil pic
        Glide.with(getApplicationContext())
                .load(sCurrentUser.getPhoto())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mUserPP);

        // user fistName
        mPrenom.setText(sCurrentUser.getPrenomEtu());

        // user lastName
        mNom.setText(sCurrentUser.getNomEtu());

        // user email
        mEmail.setText(sCurrentUser.getEmail());

        // user password
        mPwd.setText(sCurrentUser.getPassword());

        // user number
        mNumTel.setText(sCurrentUser.getNumTelephoneEtu()+"");

        // user address
        mAdresse.setText(sCurrentUser.getAdresse());

    }

    /**
     * Bouton de submit.
     * @param view
     */
    public void btnEnregister(View view) {

        List<Integer> dataChanged = new ArrayList<>(); // Testeur: Il y a au moins un champs modifier
        String prenom = mPrenom.getText().toString().trim();
        String nom = mNom.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String pwd = mPwd.getText().toString().trim();
        String tel = mNumTel.getText().toString().trim();
        String adresse = mAdresse.getText().toString().trim();

        Etudiant updateEtu = new Etudiant();

        /* Champs non vides et differents en valeurs */

        if (mImageUri != null) {

            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            dataChanged.add(0);
        }

        /* Prenom */
        if (((!(TextUtils.isEmpty(prenom))) && (!(TextUtils.equals(prenom, sCurrentUser.getPrenomEtu()))))) {

            Toast.makeText(this, "Prenom", Toast.LENGTH_SHORT).show();
            updateEtu.setPrenomEtu(prenom);
            dataChanged.add(1);
        }

        /* Nom */
        if (((!(TextUtils.isEmpty(nom))) && (!TextUtils.equals(nom, sCurrentUser.getNomEtu())))) {

            Toast.makeText(this, "Nom", Toast.LENGTH_SHORT).show();
            updateEtu.setNomEtu(nom);
            dataChanged.add(2);
        }

        /* Email */
        if (((!(TextUtils.isEmpty(email))) && (!TextUtils.equals(email, sCurrentUser.getEmail())))) {

            Toast.makeText(this, "Email", Toast.LENGTH_SHORT).show();
            updateEtu.setEmail(email);
            dataChanged.add(3);
        }

        /* Password */
        if (((!(TextUtils.isEmpty(pwd))) && (!TextUtils.equals(pwd, sCurrentUser.getPassword())))) {

            Toast.makeText(this, "Mot de passe", Toast.LENGTH_SHORT).show();
            updateEtu.setPassword(pwd);
            dataChanged.add(4);
        }

        /* Téléphone */
        if (((!(TextUtils.isEmpty(tel))) && (!TextUtils.equals(tel, String.valueOf(sCurrentUser.getNumTelephoneEtu()))))){

            Toast.makeText(this, "Téléphone", Toast.LENGTH_SHORT).show();
            updateEtu.setNumTelephoneEtu(Integer.parseInt(tel));
            dataChanged.add(5);
        }

        /* Adresse */
        if (((!(TextUtils.isEmpty(adresse))) && (!TextUtils.equals(adresse, sCurrentUser.getAdresse())))) {

            Toast.makeText(this, "Adresse", Toast.LENGTH_SHORT).show();
            updateEtu.setAdresse(adresse);
            dataChanged.add(6);
        }

        if (!dataChanged.isEmpty()) {

            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Enregistrement en cours...");
            dialog.setMessage("Veuillez patientez s'il vous plaît!");
            dialog.setCancelable(false);
            dialog.show();

            upDateUserProfile(this, dialog,dataChanged, updateEtu,mImageUri);
            execIntent(dialog);

        }

        else
            Toast.makeText(this, "Rien n'a été changé", Toast.LENGTH_SHORT).show();
    }

    /**
     * Permet d'attrendre le temps que les changements s'effectus.
     * @param dialog
     */
    private void execIntent(final ProgressDialog dialog) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (dialog.isShowing()) {

                    intent();
                } else {

                    execIntent(dialog);
                }
            }
        }, 2000);
    }

    private void intent() {

        Intent ok = new Intent(this, SplashCreenOK.class);
        startActivity(ok);
        finish();
    }

    /**
     * Permet de choisir une nouvelle photo de profile.
     */
    public void choisirPhoto() {

        mUserPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mImageUri = null;
                Intent choisirPhoto = new Intent();
                choisirPhoto.setType("image/*");
                choisirPhoto.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(choisirPhoto, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            mImageUri = data.getData();
            mUserPP.setImageURI(mImageUri);
            /*mImageBitmap = (Bitmap) data.getExtras().get("data");
            mUserProfilePic.setImageBitmap(mImageBitmap);*/

        }
    }
}
