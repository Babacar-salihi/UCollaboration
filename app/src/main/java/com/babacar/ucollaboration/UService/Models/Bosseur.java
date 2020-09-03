package com.babacar.ucollaboration.UService.Models;


import com.babacar.ucollaboration.Globals.Models.Etudiant;

public class Bosseur extends Etudiant {

    private String mCategorieSocioProf;
    private boolean mOnLine; // Le bosseur est en ligne ou pas.
    private long mNCI;
    private boolean mEmplacement; // L'emplacement du bosseur.

    public Bosseur() {

    }

    public Bosseur(String idBosseur, int nbStart, String categorieSocioProf, long nci, boolean onLine) {
        super.mIdEtu = idBosseur;
        this.mCategorieSocioProf = categorieSocioProf;
        this.mNCI = nci;
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

    public long getNCI() {
        return mNCI;
    }

    public void setNCI(long NCI) {
        mNCI = NCI;
    }

    public boolean isEmplacement() {
        return mEmplacement;
    }

    public void setEmplacement(boolean emplacement) {
        mEmplacement = emplacement;
    }

    @Override
    public String toString() {
        return "Bosseur{" +
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
                ", mDetailsPrestationsEtu=" + mDetailsPrestations +
                ", mFaculte=" + mFaculte +
                '}';
    }
}
