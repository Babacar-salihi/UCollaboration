package com.babacar.ucollaboration.Globals.Adapters;

import android.app.Dialog;
import android.app.job.JobInfo;
import android.content.Context;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.babacar.ucollaboration.R;

public class PopUpEvaluation extends Dialog {

    private String mTitre, mMsg;
    private TextView mTitreView, mMsgView;
    private Button mButtonOui, mButtonNon;
    private RatingBar mRatingBar;

    public PopUpEvaluation(@NonNull Context context) {
        super(context);
        setContentView(R.layout.adapter_popup_evaluation);

        mTitreView = findViewById(R.id.adapater_evaluation_titre);
        mMsgView = findViewById(R.id.adapater_evaluation_msg);
        mButtonOui = findViewById(R.id.adapater_evaluation_btn_oui);
        mButtonNon = findViewById(R.id.adapater_evaluation_btn_no);
        mRatingBar = findViewById(R.id.adapater_evaluation_rating);
    }

    public void setTitre(String titre) {
        mTitre = titre;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public Button getButtonOui() {

        return mButtonOui;
    }

    public Button getButtonNon() {

        return mButtonNon;
    }

    public void build() {

        mTitreView.setText(mTitre);
        mMsgView.setText(mMsg);
        show();
    }

    public RatingBar getRat() {

        return mRatingBar;
    }
}
