package com.babacar.ucollaboration.UMarket.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Modeles.ImageBien;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolderImage> {

    private final Context mContext;
    private final List<ImageBien> mImgList;

    public ViewPagerAdapter(Context context, List<ImageBien> imgList) {

        this.mContext = context;
        this.mImgList = imgList;
    }

    @NonNull
    @Override
    public ViewHolderImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderImage(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.umarket_adapter_image, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderImage holder, int position) {

        ImageBien imageBien = mImgList.get(position);
        //Picasso.with(mContext).load(imageBien.getPhoto()).into(holder.mImageView);
        Glide.with(mContext)
                .load(imageBien.getPhoto())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mImgList.size();
    }

    class ViewHolderImage extends RecyclerView.ViewHolder {

        final ImageView mImageView;


        ViewHolderImage(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.adapter_image_image);
        }
    }

}
