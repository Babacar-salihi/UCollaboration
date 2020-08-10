package com.babacar.ucollaboration.UMaps.Models;

public class Histo extends Lieu {

    private String mDate;

    public Histo() {

    }

    public Histo(String nomLieu, double lat, double lng, String date) {

        super.mPosition = nomLieu;
        super.mLat = lat;
        super.mLong = lng;
        this.mDate = date;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }
}
