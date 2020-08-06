package com.babacar.ucollaboration.UService.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UService.Models.Bosseur;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerView_Carte_Acceuil extends RecyclerView.Adapter<ViewHolderCarte> {

    private Context mContext;
    private List<Bosseur> mBosseurList;
    private int mNb = 100;
    private final int REQUEST_CODE_CALL = 16;

    public RecyclerView_Carte_Acceuil(Context context, List<Bosseur> bosseurList) {
        mContext = context;
        mBosseurList = bosseurList;
    }

    @NonNull
    @Override
    public ViewHolderCarte onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uservice_adapter_reclyclerview_carte, null);
        ViewHolderCarte viewHolderCarte = new ViewHolderCarte(view);
        return viewHolderCarte;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCarte holder, int position) {

        final Bosseur bosseur = mBosseurList.get(position);

        /* Front */
        holder.mFrontName.setText(bosseur.getPrenomEtu()+" "+bosseur.getNomEtu());
        holder.mFrontPress.setText(bosseur.getCategorieSocioProf());
        Glide.with(mContext).load(bosseur.getPhoto()).into(holder.mFrontPP);
        like(holder, bosseur); // Permet de noter le bosseur.
        if (bosseur.isOnLine())
            holder.mOnLine.setVisibility(View.VISIBLE);

        /* Back */
        Glide.with(mContext).load(bosseur.getPhoto()).into(holder.mBackPP);
        holder.mBackPress.setText(bosseur.getCategorieSocioProf());
        holder.mBackAdr.setText(bosseur.getNewAdresse());
        holder.mBackTel.setText("+221 "+bosseur.getNumTelephoneEtu());
        holder.mBackEmail.setText(bosseur.getEmail());

        /* Appeler le bosseur */
        holder.mBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callBosseur(bosseur);
            }
        });

        /* WhatsApp */
        holder.mBtnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                whatsApp(bosseur);
            }
        });

        holder.mFlipView.setFlipOnceEnabled(true);
        holder.mFlipView.setAutoFlipBack(true);
        holder.mFlipView.setAutoFlipBackTime(1000);

    }

    @Override
    public int getItemCount() {
        return mBosseurList.size();
    }

    /**
     * Permet de noter les bosseurs.
     */
    private void like(final ViewHolderCarte holderCarte, final Bosseur bosseur) {

        holderCarte.mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                holderCarte.mRatingBar.setRating(rating);
            }
        });
    }

    /**
     * Permet d'appeler le bosseur pour bénéficier de ses services.
     * @param bosseur
     */
    private void callBosseur(Bosseur bosseur) {

        if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALL);
        } else {
            Intent callBosseur = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+221"+bosseur.getNumTelephoneEtu()));
            callBosseur.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(callBosseur);
        }
    }

    /**
     * Permet de joindre le bosseur sur WhatsApp
     * @param
     */
    private void whatsApp(Bosseur bosseur) {

        if (appIsInstalled()) {

            Intent whatsApp = new Intent(Intent.ACTION_VIEW);
            whatsApp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            whatsApp.setData(Uri.parse("https://api.whatsapp.com/send?phone=+221"+bosseur.getNumTelephoneEtu()+"text=Salut"));
            mContext.startActivity(whatsApp);
        } else {

            Toast.makeText(mContext, "WhatsApp n'est pas installé!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Permet d'ouvrir whatsApp pour contacter le vendeur.
     * @return
     */
    private boolean appIsInstalled() {

        PackageManager packageManager = mContext.getPackageManager();
        boolean isInstall;
        try {

            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            isInstall = true;
        } catch (PackageManager.NameNotFoundException e) {

            isInstall = false;
        }

        return isInstall;
    }
}