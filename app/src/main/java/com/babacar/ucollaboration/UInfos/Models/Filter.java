package com.babacar.ucollaboration.UInfos.Models;

public class Filter {

    private String mFilterType;

    public Filter(String filterType) {
        mFilterType = filterType;
    }

    public String getFilterType() {
        return mFilterType;
    }

    public void setFilterType(String filterType) {
        mFilterType = filterType;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "mFilterType='" + mFilterType + '\'' +
                '}';
    }
}
