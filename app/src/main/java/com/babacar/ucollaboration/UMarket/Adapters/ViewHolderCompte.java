package com.babacar.ucollaboration.UMarket.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;


public class ViewHolderCompte extends RecyclerView.ViewHolder {

    public final CardView mCardView;
    public final ImageView mIcon;
    public final TextView mTitre;

    public ViewHolderCompte(@NonNull View itemView) {
        super(itemView);

        mCardView = itemView.findViewById(R.id.adapter_compte_cardView);
        this.mIcon = itemView.findViewById(R.id.adapter_compte_icon);
        this.mTitre = itemView.findViewById(R.id.adapter_compte_titre);
    }
}
