package com.babacar.ucollaboration.UMarket.Activitys;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Adapters.RecyclerViewBien;
import com.babacar.ucollaboration.UMarket.Modeles.Bien;

import java.util.ArrayList;
import java.util.List;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;
import static com.babacar.ucollaboration.UMarket.Fragments.FragmentAcceuil.sBienList;


public class ListeUser extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private String typeListe;
    private TextView mTitreListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umarket_activity_liste_user);

        referenceWidgets(); // Méthode pour référencer les widgets du layout "umarket_activity_liste_user".
        typeListe = getIntent().getStringExtra("TASK");
        recycler(); // Méthode permettant d'afficher la liste des biens favoris de l'utilisateur courrant.
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        mTitreListe = findViewById(R.id.titreListeUser);
        mRecyclerView = findViewById(R.id.avis_recycleView);
    }

    /**
     * Gére les proposions de même catégorie que le bien séléctionné.
     */
    private void recycler() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecyclerViewBien(getApplicationContext(), convertStringToBienObject()));
    }

    /**
     * Permet de convertir une liste de String en liste d'objet Bien.
     * @return
     */
    private List<Bien> convertStringToBienObject() {

        List<Bien> list = new ArrayList<>();

        switch (typeListe) {
            case "listeEnvie":
                mTitreListe.setText("Mes Biens Favoris");
                for (String bienString : sCurrentUser.getFavorie()) {
                    Bien bien = getBienById(bienString);
                    list.add(bien);
                }
                break;
            case "listeVente":
                mTitreListe.setText("Mes Ventes");
                for (Bien bien : sBienList) {

                    if (bien.getVendeur().getIdEtu().equals(sCurrentUser.getIdEtu())) {

                        list.add(bien);
                    }
                }
                break;
            case "listeVenteEnCours":
                mTitreListe.setText("Ventes en cours");
            /*for (Bien bien : sBienList) {

                if (bien.getVendeur().getIdEtu().equals(sCurrentUser.getIdEtu())) {

                    list.add(bien);
                }
            }*/
                break;
        }

        return list;
    }

    /**
     * Permet de récupérer le bien corréspondant à l'id bien du panier passé en paramétre.
     * @param idBien
     * @return
     */
    public static Bien getBienById(String idBien) {

        Bien bien = new Bien();
        for (Bien bien1 : sBienList) {

            if (bien1.getIdBien().equals(idBien)) {

                bien = bien1;
                break;
            }
        }
        Log.d("BIUUHNJ", bien.toString());
        return bien;
    }

}
