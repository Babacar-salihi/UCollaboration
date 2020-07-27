package com.babacar.ucollaboration.UMarket.Modeles;

import com.babacar.ucollaboration.Globals.Models.Etudiant;

import java.util.List;

public class Livreur extends Etudiant {

    private List<String> mDetailsPrestations;
    private int mNbBienLiv; // Nombre de bien livré

    public Livreur(){}

    public Livreur(String prenom, String nom, int tel) {

        super.mPrenomEtu = prenom;
        super.mNomEtu = nom;
        super.mNumTelephoneEtu = tel;
    }

    public List<String> getDetailsPrestations() {
        return mDetailsPrestations;
    }

    public void setDetailsPrestations(List<String> detailsPrestations) {
        mDetailsPrestations = detailsPrestations;
    }

    @Override
    public String toString() {
        return "Livreur{" +
                "mDetailsPrestations=" + mDetailsPrestations +
                '}';
    }
}
