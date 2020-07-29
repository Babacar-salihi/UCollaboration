package com.babacar.ucollaboration.UService.Models;

public class Bosseur {

    private String mPP;
    private String mPrenom, nom;
    private String Prefession;

    private String mAdresse , mEmail;
    private int mNumTel;

    private int nbStart;

    public Bosseur() {

    }

    public Bosseur(String PP, String prenom, String nom, String prefession, String adresse, String email, int numTel) {
        mPP = PP;
        mPrenom = prenom;
        this.nom = nom;
        Prefession = prefession;
        mAdresse = adresse;
        mEmail = email;
        mNumTel = numTel;
    }

    public String getPP() {
        return mPP;
    }

    public void setPP(String PP) {
        mPP = PP;
    }

    public String getPrenom() {
        return mPrenom;
    }

    public void setPrenom(String prenom) {
        mPrenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrefession() {
        return Prefession;
    }

    public void setPrefession(String prefession) {
        Prefession = prefession;
    }

    public String getAdresse() {
        return mAdresse;
    }

    public void setAdresse(String adresse) {
        mAdresse = adresse;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public int getNumTel() {
        return mNumTel;
    }

    public void setNumTel(int numTel) {
        mNumTel = numTel;
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
                "mPP='" + mPP + '\'' +
                ", mPrenom='" + mPrenom + '\'' +
                ", nom='" + nom + '\'' +
                ", Prefession='" + Prefession + '\'' +
                ", mAdresse='" + mAdresse + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mNumTel=" + mNumTel +
                '}';
    }
}
