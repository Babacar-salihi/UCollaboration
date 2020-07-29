package com.babacar.ucollaboration.UService.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.Globals.Models.Etudiant;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UService.Adapters.RecyclerView_Carte_Acceuil;
import com.babacar.ucollaboration.UService.Models.Bosseur;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sRefUService;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAcceuil extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;
    private List<Bosseur> mBosseurs;

    public FragmentAcceuil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.uservice_fragment_acceuil, container, false);
        mBosseurs = new ArrayList<>(10);

        referenceWidgets(); // Méthode pour rérérencer les widgets.

        return mView;
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        this.mRecyclerView = mView.findViewById(R.id.uservice_recyclerview_acceuil);
    }

    @Override
    public void onStart() {
        super.onStart();

        sRefUService.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mBosseurs.clear();
                for (DataSnapshot bosseurSnapshot : dataSnapshot.getChildren()) {

                    final Bosseur bosseur = bosseurSnapshot.getValue(Bosseur.class);
                    sReference.child("Etudiants").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot etudientSnapshot : dataSnapshot.getChildren()) {

                                Etudiant etudiant = etudientSnapshot.getValue(Etudiant.class);
                                if (bosseur.getIdEtu().equals(etudiant.getIdEtu())) {

                                    bosseur.setPrenomEtu(etudiant.getPrenomEtu());
                                    bosseur.setNomEtu(etudiant.getNomEtu());
                                    bosseur.setNumTelephoneEtu(etudiant.getNumTelephoneEtu());
                                    bosseur.setEmail(etudiant.getEmail());
                                    bosseur.setPhoto(etudiant.getPhoto());
                                    bosseur.setNewAdresse(etudiant.getNewAdresse());
                                    bosseur.setCategorieSocioProf(etudiant.getCategorieSocioProf());
                                }
                            }
                            mBosseurs.add(bosseur);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                inflater(mBosseurs);
                /*mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(new RecyclerView_Carte_Acceuil(getContext(), mBosseurs));
*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Permet de remplir le recyclerView
     */
    private void inflater(List<Bosseur> bosseurs) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        this.mRecyclerView.setLayoutManager(layoutManager);
        this.mRecyclerView.setAdapter(new RecyclerView_Carte_Acceuil(getContext(), bosseurs));
    }

}
