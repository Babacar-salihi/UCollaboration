package com.babacar.ucollaboration.UMarket.Adapters;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;


public class ViewHolderDetails extends RecyclerView.ViewHolder {

    public final CardView mCardViewListe;
    public final CheckBox mBoxAchevee;
    public final ImageView mImageBien;
    public final TextView mLibelle;
    public final TextView mDateVente;
    public final TextView mDateRV;
    public final TextView mPrixUnitaire;
    public final TextView mQuantite;
    public final TextView mLivraison;

    public ViewHolderDetails(@NonNull View itemView) {
        super(itemView);

        this.mCardViewListe = itemView.findViewById(R.id.listeVente_cardview);
        this.mBoxAchevee = itemView.findViewById(R.id.adapter_listeVente_checkBoxVente);
        this.mImageBien = itemView.findViewById(R.id.adapter_listeVente_image);
        this.mLibelle = itemView.findViewById(R.id.adapter_listeVente_libelle);
        this.mDateVente = itemView.findViewById(R.id.adapter_listeVente_dateVente);
        this.mDateRV = itemView.findViewById(R.id.adapter_listeVente_dateRV);
        this.mPrixUnitaire = itemView.findViewById(R.id.adapter_listeVente_Prix);
        this.mQuantite = itemView.findViewById(R.id.adapter_listeVente_Quantite);
        this.mLivraison = itemView.findViewById(R.id.adapter_listeVente_Livraison);
    }
}
