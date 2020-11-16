package com.babacar.ucollaboration.UInfos.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UInfos.Models.Filter;

import java.util.List;

public class FiltreAdapter extends RecyclerView.Adapter<ViewHoder_Filter> {

    private Context mContext;
    private List<Filter> mFilters;

    public FiltreAdapter(Context context, List<Filter> list){

        this.mContext = context;
        this.mFilters = list;
    }

    @NonNull
    @Override
    public ViewHoder_Filter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uinfo_adapter_categorie, null);
        ViewHoder_Filter viewHoder_filter = new ViewHoder_Filter(view);
        return viewHoder_filter;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder_Filter holder, int position) {

        Filter filter = mFilters.get(position);
        holder.mTextFilerCateg.setText(filter.getFilterType());
    }

    @Override
    public int getItemCount() {
        return mFilters.size();
    }
}
