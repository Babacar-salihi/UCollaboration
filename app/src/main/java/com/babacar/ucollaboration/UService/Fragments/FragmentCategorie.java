package com.babacar.ucollaboration.UService.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.babacar.ucollaboration.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategorie extends Fragment implements View.OnClickListener {

    private View mView;
    private CardView mInfoGraph, mProf, mTexte, mInscript, mPhotoMin, mMaintenance, mCoiffure, mCv;


    public FragmentCategorie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.uservice_fragment_categorie, container, false);

        referencerWidget(); // Méthode permttant de référencer les widgets.

        mInfoGraph.setOnClickListener(this);
        mProf.setOnClickListener(this);
        mTexte.setOnClickListener(this);
        mInscript.setOnClickListener(this);
        mPhotoMin.setOnClickListener(this);
        mMaintenance.setOnClickListener(this);
        mCoiffure.setOnClickListener(this);
        mCv.setOnClickListener(this);

        return mView;
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referencerWidget() {

        this.mInfoGraph = mView.findViewById(R.id.uservice_infoGraphie);
        this.mProf = mView.findViewById(R.id.uservice_EtuProf);
        this.mTexte = mView.findViewById(R.id.uservice_traiteTexte);
        this.mInscript = mView.findViewById(R.id.uservice_inscripOnLine);
        this.mPhotoMin = mView.findViewById(R.id.uservice_photoMin);
        this.mMaintenance = mView.findViewById(R.id.uservice_maintenance);
        this.mCoiffure = mView.findViewById(R.id.uservice_coiffure);
        this.mCv = mView.findViewById(R.id.uservice_cv);
    }

    /**
     * Permet de gérer les clics sur les itemCategories.
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.uservice_infoGraphie:
                Toast.makeText(mView.getContext(), "Infographie", Toast.LENGTH_SHORT).show();
                break;
            case R.id.uservice_EtuProf:
                Toast.makeText(mView.getContext(), "Professeur", Toast.LENGTH_SHORT).show();
                break;
            case R.id.uservice_traiteTexte:
                Toast.makeText(mView.getContext(), "Texte", Toast.LENGTH_SHORT).show();
                break;
            case R.id.uservice_inscripOnLine:
                Toast.makeText(mView.getContext(), "Inscription en ligne", Toast.LENGTH_SHORT).show();
                break;
            case R.id.uservice_photoMin:
                Toast.makeText(mView.getContext(), "Photo minutes", Toast.LENGTH_SHORT).show();
                break;
            case R.id.uservice_maintenance:
                Toast.makeText(mView.getContext(), "Maintenance", Toast.LENGTH_SHORT).show();
                break;
            case R.id.uservice_coiffure:
                Toast.makeText(mView.getContext(), "Coiffure", Toast.LENGTH_SHORT).show();
                break;
            case R.id.uservice_cv:
                Toast.makeText(mView.getContext(), "Curriculum Vitae", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
