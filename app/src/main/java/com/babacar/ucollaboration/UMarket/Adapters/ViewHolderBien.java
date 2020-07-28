package com.babacar.ucollaboration.UMarket.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;


public class ViewHolderBien extends RecyclerView.ViewHolder {

    public final CardView mBienObject;
    public final ImageView mImageBien;
    public final ImageView mLikeFalse;
    public final ImageView mLikeTrue;
    public final TextView mLibelle;
    public final TextView mPrixBien;
    public final TextView mAdrVendeur;
    public final TextView mVenteType;
    public final TextView mQuantite;
    public final TextView mNbLike;
    public final LinearLayout mLayoutLike;


    public ViewHolderBien(@NonNull View itemView) {
        super(itemView);

        /* Like */
        this.mLayoutLike = itemView.findViewById(R.id.adapter_catalogue_layoutLike);
        this.mLikeFalse = itemView.findViewById(R.id.adapter_catalogue_LikeFalse);
        this.mNbLike = itemView.findViewById(R.id.adapter_catalogue_nbLike);
        this.mLikeTrue = itemView.findViewById(R.id.adapter_catalogue_likeTrue);

        mVenteType = itemView.findViewById(R.id.adapter_catalogue_typeVente);
        mImageBien = itemView.findViewById(R.id.adapter_catalogue_produit_imgProd);
        mLibelle = itemView.findViewById(R.id.adapter_catalogue_produit_libelleProd);
        mPrixBien = itemView.findViewById(R.id.adapter_catalogue_produit_prixProd);
        mAdrVendeur = itemView.findViewById(R.id.adapter_catalogue_produit_adressVendeur);
        mBienObject = itemView.findViewById(R.id.adapter_catalogue_cardview);
        this.mQuantite = itemView.findViewById(R.id.adapter_catalogue_quantite);
    }
}
