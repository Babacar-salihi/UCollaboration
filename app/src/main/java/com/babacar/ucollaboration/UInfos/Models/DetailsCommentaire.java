package com.babacar.ucollaboration.UInfos.Models;

public class DetailsCommentaire {

    private String mIdArticle;
    private String mIdEtu;
    private String mNomEtu;
    private String mPpEtu;
    private String mComment;
    private String date_in;

    public DetailsCommentaire() {
    }

    public DetailsCommentaire(String comment, String date_in) {
        mComment = comment;
        this.date_in = date_in;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getDate_in() {
        return date_in;
    }

    public void setDate_in(String date_in) {
        this.date_in = date_in;
    }

    public String getIdArticle() {
        return mIdArticle;
    }

    public void setIdArticle(String idArticle) {
        mIdArticle = idArticle;
    }

    public String getIdEtu() {
        return mIdEtu;
    }

    public void setIdEtu(String idEtu) {
        mIdEtu = idEtu;
    }

    public String getNomEtu() {
        return mNomEtu;
    }

    public void setNomEtu(String nomEtu) {
        mNomEtu = nomEtu;
    }

    public String getPpEtu() {
        return mPpEtu;
    }

    public void setPpEtu(String ppEtu) {
        mPpEtu = ppEtu;
    }

    @Override
    public String toString() {
        return "DetailsCommentaire{" +
                "mComment='" + mComment + '\'' +
                ", date_in='" + date_in + '\'' +
                '}';
    }
}
