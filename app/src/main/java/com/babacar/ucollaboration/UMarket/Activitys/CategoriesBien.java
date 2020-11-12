package com.babacar.ucollaboration.UMarket.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Adapters.RecyclerviewCategorieBien;
import com.babacar.ucollaboration.UMarket.Modeles.Bien;

import java.util.ArrayList;
import java.util.List;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.mBiensNvendu;

public class CategoriesBien extends AppCompatActivity {

    private TextView mTitre;
    private RecyclerView mRecyclerView;
    private List<Bien> mList_biens;
    private String mStr_titre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umarket_activity_categories_bien);

        mList_biens = new ArrayList<>(10);
        referenceWidgets(); // Méthode permettant de référencer les widgets.
        mStr_titre = getIntent().getStringExtra("CategorieBien");
        mTitre.setText(mStr_titre);
    }

    @Override
    protected void onStart() {
        super.onStart();

        /* Liste de bien de catégorie quelconque */
        getBienByCategorie(); // Méthode permettant de récupérer les biens de catégorie spécifiée.

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecyclerviewCategorieBien(this, mList_biens));
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        this.mTitre = findViewById(R.id.umarket_titreCategorie);
        this.mRecyclerView = findViewById(R.id.categBien_recycleview);
    }

    /**
     * Permet de récupérer les biens de catégorie spécifiée.
     */
    private void getBienByCategorie() {

        mList_biens.clear();
        for (Bien bien :
                mBiensNvendu) {

            if (bien.getCategorie().equalsIgnoreCase(mStr_titre)) {

                mList_biens.add(bien);
            }

        }
    }

}
