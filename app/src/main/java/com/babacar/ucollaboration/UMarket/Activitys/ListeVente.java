package com.babacar.ucollaboration.UMarket.Activitys;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Adapters.RecyclerViewDetails;
import com.babacar.ucollaboration.UMarket.Modeles.Bien;
import com.babacar.ucollaboration.UMarket.Modeles.DetailsPrestation;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;

import static com.babacar.ucollaboration.UMarket.Activitys.ListeUser.getBienById;

public class ListeVente extends AppCompatActivity {

    private RecyclerView mRecyclerViewVenteEnCours;
    private RecyclerView mRecyclerViewVenteAchevee;
    private RecyclerView mRecyclerViewAchatEnCours;
    private RecyclerView mRecyclerViewAchatAcheves;

    private static List<DetailsPrestation> mListEnCours; // Liste des ventes en cours de l'utilisateur connecté.
    private static List<DetailsPrestation> mListAchevees; // Liste des ventes déjà achevées de l'utilisateur connecté.
    private static List<DetailsPrestation> mListAchatsEnCours; // Liste des achats en cours de l'utilisateur connecté.
    private static List<DetailsPrestation> mListAchatsAcheves; // Liste des achats déjà achevées de l'utilisateur connecté.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umarket_activity_liste_vente);

        referenceWidgets(); // Méthodes pour référencer les widgets.
        mListEnCours = new ArrayList<>(10);
        mListAchevees = new ArrayList<>(10);
        mListAchatsEnCours = new ArrayList<>(10);
        mListAchatsAcheves = new ArrayList<>(10);
        //typeListe(); // Méthode pour distributer les ventes du vendeur selon qu'elle est en cours ou déja achevée.
        listeVentes(); // Méthode pour distributer les ventes du vendeur selon qu'elle est en cours ou déja achevée.
        infalterVenteEnCours(); // Méthode pour inflater le RecyclerView Ventes en cours.
    }

    /*@Override
    protected void onStart() {
        super.onStart();

        DataBase.sReference.child("Details Prestations")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot detailsSnapshot :
                             dataSnapshot.getChildren()) {

                            DetailsPrestation details = detailsSnapshot.getValue(DetailsPrestation.class);
                            Log.d("Detaittili", "\n"+details.toString());
                            if (sCurrentUser.getIdEtu().equals(details.getAcheteur())) { // Si ca a rapport avec l'utilisateur courrant.

                                sCurrentUser.getDetailsPrestations().add(new Gson().toJson(details));
                            }
                        }

                        *//*mRecyclerViewAchatEnCours.notify();
                        mRecyclerViewAchatAcheves.notify();*//*
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                })
        ;

        listeVentes(); // Méthode pour distributer les ventes du vendeur selon qu'elle est en cours ou déja achevée.
        infalterVenteEnCours(); // Méthode pour inflater le RecyclerView Ventes en cours.
    }*/

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        this.mRecyclerViewVenteEnCours = findViewById(R.id.recyclerVenteEnCours);
        this.mRecyclerViewVenteAchevee = findViewById(R.id.recyclerVentesAchevees);
        this.mRecyclerViewAchatEnCours = findViewById(R.id.recyclerListAchatsEnCours);
        this.mRecyclerViewAchatAcheves = findViewById(R.id.recyclerListAchastAcheves);
    }

    /**
     * Permet d'infalter le RecyclerView Ventes en cours.
     */
    private void infalterVenteEnCours() {

        // ====================== Liste des Achats en cours=========================
        RecyclerViewDetails adapterAchatsEnCours = new RecyclerViewDetails(getApplicationContext(), mListAchatsEnCours);
        RecyclerView.LayoutManager layoutManagerAchatsEnCours = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerViewAchatEnCours.setLayoutManager(layoutManagerAchatsEnCours);
        mRecyclerViewAchatEnCours.setAdapter(adapterAchatsEnCours);

        // ====================== Liste des Achats achevés =========================
        RecyclerViewDetails adapterAchatsAcheves = new RecyclerViewDetails(getApplicationContext(), mListAchatsAcheves);
        RecyclerView.LayoutManager layoutManagerAchat = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerViewAchatAcheves.setLayoutManager(layoutManagerAchat);
        mRecyclerViewAchatAcheves.setAdapter(adapterAchatsAcheves);

        // ====================== Liste des ventes en cours =========================
        RecyclerViewDetails adapterEnCours = new RecyclerViewDetails(getApplicationContext(), mListEnCours);
        RecyclerView.LayoutManager layoutManagerEnCours = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerViewVenteEnCours.setLayoutManager(layoutManagerEnCours);
        mRecyclerViewVenteEnCours.setAdapter(adapterEnCours);


        // ====================== Liste des ventes achevées =========================
        RecyclerViewDetails adapterAchvee = new RecyclerViewDetails(getApplicationContext(), mListAchevees);
        RecyclerView.LayoutManager layoutManagerAchevee = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerViewVenteAchevee.setLayoutManager(layoutManagerAchevee);
        mRecyclerViewVenteAchevee.setAdapter(adapterAchvee);

        /*ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSimpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerViewAchatAcheves);*/
    }

    /**
     * Permet de distribuer les details aux deux listes de détails que sont : ventes achevées et ventes en cours.
     */
    public static void listeVentes() {

        for (String details : sCurrentUser.getDetailsPrestations()) {

            // Details user, joint au noeud DetailsPrestation
            DetailsPrestation detailsPrestation = DataBase.getDetailById(details);

            Log.d("DetailllllListe",detailsPrestation.toString());
            if (detailsPrestation != null) {

                Bien bien = getBienById(detailsPrestation.getBiens());

                if (bien.getVendeur().getIdEtu().equals(sCurrentUser.getIdEtu())) { // Si le produit est vendu par l'utilisateur connecté.

                    if (detailsPrestation.isAchatAcheve()) // Si la vente est déjà réglée.
                        mListAchevees.add(detailsPrestation);
                    else                                   // Vente pas encore réglée.
                        mListEnCours.add(detailsPrestation);
                } else { // Le bien n'est pas vendu par l'utilisateur connecter donc il la acheté.

                    if (detailsPrestation.isAchatAcheve()) // Achats déjà réglés.
                        mListAchatsAcheves.add(detailsPrestation);
                    else                                   // Achats pas encore réglés.
                        mListAchatsEnCours.add(detailsPrestation);
                }
            }
        }
    }

    /*ItemTouchHelper.SimpleCallback mSimpleCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            switch (direction) {

                case ItemTouchHelper.LEFT :
                    viewHolder.getAdapterPosition();
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            new RecyclerViewSwipeDecorator.Builder(ListeVente.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftActionIcon(R.drawable.ic_facture)
                    .addSwipeLeftBackgroundColor(R.color.appColor)
                    .create()
                    .decorate();
        }
    };*/

}
