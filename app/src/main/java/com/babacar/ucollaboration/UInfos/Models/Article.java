package com.babacar.ucollaboration.UInfos.Models;

public class Article {

    private String mIdArt;
    private String mAuteur;
    private String mDate_save;
    private String mDesc;
    private String mTitre;
    private String mUrl;
    private String mUrlImage;

    public Article() {
    }

    public Article(String idArt, String auteur, String date_save, String desc, String titre, String url, String urlImage) {
        mIdArt = idArt;
        mAuteur = auteur;
        mDate_save = date_save;
        mDesc = desc;
        mTitre = titre;
        mUrl = url;
        mUrlImage = urlImage;
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

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
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

    public String getUrlImage() {
        return mUrlImage;
    }

    public void setUrlImage(String urlImage) {
        mUrlImage = urlImage;
    }
}
