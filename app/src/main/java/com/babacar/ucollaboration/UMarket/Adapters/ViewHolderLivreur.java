package com.babacar.ucollaboration.UMarket.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.babacar.ucollaboration.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolderLivreur extends RecyclerView.ViewHolder {

    public CardView mLivreur;
    public final CircleImageView mLivProfilPic;
    public final TextView mLivName;

    public ViewHolderLivreur(@NonNull View itemView) {
        super(itemView);

        mLivreur = itemView.findViewById(R.id.adapter_livreur_Card);
        mLivProfilPic = itemView.findViewById(R.id.adapter_livreur_profil);
        mLivName = itemView.findViewById(R.id.adapter_livreur_nom);
    }
}
