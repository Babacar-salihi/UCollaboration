package com.babacar.ucollaboration.UMarket.Activitys;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Adapters.RecyclerViewBien;
import com.babacar.ucollaboration.UMarket.Adapters.RecyclerViewCategorie;

public class MemeCategorie extends AppCompatActivity {

    private RecyclerView mRecyclerViewCateg;
    private TextView mTitreActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umarket_activity_meme_categorie);

        referenceWidgets(); // Permet de référencer les widgets.
        inflateLayout(); // Méthode pour inflatter.
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        mRecyclerViewCateg = findViewById(R.id.recyclerMemeCateg);
        mTitreActivity = findViewById(R.id.memeCategTitre);
    }

    /**
     * Permet d'afficher les biens de même categorie.
     */
    private void inflateLayout() {

        mTitreActivity.setText(RecyclerViewCategorie.categ);
        RecyclerViewBien recyclerViewBien = new RecyclerViewBien(getApplicationContext(), RecyclerViewCategorie.mBienCateg);
        LinearLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerViewCateg.setLayoutManager(layoutManager);
        mRecyclerViewCateg.setAdapter(recyclerViewBien);
    }
}
