package com.babacar.ucollaboration.UMarket.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Modeles.Livreur;

import java.util.List;

public class RecyclerViewLivreur extends RecyclerView.Adapter<ViewHolderLivreur> {

    private Context mContext;
    private final List<Livreur>  mLivreurs;

    public RecyclerViewLivreur(Context context, List<Livreur> livreurs) {

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

        Livreur livreur = mLivreurs.get(position);

        holder.mLivName.setText(livreur.getPrenomEtu());
    }

    @Override
    public int getItemCount() {
        return mLivreurs.size();
    }
}
