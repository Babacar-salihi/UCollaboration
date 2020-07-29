package com.babacar.ucollaboration.UService.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;

class ViewHolderCarte extends RecyclerView.ViewHolder {

    public ImageView mFrontPP, mBackPP;
    public TextView mFrontName, mFrontPress, mBackPress, mBackAdr, mBackTel, mBackEmail;
    public RatingBar mRatingBar;

    public ViewHolderCarte(@NonNull View itemView) {
        super(itemView);

        this.mFrontPP = itemView.findViewById(R.id.front_carte_image);
        this.mFrontName = itemView.findViewById(R.id.front_carte_name);
        this.mFrontPress = itemView.findViewById(R.id.front_carte_profess);

        this.mBackPP = itemView.findViewById(R.id.back_carte_image);
        this.mBackPress = itemView.findViewById(R.id.back_carte_profess);
        this.mBackAdr = itemView.findViewById(R.id.back_carte_adr);
        this.mBackTel = itemView.findViewById(R.id.back_carte_tel);
        this.mBackEmail = itemView.findViewById(R.id.back_carte_email);

        this.mRatingBar = itemView.findViewById(R.id.front_carte_noterBosseur);
    }
}
