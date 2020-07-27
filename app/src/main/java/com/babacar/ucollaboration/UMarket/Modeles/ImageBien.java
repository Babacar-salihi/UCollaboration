package com.babacar.ucollaboration.UMarket.Modeles;

public class ImageBien {

    private String  mIdPhoto;
    private String mPhotoUrl;
    private int mLargeur;
    private int mHauteur;

    private Bien mBien;

    public ImageBien() {
    }

    public ImageBien(String  idPhoto, String photoUrl, int largeur, int hauteur) {
        mIdPhoto = idPhoto;
        mPhotoUrl = photoUrl;
        mLargeur = largeur;
        mHauteur = hauteur;
    }

    public ImageBien(String idPhoto, String  photo, int largeur, int hauteur, Bien bien) {
        mIdPhoto = idPhoto;
        mPhotoUrl = photo;
        mLargeur = largeur;
        mHauteur = hauteur;
        mBien = bien;
    }

    public String getIdPhoto() {
        return mIdPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        mIdPhoto = idPhoto;
    }

    public int getLargeur() {
        return mLargeur;
    }

    public void setLargeur(int largeur) {
        mLargeur = largeur;
    }

    public int getHauteur() {
        return mHauteur;
    }

    public void setHauteur(int hauteur) {
        mHauteur = hauteur;
    }

    public Bien getBien() {
        return mBien;
    }

    public void setBien(Bien bien) {
        mBien = bien;
    }

    public String getPhoto() {
        return mPhotoUrl;
    }

    public void setPhoto(String photo) {
        mPhotoUrl = photo;
    }

    @Override
    public String toString() {
        return "ImageBien{" +
                "mIdPhoto=" + mIdPhoto +
                ", mLargeur=" + mLargeur +
                ", mHauteur=" + mHauteur +
                ", mBien=" + mBien +
                '}';
    }
}
