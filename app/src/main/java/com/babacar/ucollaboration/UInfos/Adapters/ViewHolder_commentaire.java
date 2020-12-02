package com.babacar.ucollaboration.UInfos.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolder_commentaire extends RecyclerView.ViewHolder {

    public CircleImageView mPpUser;
    public TextView mCommentaire;
    public TextView mUserName;
    public TextView mDateIn;

    public ViewHolder_commentaire(@NonNull View itemView) {
        super(itemView);

        mPpUser = itemView.findViewById(R.id.uinfo_adapter_comment_pp_user);
        mCommentaire = itemView.findViewById(R.id.uinfo_adapter_comment_comment);
        mUserName = itemView.findViewById(R.id.uinfo_adapter_comment_nom_user);
        mDateIn = itemView.findViewById(R.id.uinfo_adapter_comment_date_in);
    }


}
