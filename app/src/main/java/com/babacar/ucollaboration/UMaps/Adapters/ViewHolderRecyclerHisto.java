package com.babacar.ucollaboration.UMaps.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;


public class ViewHolderRecyclerHisto extends RecyclerView.ViewHolder{

    public CardView mCardView;
    public TextView mNomLieu, mHeure;

    public ViewHolderRecyclerHisto(@NonNull View itemView) {
        super(itemView);

        this.mCardView = itemView.findViewById(R.id.umaps_adapter_card);
        this.mNomLieu = itemView.findViewById(R.id.umaps_adapter_nomLieu);
        this.mHeure = itemView.findViewById(R.id.umaps_adapter_heure);
    }
}
