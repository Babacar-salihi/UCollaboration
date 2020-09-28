package com.babacar.ucollaboration.UInfos.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;

public class ViewHolder_AccueilActu extends RecyclerView.ViewHolder {

    public CardView mCardActu;
    public ImageView mImgActu;
    public TextView mTitreActu, mDesc, mAuteurActu, mDateActu, mNbLike, mNbComment;

    public RelativeLayout mBtnLikeGroup;
    public ImageView mImgLike_true, mImgLike_false;

    public ViewHolder_AccueilActu(@NonNull View itemView) {
        super(itemView);

        mCardActu = itemView.findViewById(R.id.uinfo_adapter_acceuil_card);
        mImgActu = itemView.findViewById(R.id.uinfo_adapter_accueil_img);
        mDesc = itemView.findViewById(R.id.uinfo_adapter_accueil_desc);
        mTitreActu = itemView.findViewById(R.id.uinfo_adapter_accueil_titre);
        mAuteurActu = itemView.findViewById(R.id.uinfo_adapter_accueil_auteur);
        mDateActu = itemView.findViewById(R.id.uinfo_adapter_accueil_date);

        mBtnLikeGroup = itemView.findViewById(R.id.uinfo_adapter_accueil_like);
        mImgLike_true = itemView.findViewById(R.id.uinfo_adapter_accueil_likeTrue);
        mImgLike_false = itemView.findViewById(R.id.uinfo_adapter_accueil_likeFalse);


        mNbLike = itemView.findViewById(R.id.uinfo_adapter_accueil_nbLike);
        mNbComment = itemView.findViewById(R.id.uinfo_adapter_accueil_nbComment);

    }
}
