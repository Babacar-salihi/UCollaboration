package com.babacar.ucollaboration.UService.Models;

import com.babacar.ucollaboration.Globals.Models.Etudiant;

public class Bosseur extends Etudiant {

    private int nbStart;
    private String mCategorieSocioProf;
    private boolean mOnLine; // Le bosseur est en ligne ou pas.


    public Bosseur() {

    }

    public Bosseur(String idBosseur, int nbStart, String categorieSocioProf, boolean onLine) {
        super.mIdEtu = idBosseur;
        this.nbStart = nbStart;
        this.mCategorieSocioProf = categorieSocioProf;
        this.mOnLine = onLine;
    }

    public Bosseur(String idEtu, String prenom, String nom, String email, int numTel, String categorieSocioProf) {
        super.mIdEtu = idEtu;
        super.mPrenomEtu = prenom;
        super.mNomEtu = nom;
        super.mEmail = email;
        super.mNumTelephoneEtu = numTel;
        this.mCategorieSocioProf = categorieSocioProf;
    }

    public int getNbStart() {
        return nbStart;
    }

    public void setNbStart(int nbStart) {
        this.nbStart = nbStart;
    }

    public String getCategorieSocioProf() {
        return mCategorieSocioProf;
    }

    public void setCategorieSocioProf(String categorieSocioProf) {
        mCategorieSocioProf = categorieSocioProf;
    }

    public boolean isOnLine() {
        return mOnLine;
    }

    public void setOnLine(boolean onLine) {
        this.mOnLine = onLine;
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
                ", mFaculte=" + mFaculte +
                '}';
    }
}
