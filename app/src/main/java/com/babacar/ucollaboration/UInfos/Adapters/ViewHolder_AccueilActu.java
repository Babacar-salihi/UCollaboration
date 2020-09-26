package com.babacar.ucollaboration.UInfos.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;

public class ViewHolder_AccueilActu extends RecyclerView.ViewHolder {

    public CardView mCardActu;
    public ImageView mImgActu;
    public TextView mTitreActu, mDesc, mAuteurActu, mDateActu, mNbLike, mNbComment;

    public ViewHolder_AccueilActu(@NonNull View itemView) {
        super(itemView);

        mCardActu = itemView.findViewById(R.id.uinfo_adapter_acceuil_card);
        mImgActu = itemView.findViewById(R.id.uinfo_adapter_acceuil_img);
        mDesc = itemView.findViewById(R.id.uinfo_adapter_acceuil_desc);
        mTitreActu = itemView.findViewById(R.id.uinfo_adapter_acceuil_titre);
        mAuteurActu = itemView.findViewById(R.id.uinfo_adapter_acceuil_auteur);
        mDateActu = itemView.findViewById(R.id.uinfo_adapter_acceuil_date);

        mNbLike = itemView.findViewById(R.id.uinfo_adapter_acceuil_nbLike);
        mNbComment = itemView.findViewById(R.id.uinfo_adapter_acceuil_nbComment);

    }
}
