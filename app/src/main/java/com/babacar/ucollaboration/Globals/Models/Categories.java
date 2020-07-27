package com.babacar.ucollaboration.Globals.Models;

public class Categories {

    private String mIconCateg;
    private String mTitreCateg;

    public Categories(String iconCateg, String titreCateg) {
        mIconCateg = iconCateg;
        mTitreCateg = titreCateg;
    }

    public String getIconCateg() {
        return mIconCateg;
    }

    public void setIconCateg(String iconCateg) {
        mIconCateg = iconCateg;
    }

    public String getTitreCateg() {
        return mTitreCateg;
    }

    public void setTitreCateg(String titreCateg) {
        mTitreCateg = titreCateg;
    }
}
