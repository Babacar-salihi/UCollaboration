package com.babacar.ucollaboration.Globals.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babacar.ucollaboration.Globals.Models.Etudiant;
import com.babacar.ucollaboration.Globals.Models.FonctionnaliteCompte;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.Globals.Adapters.RecyclerViewCompte;
import com.babacar.ucollaboration.UMarket.Modeles.DetailsPrestation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.getDetailById;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;

public class UserSpace extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Button mBntConnex, mBtnInscrip;

    private CircleImageView mUserProfilPic;
    private TextView mUserName, mNbArtVendu, mNbArtAchat, mNbArtLike;
    private TextView mTextArtVendu, mTextArtAchat, mTextArtLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_space);

        referenceWidgets(); // Méthode pour référencer les widgets de fonctCompte
        inflateFonct(); // Méthode pour adapter le recycleView.
        pageAuth(); // Méthode pour acceder à la page de connexion ou d'inscription.
        toggleConnex(); // Méthode pour cacher ou faire apparaitre la partie authentification. (Bouton connexion et inscription).
        if(sCurrentUser != null){
            remplirInfoUser(); // Méthode pour remplir les informations de l'utilisateur connecté.
        }
    }

    /**
     * Permet de référencer les widgets de l'adapter fonctCompte.
     */
    private void referenceWidgets(){

        this.mRecyclerView = findViewById(R.id.compte_recycleView);
        this.mBntConnex = findViewById(R.id.compte_btnConnex);
        this.mBtnInscrip = findViewById(R.id.compte_btnInscript);

        this.mUserProfilPic = findViewById(R.id.compte_userProfilPic);
        this.mUserName =  findViewById(R.id.compte_userName);

        this.mNbArtVendu = findViewById(R.id.compte_articleVendu);
        this.mNbArtAchat = findViewById(R.id.compte_articleAchete);
        this.mNbArtLike = findViewById(R.id.compte_articleLike);

        this.mTextArtVendu = findViewById(R.id.compte_texteArtVendu);
        this.mTextArtAchat = findViewById(R.id.compte_texteArtAchat);
        this.mTextArtLike = findViewById(R.id.compte_texteArtLike);
    }

    /**
     * Permet d'afficher les fonctionnalité dans le RecyclerView.
     */
    private void inflateFonct(){

        List<FonctionnaliteCompte> list = new ArrayList<>();

        list.add(new FonctionnaliteCompte("ic_liste_vente", "Mes Achats et ventes"));
        list.add(new FonctionnaliteCompte("favorite_card", "Ma liste d'envis"));
        /* Compte bosseur */
        list.add(new FonctionnaliteCompte("bosseur", "Compte bosseur"));
        list.add(new FonctionnaliteCompte("modifier", "Gérer mon compte"));
        list.add(new FonctionnaliteCompte("deconnex", "Déconnexion"));
        list.add(new FonctionnaliteCompte("supprimer", "Supprimer mon compte"));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false );
        RecyclerViewCompte recyclerViewBien = new RecyclerViewCompte(this,list);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(recyclerViewBien);

    }

    /**
     * Permet à un utilisateur d'acceder à la page de connexion ou d'inscription.
     */
    private void pageAuth(){

        // Clique sur le bouton connexion.
        mBntConnex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Connexion.class));
            }
        });

        // Clique sur le bouton inscription.
        mBtnInscrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Inscription.class));
            }
        });
    }

    /**
     * Permet de cacher ou d'afficher le layout contenant " Connexion et Inscription" et la listView
     */
    private void toggleConnex(){

        LinearLayout layout = findViewById(R.id.compte_layoutConnex);
        if(sCurrentUser != null){
            layout.setVisibility(View.GONE); // On cache la partie "connexion inscription" s'il y a un utilisateur connecté.
            mRecyclerView.setEnabled(true);
        }
        else {
            layout.setVisibility(View.VISIBLE); // On affiche la partie "connexion inscription" s'il n'y a pas d'utilisateur connecté.
            mRecyclerView.setEnabled(false);
        }
    }

    // TODO: NBvente, ... dans etudiant.
    /**
     * Permet d'afficher les informations de l'utilisateur s'il est connecté.
     */
    public void remplirInfoUser(){

        int nbVente = 0;
        int nbAchat = 0;
        for (String details : sCurrentUser.getDetailsPrestations()) {

            DetailsPrestation detailsPrestation = getDetailById(details);
            if (detailsPrestation.getVendeur().equals(sCurrentUser.getIdEtu())) // Vendeur
                ++nbVente;
            else
                ++nbAchat;
        }

        if(sCurrentUser.getPhoto() != null)
            Glide.with(getApplicationContext())
                .load(sCurrentUser.getPhoto())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mUserProfilPic);
        this.mUserName.setText(sCurrentUser.getPrenomEtu()+" "+sCurrentUser.getNomEtu());

        if(nbVente != 0)
            this.mNbArtVendu.setText(String.valueOf(nbVente));
        if (nbAchat != 0)
            this.mNbArtAchat.setText(String.valueOf(nbAchat));
        this.mNbArtLike.setText(String.valueOf(sCurrentUser.getFavorie().size()));

        if (Integer.parseInt(mNbArtVendu.getText().toString()) > 1)
            mTextArtVendu.setText("articles vendus");
        if (Integer.parseInt(mNbArtAchat.getText().toString()) > 1)
            mTextArtAchat.setText("articles achetés");
        if (Integer.parseInt(mNbArtLike.getText().toString()) > 1)
            mTextArtLike.setText("articles aimés");
    }
}
