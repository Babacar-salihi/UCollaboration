package com.babacar.ucollaboration.UMarket.Modeles;

import com.babacar.ucollaboration.Globals.Models.Etudiant;

import java.util.ArrayList;
import java.util.List;

public class Acheteur extends Etudiant {

    private List<String> mDetailsPrestations = new ArrayList<>();

    public Acheteur(){}

    public List<String> getDetailsPrestations() {
        return mDetailsPrestations;
    }

    public void setDetailsPrestations(List<String> detailsPrestations) {
        mDetailsPrestations = detailsPrestations;
    }

    @Override
    public String toString() {
        return "Acheteur{" +
                "mDetailsPrestations=" + mDetailsPrestations +
                '}';
    }
}
