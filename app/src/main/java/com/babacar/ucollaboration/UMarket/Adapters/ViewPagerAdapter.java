package com.babacar.ucollaboration.UMarket.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Modeles.ImageBien;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private final Context mContext;
    private final List<ImageBien> mImgList;
    private LayoutInflater mLayoutInflater;

    public ViewPagerAdapter(Context context, List<ImageBien> imgList) {

        this.mContext = context;
        this.mImgList = imgList;
    }

    @Override
    public int getCount() {
        return mImgList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.umarket_adapter_image, null);

        ImageView imageView = view.findViewById(R.id.adapter_image_image);

        Glide.with(mContext)
                .load(mImgList.get(position).getPhoto())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
