package com.babacar.ucollaboration.UInfos.Models;

import java.util.ArrayList;
import java.util.List;

public class Article {

    private String mIdArt;
    private String mAuteur;
    private String mDate_save;
    private String mDescription;
    private String mTitre;
    private String mUrl;
    private String mImage;
    private List<String> mListIdUserLike = new ArrayList<>(); // Liste des utiisateur qui on aimé l'article.
    private List<String> mListIdUserComment = new ArrayList<>(); // Liste des utilisateur qui on commenté k'article.

    public Article() {}

    public Article(String idArt, String auteur, String date_save, String desc, String titre, String url, String urlImage) {

        mIdArt = idArt;
        mAuteur = auteur;
        mDate_save = date_save;
        mDescription = desc;
        mTitre = titre;
        mUrl = url;
        mImage = urlImage;
    }

    public String getIdArt() {
        return mIdArt;
    }

    public void setIdArt(String idArt) {
        mIdArt = idArt;
    }

    public String getAuteur() {
        return mAuteur;
    }

    public void setAuteur(String auteur) {
        mAuteur = auteur;
    }

    public String getDate_save() {
        return mDate_save;
    }

    public void setDate_save(String date_save) {
        mDate_save = date_save;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getTitre() {
        return mTitre;
    }

    public void setTitre(String titre) {
        mTitre = titre;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public List<String> getListIdUserLike() {
        return mListIdUserLike;
    }

    public void setListIdUserLike(List<String> listIdUserLike) {
        mListIdUserLike = listIdUserLike;
    }

    public List<String> getListIdUserComment() {
        return mListIdUserComment;
    }

    public void setListIdUserComment(List<String> listIdUserComment) {
        mListIdUserComment = listIdUserComment;
    }

    @Override
    public String toString() {
        return "Article{" +
                "mIdArt='" + mIdArt + '\'' +
                ", mAuteur='" + mAuteur + '\'' +
                ", mDate_save='" + mDate_save + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mTitre='" + mTitre + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mListIdUserLike=" + mListIdUserLike +
                ", mListIdUserComment=" + mListIdUserComment +
                '}';
    }
}