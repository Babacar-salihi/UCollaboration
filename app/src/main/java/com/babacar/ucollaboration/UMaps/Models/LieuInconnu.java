package com.babacar.ucollaboration.UMaps.Models;

public class LieuInconnu {

    private String mIdLieu;
    private String mNomLieu;
    private String mDescriptLieu;

    public LieuInconnu() {
    }

    public LieuInconnu(String nomLieu, String descriptLieu) {
        mNomLieu = nomLieu;
        mDescriptLieu = descriptLieu;
    }

    public String getIdLieu() {
        return mIdLieu;
    }

    public void setIdLieu(String idLieu) {
        mIdLieu = idLieu;
    }

    public String getNomLieu() {
        return mNomLieu;
    }

    public void setNomLieu(String nomLieu) {
        mNomLieu = nomLieu;
    }

    public String getDescriptLieu() {
        return mDescriptLieu;
    }

    public void setDescriptLieu(String descriptLieu) {
        mDescriptLieu = descriptLieu;
    }
}
