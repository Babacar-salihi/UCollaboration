package com.babacar.ucollaboration.UMarket.Modeles;

import java.util.List;

public class DetailsPrestation {

    private String mIdDetail;
    private int mQuantite;
    private String mDateVente;
    private String mCodeLivraison; // QR CODE.
    private String mBiens;
    //private Acheteur mAcheteur;
    private String mAcheteur;
    private String mVendeur;
    private int mPrixAchat;
    private String dateRV; // Jour de rencontre du vendeur et de l'acheteur.
    private boolean estLivre; // Permet de savoir si le bien acheter avec l'option livrer ou pas.
    private boolean achatAcheve; // Permet de savoir si l'achat est reglé ou pas.

    private List<String> mLivreur;

    public DetailsPrestation() {

    }

    public DetailsPrestation(String idDetail, int quantite, String dateVente, String codeLivraison, String acheteur, List<String> livreur, String biens) {
        mIdDetail = idDetail;
        mQuantite = quantite;
        mDateVente = dateVente;
        mCodeLivraison = codeLivraison;
        mAcheteur = acheteur;
        mLivreur = livreur;
        mBiens = biens;
    }

    public String getIdDetail() {
        return mIdDetail;
    }

    public void setIdDetail(String idDetail) {
        mIdDetail = idDetail;
    }

    public int getQuantite() {
        return mQuantite;
    }

    public void setQuantite(int quantite) {
        mQuantite = quantite;
    }

    public String getDateVente() {
        return mDateVente;
    }

    public void setDateVente(String dateVente) { mDateVente = dateVente; }

    public String getCodeLivraison() {
        return mCodeLivraison;
    }

    public void setCodeLivraison(String codeLivraison) {
        mCodeLivraison = codeLivraison;
    }

    public String getAcheteur() {
        return mAcheteur;
    }

    public void setAcheteur(String acheteur) {
        mAcheteur = acheteur;
    }

    public List<String> getLivreur() {
        return mLivreur;
    }

    public void setLivreur(List<String> livreur) {
        mLivreur = livreur;
    }

    public String getBiens() {
        return mBiens;
    }

    public void setBiens(String biens) {
        mBiens = biens;
    }

    public String getDateRV() {
        return dateRV;
    }

    public void setDateRV(String dateRV) {
        this.dateRV = dateRV;
    }

    public int getPrixAchat() {
        return mPrixAchat;
    }

    public void setPrixAchat(int prixAchat) {
        mPrixAchat = prixAchat;
    }

    public boolean isEstLivre() {
        return estLivre;
    }

    public void setEstLivre(boolean estLivre) {
        this.estLivre = estLivre;
    }

    public boolean isAchatAcheve() {
        return achatAcheve;
    }

    public void setAchatAcheve(boolean achatAcheve) {
        this.achatAcheve = achatAcheve;
    }

    public String getVendeur() {
        return mVendeur;
    }

    public void setVendeur(String vendeur) {
        mVendeur = vendeur;
    }

    @Override
    public String toString() {
        return "DetailsPrestation{" +
                "mIdDetail='" + mIdDetail + '\'' +
                ", mQuantite=" + mQuantite +
                ", mDateVente='" + mDateVente + '\'' +
                ", mCodeLivraison='" + mCodeLivraison + '\'' +
                ", mBiens='" + mBiens + '\'' +
                ", mAcheteur='" + mAcheteur + '\'' +
                ", mPrixAchat=" + mPrixAchat +
                ", dateRV='" + dateRV + '\'' +
                ", estLivre=" + estLivre +
                ", achatAcheve=" + achatAcheve +
                ", mLivreur=" + mLivreur +
                '}';
    }
}
