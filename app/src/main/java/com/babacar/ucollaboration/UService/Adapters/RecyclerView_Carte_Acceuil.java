package com.babacar.ucollaboration.UService.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UService.Models.Bosseur;

import java.util.List;

public class RecyclerView_Carte_Acceuil extends RecyclerView.Adapter<ViewHolderCarte> {

    private Context mContext;
    private List<Bosseur> mBosseurList;
    private int mNb = 100;

    public RecyclerView_Carte_Acceuil(Context context, List<Bosseur> bosseurList) {
        mContext = context;
        mBosseurList = bosseurList;
    }

    @NonNull
    @Override
    public ViewHolderCarte onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uservice_adapter_reclyclerview_carte, null);
        ViewHolderCarte viewHolderCarte = new ViewHolderCarte(view);
        return viewHolderCarte;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCarte holder, int position) {

        Bosseur bosseur = mBosseurList.get(position);

        /* Front */
        holder.mFrontName.setText(bosseur.getPrenomEtu()+" "+bosseur.getNomEtu());
        holder.mFrontPress.setText(bosseur.getCategorieSocioProf());
        int res = mContext.getResources().getIdentifier(bosseur.getPhoto(), "drawable", mContext.getPackageName());
        holder.mFrontPP.setImageResource(res);
        like(holder, bosseur); // Permet de noter le bosseur.

        /* Back */
        holder.mBackPP.setImageResource(res);
        holder.mBackPress.setText(bosseur.getCategorieSocioProf());
        holder.mBackAdr.setText(bosseur.getAdresse());
        holder.mBackTel.setText("+221 "+bosseur.getNumTelephoneEtu());
        holder.mBackEmail.setText(bosseur.getEmail());


    }

    @Override
    public int getItemCount() {
        return mBosseurList.size();
    }

    /**
     * Permet de noter les bosseurs.
     */
    private void like(final ViewHolderCarte holderCarte, final Bosseur bosseur) {

        holderCarte.mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                holderCarte.mRatingBar.setRating(rating);
            }
        });
    }
}
