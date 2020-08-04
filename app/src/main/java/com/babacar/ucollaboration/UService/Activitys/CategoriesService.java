package com.babacar.ucollaboration.UService.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UService.Adapters.RecyclerView_Carte_Acceuil;
import com.babacar.ucollaboration.UService.Models.Bosseur;

import java.util.ArrayList;
import java.util.List;

import static com.babacar.ucollaboration.UService.Fragments.FragmentAcceuil.sBosseurs;

public class CategoriesService extends AppCompatActivity {

    private TextView mTitreCategorie;
    private RecyclerView mRecyclerView;
    private List<Bosseur> mBosseurList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uservce_activity_categories_service);

        mBosseurList = new ArrayList<>(10);
        referenceWidget(); // Méthode permettant de référencer les widgets.
        String titre = getIntent().getStringExtra("Categ");
        if (titre.length() != 0)
            mTitreCategorie.setText(titre);
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidget() {

        this.mTitreCategorie = findViewById(R.id.titreCategorie);
        this.mRecyclerView = findViewById(R.id.categBosseur_recycleview);
    }


    @Override
    protected void onStart() {
        super.onStart();

        mBosseurList.clear();
        for (Bosseur bosseur : sBosseurs) {

            if (bosseur.getCategorieSocioProf().contains(mTitreCategorie.getText())) {

                mBosseurList.add(bosseur);
            }
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecyclerView_Carte_Acceuil(getApplicationContext(), mBosseurList));
    }
}
