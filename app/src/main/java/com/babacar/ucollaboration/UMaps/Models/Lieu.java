package com.babacar.ucollaboration.UMaps.Models;

import java.io.Serializable;

public class Lieu implements Serializable {

    protected String mIdLieu;
    protected String mPosition;
    protected double mLat;
    protected double mLong;
//    protected String mDescription;

    public Lieu() {
    }

    public Lieu(String idLieu, String position, double lat, double aLong) {
        mIdLieu = idLieu;
        mPosition = position;
        mLat = lat;
        mLong = aLong;
    }

    public Lieu(String position, double lat, double aLong) {
        mPosition = position;
        mLat = lat;
        mLong = aLong;
    }

    public String getIdLieu() {
        return mIdLieu;
    }

    public void setIdLieu(String idLieu) {
        mIdLieu = idLieu;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        mPosition = position;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public double getLong() {
        return mLong;
    }

    public void setLong(double aLong) {
        mLong = aLong;
    }

//    public String getDescription() {
//        return mDescription;
//    }
//
//    public void setDescription(String description) {
//        mDescription = description;
//    }

    @Override
    public String toString() {
        return "Lieu{" +
                "mIdLieu='" + mIdLieu + '\'' +
                ", mPosition='" + mPosition + '\'' +
                ", mLat=" + mLat +
                ", mLong=" + mLong +
                '}';
    }
}
