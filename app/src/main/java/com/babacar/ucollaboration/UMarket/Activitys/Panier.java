package com.babacar.ucollaboration.UMarket.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Adapters.RecyclerViewPanier;
import com.babacar.ucollaboration.UMarket.Modeles.Bien;

import java.util.List;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;
import static com.babacar.ucollaboration.UMarket.Activitys.MainActivity.getBienById;


public class Panier extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayout mPanierVide;
    private Button mInspiration;
    private TextView mSommeTotale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umarket_activity_panier);

        referenceWidgets(); // Méthode pour référencer les widgets du panier.
        inflater(); // Méthode pour inflater la liste du panier de l'utilisateur.
        findInspi(); // Méthode permettant à l'utilisateur de trouver des idées d'achat.
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        this.mRecyclerView = findViewById(R.id.panier_recyclerView);
        this.mPanierVide = findViewById(R.id.panier_layoutVide);
        this.mInspiration = findViewById(R.id.panier_trouverIdee);
        this.mSommeTotale = findViewById(R.id.panier_sommeTotaleBiens);
    }

    /**
     * Permet de gonfler le panier.
     */
    private void inflater(){

        if (sCurrentUser != null && !sCurrentUser.getPanier().isEmpty()){
            RecyclerViewPanier adapter = new RecyclerViewPanier(getApplicationContext(), sCurrentUser.getPanier());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(adapter);
            this.mSommeTotale.setText(sommeTotale(sCurrentUser.getPanier())+"fcfa");
        } else {
            mPanierVide.setVisibility(View.VISIBLE);
        }

    }

    private int sommeTotale(List<com.babacar.ucollaboration.UMarket.Modeles.Panier> paniers) {

        int somme = 0;
        for (com.babacar.ucollaboration.UMarket.Modeles.Panier panier : paniers) {

            Bien bien = getBienById(panier);
            somme += (bien.getPrixBien()*panier.getQuantiteAchat());
        }
        return somme;
    }


    /**
     * Permet à l'utilisateur de trouver des idées d'achat, ceci lui afffiche tous les ventes flash du jour.
     */
    private void findInspi() {

        mInspiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Panier.this, "Inspiration", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
