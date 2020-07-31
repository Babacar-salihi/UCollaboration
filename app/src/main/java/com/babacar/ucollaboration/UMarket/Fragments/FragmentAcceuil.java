package com.babacar.ucollaboration.UMarket.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.Globals.Models.Categories;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Activitys.MainActivity;
import com.babacar.ucollaboration.UMarket.Activitys.Panier;
import com.babacar.ucollaboration.UMarket.Adapters.RecyclerViewBien;
import com.babacar.ucollaboration.UMarket.Adapters.RecyclerViewCategorie;
import com.babacar.ucollaboration.UMarket.Modeles.Bien;
import com.babacar.ucollaboration.UMarket.Modeles.DetailsPrestation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.mBiensNvendu;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sBienList;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAcceuil extends Fragment {

    private View mView;
    private SearchView mSearchBar;
    private RecyclerView mRecyclerView, mRecyclerViewCateg;
    private ImageView mIconPanier;
    public TextView mNbArticlePanier;


    public FragmentAcceuil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.umarket_fragment_acceuil, container, false);

        referenceWidgets(); // Méthode pour référencer les widgets de l'adapter catalogue.
        //inflateCatalogue(); // Méthide pour afficher les produits dans le RecyclerView.
        monPanier(); // Méthode pour accéder au panier de l'utilisateur.
        recherche(); // Méthode pour gérer la recherche d'article.

        Button btnTeste = mView.findViewById(R.id.teste);
        btnTeste.setVisibility(View.VISIBLE);
        btnTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(new Intent(getContext(), Facture.class));*/
                if (sCurrentUser != null){

                    sCurrentUser.getDetailsPrestations().clear();
                    sCurrentUser.getPanier().clear();
                    sCurrentUser.getFavorie().clear();
                }
            }
        });

        return mView;
    }

    /**
     * Permet de référencer les widgets de l'adapter catalogue.
     */
    private void referenceWidgets(){

        this.mSearchBar = mView.findViewById(R.id.catalogue_barRecherche);
        this.mNbArticlePanier = mView.findViewById(R.id.catalogue_nbArticlePanier);
        this.mIconPanier = mView.findViewById(R.id.catalogue_panierUser);
        this.mRecyclerView = mView.findViewById(R.id.catalague_recyclerProduit);
        this.mRecyclerViewCateg = mView.findViewById(R.id.catalague_recyclerCategorie);
    }

    /**
     * Permet de faire les différents traitements.
     */
    @Override
    public void onStart() {
        super.onStart();

        inflateCategories(); // Méthode pour inflater la liste de categorie.
        DataBase.sReference.child("Biens")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sBienList.clear();
                mBiensNvendu.clear();
                for(DataSnapshot bienSnapshot : dataSnapshot.getChildren()){
                    Bien bien = bienSnapshot.getValue(Bien.class);
                    Log.d("CurrentBien", bien.toString());
                    sBienList.add(bien); // Liste contenant tous les biens de la base.

                    if(bien.getEtatBien() == 0 && bien.isActiver()) // Si le bien n'est pas encore vendu on l'affiche.
                        mBiensNvendu.add(bien); // Liste contenant les biens toujours disponible.
                }
                RecyclerViewBien adapter = new RecyclerViewBien(getContext(), mBiensNvendu);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                mRecyclerView.setAdapter(adapter);

                if (sCurrentUser != null)
                    mNbArticlePanier.setText(sCurrentUser.getPanier().size()+"");
                else
                    mNbArticlePanier.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Permet d'inflater le RecycleView Categorie de l'acceuil.
     */
    private void inflateCategories(){

        List<Categories> list = new ArrayList<>(10);
        list.add(new Categories("docs","Document"));
        list.add(new Categories("tickets","Ticket"));
        list.add(new Categories("high_tech","High-Tech"));
        list.add(new Categories("mode","Fashion"));

        RecyclerViewCategorie adapter = new RecyclerViewCategorie(getContext(), list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewCateg.setLayoutManager(layoutManager);
        mRecyclerViewCateg.setAdapter(adapter);

    }

    /**
     * Permet à l'utilisateur d'accéder à son panier.
     */
    private void monPanier(){

        mIconPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), Panier.class));
            }
        });
    }

    /**
     * S'occuper de la recherche d'article.
     */
    private void recherche() {

        mSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                List<Bien> list = new ArrayList<>();

                for (Bien bien : mBiensNvendu){
                    Log.d("vendeurrr", bien.getVendeur().getNewAdresse());
                    if (bien.getLibelle().toLowerCase().contains(query.trim().toLowerCase())  // Recherche selon le nom, l'adresse du vendeur,
                            || bien.getVendeur().getNewAdresse().equalsIgnoreCase(query.trim())) { // Si c'est l'adresse du vendeur il faut tout écrire.
                        Log.d("vendeurrrr", "BIEN_TROUVER");
                        list.add(bien);
                    }
                }
                mRecyclerView.setAdapter(new RecyclerViewBien(getContext(), list));

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText.trim())) {

                    startActivity(new Intent(getContext(), MainActivity.class));
                }
                return false;
            }

        });
    }
}
