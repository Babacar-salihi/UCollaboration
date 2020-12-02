package com.babacar.ucollaboration.UInfos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UInfos.Models.DetailsCommentaire;
import com.bumptech.glide.Glide;

import java.util.List;

public class CommentaireAdapter extends RecyclerView.Adapter<ViewHolder_commentaire> {

    private Context mContext;
    private List<DetailsCommentaire> mDetailsCommentaires;
    public CommentaireAdapter(Context context, List<DetailsCommentaire> detailsCommentaires) {

        this.mContext = context;
        this.mDetailsCommentaires = detailsCommentaires;
    }

    @NonNull
    @Override
    public ViewHolder_commentaire onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uinfo_adapter_comment, null);
        return new ViewHolder_commentaire(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_commentaire holder, int position) {

        DetailsCommentaire detailsCommentaire = mDetailsCommentaires.get(position);
        Glide.with(mContext).load(detailsCommentaire.getPpEtu()).into(holder.mPpUser);
        holder.mCommentaire.setText(detailsCommentaire.getComment());
        holder.mUserName.setText(detailsCommentaire.getNomEtu());
        holder.mDateIn.setText(detailsCommentaire.getDate_in());
    }

    @Override
    public int getItemCount() {
        return mDetailsCommentaires.size();
    }
}
