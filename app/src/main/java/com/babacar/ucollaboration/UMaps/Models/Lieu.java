package com.babacar.ucollaboration.UMaps.Models;

public class Lieu {

    private String mIdLieu;
    private String mPosition;
    private double mLat;
    private double mLong;

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
