package com.babacar.ucollaboration.UMarket.Modeles;



import com.babacar.ucollaboration.Globals.Models.Etudiant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bien implements Serializable {

    // TODO: Ajouter adresse de vente ici...
    private String mIdBien;
    private String mCategorie;
    private String mLibelle;
    private int mPrixBien;
    private String mGarantie;
    private int mNombreBien;
    private String mDescription;
    private String mTypeVente;
    private String  mDatePublication;
    private int mEtatBien; // -1: Non disponible, 0: Disponible(Par defaut), 1: Vendu.
    private boolean mLivraison; // livraison disponible : true, livraison indisponible: false.

    private boolean mActiver; // Bien activer ou pas.

    private Etudiant mVendeur;
    private List<ImageBien> mImages;
    //private ImageBien mImages;
    private DetailsPrestation mDetailsPrestation;

    private int mLike = 0; // Nombre de like du produit.

    public Bien() {
        this.mImages = new ArrayList<>();
        this.mEtatBien = 0; // Etat disponible.
        this.mLike = 0;
    }

    public Bien(String libelle, int prixBien, Vendeur vendeur) {
        mLibelle = libelle;
        mPrixBien = prixBien;
        mVendeur = vendeur;
    }

    public Bien(String idBien, String categorie, String libelle, int prixBien, String garantie, int nombreBien, String description, String typeVente, String datePublication, short etatBien, Vendeur vendeur, List<ImageBien> images, DetailsPrestation detailsPrestation) {
        mIdBien = idBien;
        mCategorie = categorie;
        mLibelle = libelle;
        mPrixBien = prixBien;
        mGarantie = garantie;
        mNombreBien = nombreBien;
        mDescription = description;
        mTypeVente = typeVente;
        mDatePublication = datePublication;
        mEtatBien = etatBien;
        mVendeur = vendeur;
        mImages = images;
        mDetailsPrestation = detailsPrestation;
    }

    public String  getIdBien() {
        return mIdBien;
    }

    public void setIdBien(String  idBien) {
        mIdBien = idBien;
    }

    public String getLibelle() {
        return mLibelle;
    }

    public void setLibelle(String libelle) {
        mLibelle = libelle;
    }

    public int getPrixBien() {
        return mPrixBien;
    }

    public void setPrixBien(int prixBien) {
        mPrixBien = prixBien;
    }

    public String getGarantie() {
        return mGarantie;
    }

    public void setGarantie(String garantie) {
        mGarantie = garantie;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getCategorie() {
        return mCategorie;
    }

    public void setCategorie(String categorie) {
        mCategorie = categorie;
    }

    public String getTypeVente() {
        return mTypeVente;
    }

    public void setTypeVente(String typeVente) {
        mTypeVente = typeVente;
    }

    public int getNombreBien() {
        return mNombreBien;
    }

    public void setNombreBien(int nombreBien) {
        mNombreBien = nombreBien;
    }

    public String getDatePublication() {
        return mDatePublication;
    }

    public void setDatePublication(String datePublication) {
        mDatePublication = datePublication;
    }

    public int getEtatBien() {
        return mEtatBien;
    }

    public void setEtatBien(int etatBien) {
        mEtatBien = etatBien;
    }

    public Etudiant getVendeur() {
        return mVendeur;
    }

    public void setVendeur(Etudiant vendeur) {
        mVendeur = vendeur;
    }

    public List<ImageBien> getImages() {
        return mImages;
    }

    public void setImages(List<ImageBien> images) {
        mImages = images;
    }

    public DetailsPrestation getDetailsPrestation() {
        return mDetailsPrestation;
    }

    public void setDetailsPrestation(DetailsPrestation detailsPrestation) {
        mDetailsPrestation = detailsPrestation;
    }

    public int getLike() {
        return mLike;
    }

    public void setLike(int like) {
        mLike = like;
    }

    public boolean getLivraison() {
        return mLivraison;
    }

    public void setLivraison(boolean livraison) {
        mLivraison = livraison;
    }


    public boolean isActiver() {
        return mActiver;
    }

    public void setActiver(boolean activer) {
        this.mActiver = activer;
    }

    @Override
    public String toString() {
        return "Bien{" +
                "mIdBien='" + mIdBien + '\'' +
                ", mCategorie='" + mCategorie + '\'' +
                ", mLibelle='" + mLibelle + '\'' +
                ", mPrixBien=" + mPrixBien +
                ", mGarantie='" + mGarantie + '\'' +
                ", mNombreBien=" + mNombreBien +
                ", mDescription='" + mDescription + '\'' +
                ", mTypeVente='" + mTypeVente + '\'' +
                ", mDatePublication='" + mDatePublication + '\'' +
                ", mEtatBien=" + mEtatBien +
                ", mLivraison=" + mLivraison +
                ", activer=" + mActiver +
                ", mVendeur=" + mVendeur +
                ", mImages=" + mImages +
                ", mDetailsPrestation=" + mDetailsPrestation +
                ", mLike=" + mLike +
                '}';
    }
}
