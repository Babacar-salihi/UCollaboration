package com.babacar.ucollaboration.UService.Models;

import com.babacar.ucollaboration.Globals.Models.Etudiant;

public class Bosseur extends Etudiant {

    private int nbStart;

    public Bosseur() {

    }

    public Bosseur(String idBosseur, int nbStart) {
        super.mIdEtu = idBosseur;
        this.nbStart = nbStart;
    }

    public Bosseur(String idEtu, String prenom, String nom, String email, int numTel, String categorieSocioProf) {
        super.mIdEtu = idEtu;
        super.mPrenomEtu = prenom;
        super.mNomEtu = nom;
        super.mEmail = email;
        super.mNumTelephoneEtu = numTel;
        super.mCategorieSocioProf = categorieSocioProf;
    }

    public int getNbStart() {
        return nbStart;
    }

    public void setNbStart(int nbStart) {
        this.nbStart = nbStart;
    }

    @Override
    public String toString() {
        return "Bosseur{" +
                "nbStart=" + nbStart +
                ", mIdEtu='" + mIdEtu + '\'' +
                ", mNomEtu='" + mNomEtu + '\'' +
                ", mPrenomEtu='" + mPrenomEtu + '\'' +
                ", mNumTelephoneEtu=" + mNumTelephoneEtu +
                ", mNumChambre='" + mNumChambre + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mAdresse='" + mAdresse + '\'' +
                ", mCategorieSocioProf='" + mCategorieSocioProf + '\'' +
                ", mPhoto='" + mPhoto + '\'' +
                ", mNewAdresse='" + mNewAdresse + '\'' +
                ", mPanier=" + mPanier +
                ", mFavorie=" + mFavorie +
                ", mDetailsPrestationsEtu=" + mDetailsPrestationsEtu +
                ", mDepartement=" + mDepartement +
                '}';
    }
}
