package com.babacar.ucollaboration.Globals.Models;


public class FonctionnaliteCompte {

    private String mIcon;
    private String mText;

    public FonctionnaliteCompte() {
    }

    public FonctionnaliteCompte(String icon, String text) {
        mIcon = icon;
        mText = text;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getText() {
        return mText;
    }

    public void setText(String tetx) {
        mText = tetx;
    }
}
