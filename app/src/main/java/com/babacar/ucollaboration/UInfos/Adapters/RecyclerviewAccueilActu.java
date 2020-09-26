package com.babacar.ucollaboration.UInfos.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UInfos.Models.Article;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerviewAccueilActu extends RecyclerView.Adapter<ViewHolder_AccueilActu> {

    private Context mContext;
    private List<Article> mArticleList;

    public RecyclerviewAccueilActu(Context context, List<Article> articles) {

        this.mContext = context;
        this.mArticleList = articles;
    }

    @NonNull
    @Override
    public ViewHolder_AccueilActu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uinfo_adapter_actu, null);
        ViewHolder_AccueilActu viewHolder = new ViewHolder_AccueilActu(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_AccueilActu holder, int position) {

        final Article article = mArticleList.get(position);

        Glide.with(mContext).load(article.getUrlImage()).into(holder.mImgActu);
        holder.mTitreActu.setText(article.getTitre());
        holder.mDesc.setText(article.getDesc());
        holder.mAuteurActu.setText(article.getAuteur());
        holder.mDateActu.setText(article.getDate_save());

        holder.mNbLike.setText(14+"");
        holder.mNbComment.setText(10+"");

        // Redirigé vers le site.
        holder.mCardActu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent versSite = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                versSite.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(versSite);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }
}
