package com.babacar.ucollaboration.UMarket.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;


public class ViewHolderCategorie extends RecyclerView.ViewHolder {

    public CardView mCardView;
    public ImageView mIconCateg;
    public TextView mTitreCateg;


    public ViewHolderCategorie(@NonNull View itemView) {
        super(itemView);

        this.mCardView = itemView.findViewById(R.id.adapter_catalogue_categorie_cardView);
        this.mIconCateg = itemView.findViewById(R.id.adapter_iconCateg);
        this.mTitreCateg = itemView.findViewById(R.id.adapter_titreCateg);
    }
}
