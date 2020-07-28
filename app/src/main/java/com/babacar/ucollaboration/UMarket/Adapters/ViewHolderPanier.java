package com.babacar.ucollaboration.UMarket.Adapters;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;


public class ViewHolderPanier extends RecyclerView.ViewHolder {

    public final CardView mBien;
    public final ImageView mImageBien;
    public final TextView mLibelle;
    public final TextView mPrixU;
    public final TextView mExpiration;
    public final TextView mTotalCom;
    public final TextView mNombreAchat;
    public final ImageButton mBtnSupp;
    public final ImageButton mLikeFalse;
    public final ImageButton mLikeTrue;
    public final ImageButton mBtnPlus;
    public final ImageButton mBtnMoin;
    public final LinearLayout mBtnLike;


    public ViewHolderPanier(@NonNull View itemView) {
        super(itemView);

        this.mBien = itemView.findViewById(R.id.adapter_panier_cardView);
        this.mImageBien = itemView.findViewById(R.id.adapter_panier_photo);
        this.mLibelle = itemView.findViewById(R.id.adapter_panier_Libelle);
        this.mPrixU = itemView.findViewById(R.id.adapter_panier_prixUnitaire);
        this.mExpiration = itemView.findViewById(R.id.adapter_panier_dateExpiration);
        this.mTotalCom = itemView.findViewById(R.id.adapter_panier_souTotalBien);
        this.mBtnPlus = itemView.findViewById(R.id.adapter_panier_btnPlus);
        this.mBtnMoin = itemView.findViewById(R.id.adapter_panier_btnMoin);
        this.mNombreAchat = itemView.findViewById(R.id.adapter_panier_quantite);

        this.mBtnSupp = itemView.findViewById(R.id.adapter_panier_btnSupprimer);

        /* Like */
        this.mBtnLike = itemView.findViewById(R.id.adapter_panier_btnLike);
        this.mLikeFalse = itemView.findViewById(R.id.adapter_panier_LikeFalse);
        this.mLikeTrue = itemView.findViewById(R.id.adapter_panier_LikeTrue);
    }
}
