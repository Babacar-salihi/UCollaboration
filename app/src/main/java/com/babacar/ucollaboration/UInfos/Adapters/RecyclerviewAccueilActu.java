package com.babacar.ucollaboration.UInfos.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UInfos.Activitys.DetailsArticle;
import com.babacar.ucollaboration.UInfos.Models.Article;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sConnexTest;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;

public class RecyclerviewAccueilActu extends RecyclerView.Adapter<ViewHolder_AccueilActu> {

    private Context mContext;
    private List<Article> mArticleList;
    public static Article selectedActicle;

    public RecyclerviewAccueilActu(Context context, List<Article> articles) {

        this.mContext = context;
        this.mArticleList = articles;
    }

    @NonNull
    @Override
    public ViewHolder_AccueilActu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uinfo_adapter_actu_2, null);
        ViewHolder_AccueilActu viewHolder = new ViewHolder_AccueilActu(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder_AccueilActu holder, int position) {

        final Article article = mArticleList.get(position);

        Glide.with(mContext)
                .load(article.getImage())
                .placeholder(R.drawable.progess_bar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mImgActu);
        holder.mTitreActu.setText(article.getTitre());
        //holder.mDesc.setText(article.getDescription()); // Design v_1
        holder.mAuteurActu.setText(article.getAuteur());
        holder.mDateActu.setText(article.getDate_save());

        if (article.getListIdUserLike() != null) {

            holder.mNbLike.setText(String.valueOf(article.getListIdUserLike().size()));
            if (sCurrentUser != null && article.getListIdUserLike().contains(sCurrentUser.getIdEtu())) {

                holder.mImgLike_true.setVisibility(View.VISIBLE);
                holder.mImgLike_false.setVisibility(View.INVISIBLE);
            }
        }
        if (article.getListIdUserComment() != null)
            holder.mNbComment.setText(String.valueOf(article.getListIdUserComment().size()));

        // Redirigé vers le site.
        holder.mCardActu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedActicle = article;
                Intent versDetails = new Intent(mContext, DetailsArticle.class);
                versDetails.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(versDetails);
            }
        });

        /* Aimer un acticle */
        holder.mBtnLikeGroup
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (sCurrentUser != null) {

                            if (article.getListIdUserLike() != null && article.getListIdUserLike().contains(sCurrentUser.getIdEtu())) { // ne pas imer.

                                holder.mImgLike_true.setVisibility(View.GONE);
                                holder.mImgLike_false.setVisibility(View.VISIBLE);
                                holder.mNbLike.setText(String.valueOf(article.getListIdUserLike().size()-1));

                                article.getListIdUserLike().remove(sCurrentUser.getIdEtu());
                                DataBase.upDateArticle(article);

                            } else { // Aimer.
                                holder.mImgLike_true.setVisibility(View.VISIBLE);
                                holder.mImgLike_false.setVisibility(View.INVISIBLE);
                                holder.mNbLike.setText(String.valueOf(article.getListIdUserLike().size()+1));

                                article.getListIdUserLike().add(sCurrentUser.getIdEtu());
                                DataBase.upDateArticle(article);
                            }
                        } else {

                            Toast.makeText(mContext, "Connecté vous d'abord à votre compte!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }
}
