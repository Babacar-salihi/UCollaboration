package com.babacar.ucollaboration.UMarket.Modeles;


import java.io.Serializable;

public class Panier implements Serializable {

    private String idPanier;
    private String mBiens; // L'ensemble des biens du panier
    private int mQuantiteAchat; // Nombre d'article ajouté.
    private int mSommeTotale; // Somme totale des biens ajoutés.
    private long mTimeToExpire; // Delai pour acheter le bien.

    public Panier() {
    }

    public Panier(String idPanier, String biens, int quantiteAchat, int sommeTotale, long timeToExpire) {
        this.idPanier = idPanier;
        mBiens = biens;
        mQuantiteAchat = quantiteAchat;
        mSommeTotale = sommeTotale;
        this.mTimeToExpire = timeToExpire;
    }

    public String getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(String idPanier) {
        this.idPanier = idPanier;
    }

    public String getBiens() {
        return mBiens;
    }

    public void setBiens(String biens) {
        mBiens = biens;
    }

    public int getQuantiteAchat() {
        return mQuantiteAchat;
    }

    public void setQuantiteAchat(int quantiteAchat) {
        mQuantiteAchat = quantiteAchat;
    }

    public int getSommeTotale() {
        return mSommeTotale;
    }

    public void setSommeTotale(int sommeTotale) {
        mSommeTotale = sommeTotale;
    }

    public long getTimeToExpire() {
        return mTimeToExpire;
    }

    public void setTimeToExpire(long timeToExpire) {
        mTimeToExpire = timeToExpire;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "idPanier='" + idPanier + '\'' +
                ", mBiens='" + mBiens + '\'' +
                ", mQuantiteAchat=" + mQuantiteAchat +
                ", mSommeTotale=" + mSommeTotale +
                ", DateExpiration='" + mTimeToExpire + '\'' +
                '}';
    }
}
