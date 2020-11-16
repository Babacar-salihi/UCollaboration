package com.babacar.ucollaboration.UInfos.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;


public class ViewHoder_Filter extends RecyclerView.ViewHolder {

    public TextView mTextFilerCateg;

    public ViewHoder_Filter(@NonNull View itemView) {
        super(itemView);

        mTextFilerCateg = itemView.findViewById(R.id.uinfo_adapter_categ_text);
    }
}
