package com.babacar.ucollaboration.Globals.Models;

import java.util.List;

public class Departement {

    private int mIdDepartement;
    private String icon;
    private String mNomDepart;
    private String mFaculte;
    private List<Etudiant> mEtudiants;

    public Departement() {
    }

    public Departement(String icon, String nomDepart) {

        this.icon = icon;
        this.mNomDepart = nomDepart;
    }

    public Departement(int idDepartement, String nomDepart, String faculte, List<Etudiant> etudiants) {
        mIdDepartement = idDepartement;
        mNomDepart = nomDepart;
        mFaculte = faculte;
        mEtudiants = etudiants;
    }

    public int getIdDepartement() {
        return mIdDepartement;
    }

    public void setIdDepartement(int idDepartement) {
        mIdDepartement = idDepartement;
    }

    public String getNomDepart() {
        return mNomDepart;
    }

    public void setNomDepart(String nomDepart) {
        mNomDepart = nomDepart;
    }

    public String getFaculte() {
        return mFaculte;
    }

    public void setFaculte(String faculte) {
        mFaculte = faculte;
    }

    public List<Etudiant> getEtudiants() {
        return mEtudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        mEtudiants = etudiants;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Departement{" +
                "mIdDepartement=" + mIdDepartement +
                ", mNomDepart='" + mNomDepart + '\'' +
                ", mFaculte='" + mFaculte + '\'' +
                ", mEtudiants=" + mEtudiants +
                '}';
    }
}
