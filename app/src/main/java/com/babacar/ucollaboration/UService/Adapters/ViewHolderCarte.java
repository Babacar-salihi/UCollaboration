package com.babacar.ucollaboration.UService.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

class ViewHolderCarte extends RecyclerView.ViewHolder {

    public EasyFlipView mFlipView;
    public View mOnLine;
    public ImageView mFrontPP, mBackPP;
    public TextView mFrontName, mFrontPress, mBackPress, mBackAdr, mBackTel, mBackEmail;
    public RatingBar mRatingBar;
    public CardView mEmp, mBtnCall, mBtnMsg;

    public ViewHolderCarte(@NonNull View itemView) {
        super(itemView);

        this.mFlipView = itemView.findViewById(R.id.flipper);

        this.mOnLine = itemView.findViewById(R.id.uservice_onLine);
        this.mFrontPP = itemView.findViewById(R.id.front_carte_image);
        this.mFrontName = itemView.findViewById(R.id.front_carte_name);
        this.mFrontPress = itemView.findViewById(R.id.front_carte_profess);

        this.mBackPP = itemView.findViewById(R.id.back_carte_image);
        this.mBackPress = itemView.findViewById(R.id.back_carte_profess);
        this.mBackAdr = itemView.findViewById(R.id.back_carte_adr);
        this.mBackTel = itemView.findViewById(R.id.back_carte_tel);
        this.mBackEmail = itemView.findViewById(R.id.back_carte_email);

        this.mEmp = itemView.findViewById(R.id.uservice_btn_emp_bosseur);
        this.mBtnCall = itemView.findViewById(R.id.uservice_btn_call_bosseur);
        this.mBtnMsg = itemView.findViewById(R.id.uservice_btn_whats_bosseur);

        this.mRatingBar = itemView.findViewById(R.id.front_carte_noterBosseur);
    }
}
