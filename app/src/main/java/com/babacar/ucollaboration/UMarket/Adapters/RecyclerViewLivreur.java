package com.babacar.ucollaboration.UMarket.Adapters;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.babacar.ucollaboration.Globals.Models.Etudiant;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Activitys.ProfileLivreur;
import com.babacar.ucollaboration.UMarket.Modeles.Livreur;
import com.babacar.ucollaboration.UService.Models.Bosseur;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewLivreur extends RecyclerView.Adapter<ViewHolderLivreur> {

    private Context mContext;
    private final List<Etudiant> mLivreurs;

    public RecyclerViewLivreur(Context context, List<Etudiant> livreurs) {

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

        final Etudiant livreur = mLivreurs.get(position);
        holder.mLivName.setText(livreur.getPrenomEtu());
        Glide.with(mContext).load(livreur.getPhoto()).into(holder.mLivProfilPic);

        holder.mLivreur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Votre choix sur le livreur");
                alert.setMessage("Que voulez vous faire?");
                alert.setPositiveButton("Contacter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        appeler(livreur.getNumTelephoneEtu()); //Méthode permettant de contacter le livreur.
                    }
                });
                alert.setNegativeButton("Voir profil", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent profile = new Intent(mContext, ProfileLivreur.class);
                        profile.putExtra("idLivreur", livreur.getIdEtu());
                        profile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(profile);
                    }
                });
                alert.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mLivreurs.size();
    }

    /**
     * Permet de contacter le livreur.
     * @param num
     */
    private void appeler(int num) {

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) mContext, new String[] {Manifest.permission.CALL_PHONE}, 109);
        } else {

            Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+num));
            call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(call);
        }
    }


}
