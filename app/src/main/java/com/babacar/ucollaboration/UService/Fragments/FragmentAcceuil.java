package com.babacar.ucollaboration.UService.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UService.Adapters.RecyclerView_Carte_Acceuil;
import com.babacar.ucollaboration.UService.Models.Bosseur;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAcceuil extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;

    public FragmentAcceuil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.uservice_fragment_acceuil, container, false);

        referenceWidgets(); // Méthode pour rérérencer les widgets.
        inflater(); // Méthode pour remplir les vues.


        return mView;
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        this.mRecyclerView = mView.findViewById(R.id.uservice_recyclerview_acceuil);

    }

    /**
     * Permet de remplir le recyclerView
     */
    private void inflater() {

        List<Bosseur> bosseurs = new ArrayList<>(5);
        bosseurs.add(new Bosseur("steve","Babacar","Ndong","Maintenancier","Fass Delorme","ndongbabacar100@gmail.com",781401217));
        bosseurs.add(new Bosseur("steve","Fallou","Ndong","Taiteur de ic_texte","Pavillon B","ndongfallilougmail.com",709809098));
        bosseurs.add(new Bosseur("steve","Fatou","Fall","Coiffeur","PM","fallfatousha@gmail.com",780090988));
        bosseurs.add(new Bosseur("steve","Babacar","Ndong","Maintenancier","Fass Delorme","ndongbabacar100@gmail.com",781401217));
        bosseurs.add(new Bosseur("steve","Fallou","Ndong","Taiteur de ic_texte","Pavillon B","ndongfallilougmail.com",709809098));
        bosseurs.add(new Bosseur("steve","Fatou","Fall","Coiffeur","PM","fallfatousha@gmail.com",780090988));


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        this.mRecyclerView.setLayoutManager(layoutManager);
        this.mRecyclerView.setAdapter(new RecyclerView_Carte_Acceuil(mView.getContext(), bosseurs));
    }

}
