package com.babacar.ucollaboration.UMarket.Modeles;


import com.babacar.ucollaboration.Globals.Models.Etudiant;

import java.util.List;

public class Vendeur extends Etudiant {

    private List<Bien> bien;

    public Vendeur() {
    }

    public Vendeur(String prenom, String nom,String id, int tel, String pp,String adr){ // Utiliser lors de la publication d'un bien.

        super.setPrenomEtu(prenom);
        super.setNomEtu(nom);
        super.setIdEtu(id);
        super.setNumTelephoneEtu(tel);
        super.setPhoto(pp);
        super.setNewAdresse(adr);
    }

    public Vendeur(String nom, String prenom, int tel, String adr ){

        super.setNomEtu(nom);
        super.setPrenomEtu(prenom);
        super.setNumTelephoneEtu(tel);
        super.setNewAdresse(adr);
    }

    public List<Bien> getBien() {
        return bien;
    }

    public void setBien(List<Bien> bien) {
        this.bien = bien;
    }

    @Override
    public String toString() {

        return super.toString() +
                "bien=" + bien +
                '}';
    }
}
