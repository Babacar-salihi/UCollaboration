package com.babacar.ucollaboration.UMarket.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.babacar.ucollaboration.Globals.Models.Etudiant;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Modeles.Livreur;
import com.babacar.ucollaboration.UService.Models.Bosseur;

import java.util.List;

public class RecyclerViewLivreur extends RecyclerView.Adapter<ViewHolderLivreur> {

    private Context mContext;
    private final List<Etudiant>  mLivreurs;

    public RecyclerViewLivreur(Context context, List<Etudiant> livreurs) {

        this.mContext = context;
        this.mLivreurs = livreurs;
    }

    @NonNull
    @Override
    public ViewHolderLivreur onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.umarket_adapter_recycleview_livreur, null);
        ViewHolderLivreur viewHolderLivreur = new ViewHolderLivreur(view);
        return viewHolderLivreur;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLivreur holder, int position) {

        Etudiant livreur = mLivreurs.get(position);
        holder.mLivName.setText(livreur.getPrenomEtu());
    }

    @Override
    public int getItemCount() {
        return mLivreurs.size();
    }
}
