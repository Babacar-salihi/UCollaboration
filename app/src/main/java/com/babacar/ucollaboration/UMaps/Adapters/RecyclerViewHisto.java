package com.babacar.ucollaboration.UMaps.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMaps.Activitys.MainActivity;
import com.babacar.ucollaboration.UMaps.Models.Histo;
import com.babacar.ucollaboration.UMaps.Models.Lieu;

import java.util.List;

import static com.babacar.ucollaboration.UMaps.Utilitaires.UcadCarte.sHistoR;
import static com.babacar.ucollaboration.UMaps.Activitys.MainActivity.sSearchByHisto;

public class RecyclerViewHisto extends RecyclerView.Adapter<ViewHolderRecyclerHisto> {


    private Context mContext;
    private List<Histo> mHistoList;

    public RecyclerViewHisto(Context context, List<Histo> lieus) {

        this.mContext = context;
        this.mHistoList = lieus;
    }


    @NonNull
    @Override
    public ViewHolderRecyclerHisto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.umaps_adapter_histo, null);
        ViewHolderRecyclerHisto viewHolderRecyclerHisto = new ViewHolderRecyclerHisto(view);
        return viewHolderRecyclerHisto;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecyclerHisto holder, final int position) {

        final Histo histo = mHistoList.get(position);

        holder.mNomLieu.setText(histo.getPosition());
        holder.mHeure.setText("Date: "+histo.getDate());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sSearchByHisto = true;
                sHistoR = histo;
                Log.d("SURCLASSEMENT", sHistoR.toString());
                Intent carte = new Intent(mContext, MainActivity.class);
                carte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(carte);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHistoList.size();
    }


}
