package com.babacar.ucollaboration.Globals.Models;



import com.babacar.ucollaboration.UMarket.Modeles.Panier;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class Etudiant {

    protected String mIdEtu;
    protected String mNomEtu;
    protected String mPrenomEtu;
    protected int mNumTelephoneEtu;
    protected String mNumChambre;
    protected String mEmail;
    protected String mPassword;
    protected String mAdresse;
    protected String mPhoto;
    protected String mNewAdresse;
    protected List<Panier> mPanier = new ArrayList<>(); // Panier de l'utilisateur.
    protected List<String> mFavorie = new ArrayList<>(); // Les favories.
    protected float mNote; // Evaluation du vendeur.
    //protected int mNbEval;
    //protected List<Panier> mBienAcheter = new ArrayList<>(); // Les Bients acheter.
    protected List<String> mDetailsPrestations = new ArrayList<>();

    protected String mFaculte;

    public Etudiant() {
    }

    /*public Etudiant(String idEtu, String categorieSocioProf) {
        mIdEtu = idEtu;
        mCategorieSocioProf = categorieSocioProf;
    }*/

    public Etudiant(String idEtu, String prenomEtu, String nomEtu, int numTelephoneEtu, String photo, String newAdresse, List<String> detailsDespt, float note /*,int nbEval*/) {

        this.mIdEtu = idEtu;
        this.mPrenomEtu = prenomEtu;
        this.mNomEtu = nomEtu;
        this.mNumTelephoneEtu = numTelephoneEtu;
        this.mPhoto = photo;
        this.mNewAdresse = newAdresse;
        this.mDetailsPrestations = detailsDespt;
        this.mNote = note;
        //this.mNbEval = nbEval;
    }

    /*public Etudiant(String prenomEtu, String nomEtu, String categorieSocioProf, int numTelephoneEtu, String email, String adresse) {

        this.mPrenomEtu = prenomEtu;
        this.mNomEtu = nomEtu;
        this.mCategorieSocioProf = categorieSocioProf;
        this.mNumTelephoneEtu = numTelephoneEtu;
        this.mEmail = email;
        this.mAdresse = adresse;
    }*/


    public Etudiant(String mIdEtu, String mNomEtu, String mPrenomEtu, int mNumTelephoneEtu, String mNumChambre, String mEmail, String password, String mAdresse, String mPhoto, String mFaculte, String newAdresse) {
        this.mIdEtu = mIdEtu;
        this.mNomEtu = mNomEtu;
        this.mPrenomEtu = mPrenomEtu;
        this.mNumTelephoneEtu = mNumTelephoneEtu;
        this.mNumChambre = mNumChambre;
        this.mEmail = mEmail;
        this.mPassword = password;
        this.mAdresse = mAdresse;
        //this.mCategorieSocioProf = mCategorieSocioProf;
        this.mPhoto = mPhoto;
        this.mFaculte = mFaculte;
        this.mNewAdresse = newAdresse;
    }

    public String getIdEtu() {
        return mIdEtu;
    }

    public void setIdEtu(String idEtu) {
        mIdEtu = idEtu;
    }

    public String getNomEtu() {
        return mNomEtu;
    }

    public void setNomEtu(String nomEtu) {
        mNomEtu = nomEtu;
    }

    public String getPrenomEtu() {
        return mPrenomEtu;
    }

    public void setPrenomEtu(String prenomEtu) {
        mPrenomEtu = prenomEtu;
    }

    public int getNumTelephoneEtu() {
        return mNumTelephoneEtu;
    }

    public void setNumTelephoneEtu(int numTelephoneEtu) {
        mNumTelephoneEtu = numTelephoneEtu;
    }

    public String getNumChambre() {
        return mNumChambre;
    }

    public void setNumChambre(String numChambre) {
        mNumChambre = numChambre;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getAdresse() {
        return mAdresse;
    }

    public void setAdresse(String adresse) {
        mAdresse = adresse;
    }

    /*public String getCategorieSocioProf() {
        return mCategorieSocioProf;
    }

    public void setCategorieSocioProf(String categorieSocioProf) {
        mCategorieSocioProf = categorieSocioProf;
    }*/

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getFaculte() {
        return mFaculte;
    }

    public void setFaculte(String faculte) {
        mFaculte = faculte;
    }

    public String getNewAdresse() {
        return mNewAdresse;
    }

    public void setNewAdresse(String newAdresse) {
        mNewAdresse = newAdresse;
    }

    public List<Panier> getPanier() {
        return mPanier;
    }

    public void setPanier(List<Panier> panier) {
        mPanier = panier;
    }

    public List<String> getFavorie() {
        return mFavorie;
    }

    public void setFavorie(List<String> favorie) {
        mFavorie = favorie;
    }

    /*public List<Panier> getBienAcheter() {
        return mBienAcheter;
    }

    public void setBienAcheter(List<Panier> bienAcheter) {
        mBienAcheter = bienAcheter;
    }*/

    public List<String> getDetailsPrestations() {
        return mDetailsPrestations;
    }

    public void setDetailsPrestations(List<String> detailsPrestations) {
        mDetailsPrestations = detailsPrestations;
    }

    public float getNote() {
        return mNote;
    }

    public void setNote(float note) {
        mNote = note;
    }

    /*public int getNbEval() {
        return mNbEval;
    }

    public void setNbEval(int nbEval) {
        mNbEval = nbEval;
    }*/

    @Override
    public String toString() {
        return "Etudiant{" +
                "mIdEtu='" + mIdEtu + '\'' +
                ", mNomEtu='" + mNomEtu + '\'' +
                ", mPrenomEtu='" + mPrenomEtu + '\'' +
                ", mNumTelephoneEtu=" + mNumTelephoneEtu +
                ", mNumChambre='" + mNumChambre + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mAdresse='" + mAdresse + '\'' +
                ", mPhoto='" + mPhoto + '\'' +
                ", mNewAdresse='" + mNewAdresse + '\'' +
                ", mPanier=" + mPanier +
                ", mFavorie=" + mFavorie +
                ", mNote=" + mNote +
                //", mNbEval=" + mNbEval +
                ", mDetailsPrestationsEtu=" + mDetailsPrestations +
                ", mFaculte='" + mFaculte + '\'' +
                '}';
    }
}
