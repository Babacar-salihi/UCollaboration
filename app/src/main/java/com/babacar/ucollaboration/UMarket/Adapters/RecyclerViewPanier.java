package com.babacar.ucollaboration.UMarket.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.Globals.Models.Etudiant;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Activitys.AcheterBien;
import com.babacar.ucollaboration.UMarket.Modeles.Bien;
import com.babacar.ucollaboration.UMarket.Modeles.Panier;
import com.squareup.picasso.Picasso;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;
import static com.babacar.ucollaboration.UMarket.Activitys.MainActivity.getBienById;
import static com.babacar.ucollaboration.UMarket.Activitys.Panier.notifyChange;


public class RecyclerViewPanier extends RecyclerView.Adapter<ViewHolderPanier> {

    private static Runnable mRunnable;
    private final Context mContext;
    private final List<Panier> mPaniers;
    //private long timer = 864001000;
    private int mNbBien;
    public static CountDownTimer sCountDownTimer;

    public RecyclerViewPanier(Context context, List<Panier> paniers){

        this.mContext = context;
        this.mPaniers = paniers;
    }

    @NonNull
    @Override
    public ViewHolderPanier onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.umarket_adapter_panier, null);
        return new ViewHolderPanier(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderPanier holder, final int position) {

        final Panier panier = mPaniers.get(position);

        //final Bien bien = gson.fromJson(panier.getBiens(), Bien.class);
        final Bien bien = getBienById(panier);

        Picasso.with(mContext).load(bien.getImages().get(0).getPhoto()).placeholder(R.drawable.progess_bar).into(holder.mImageBien);
        holder.mLibelle.setText(bien.getLibelle()); // Libelle du bien.
        holder.mNombreAchat.setText(String.valueOf(panier.getQuantiteAchat())); // Nombre d'article de ce bien ajouter.
        holder.mPrixU.setText(bien.getPrixBien()+"\nfcfa"); // Prix unitaire du bien.
        //holder.mExpiration.setText(); // Avant 24H.
        startMinuteur(holder, panier, position); // Méthode pour démarrer le compte à rebour de 24H.
        holder.mTotalCom.setText(panier.getSommeTotale()+"fcfa");

        PlusMoin(holder, bien, panier, position); // Méthode pour augmenter ou diminuer la quantité à acheter.

        holder.mBien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Acheter le bien");
                alert.setMessage("Voulez vous l\'achtez?");
                alert.setPositiveButton("Acheter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent achat = new Intent(mContext, AcheterBien.class);
                        achat.putExtra("AchatBien", panier);
                        achat.putExtra("PositionAchatBien", position);
                        achat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(achat);

                        mContext.startActivity(new Intent(mContext, AcheterBien.class).putExtra("AchatBien", panier));
                    }
                });
                alert.setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        bien.setNombreBien(bien.getNombreBien()+panier.getQuantiteAchat());

                        if (bien.getEtatBien() == -1) {
                            bien.setEtatBien(0);
                        }
                        DataBase.updateBien(bien);
                        mPaniers.remove(position);

                        DataBase.upDateUserPanier(sCurrentUser);
                        //holder.mBien.setVisibility(View.GONE);
                        notifyChange(); // Dire à l'adapter que la liste est modifier.
                        Log.d("Positihon", position+"");

                        Toast.makeText(mContext, bien.getLibelle()+" supprimé", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();
                /*Intent achat = new Intent(mContext, AcheterBien.class);
                achat.putExtra("AchatBien", panier);
                achat.putExtra("PositionAchatBien", position);
                achat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(achat);*/
            }
        });

        /* Like */
        if (sCurrentUser != null) {

            holder.mBtnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int teste = 0;
                    for (String favorite : sCurrentUser.getFavorie()) {

                        if (favorite.equals(bien.getIdBien())) {

                            holder.mLikeTrue.setVisibility(View.VISIBLE); teste = 1;
                            holder.mLikeFalse.setVisibility(View.GONE);
                            break;
                        }
                    }

                    if (teste == 0) {

                        holder.mLikeTrue.setVisibility(View.GONE);
                        holder.mLikeFalse.setVisibility(View.VISIBLE);
                    }
                }
            });

        }
        // Le bouton supprimer.
        /*holder.mBtnSupp
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bien.setNombreBien(bien.getNombreBien()+panier.getQuantiteAchat());

                if (bien.getEtatBien() == -1) {
                    bien.setEtatBien(0);
                }
                DataBase.updateBien(bien);
                mPaniers.remove(position);

                DataBase.upDateUserPanier(sCurrentUser);
                //holder.mBien.setVisibility(View.GONE);
                Toast.makeText(mContext, bien.getLibelle()+" supprimé", Toast.LENGTH_SHORT).show();
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return mPaniers.size();
    }


    /**
     * Démarrer le muniteur.
     * @param holder
     */
    private void startMinuteur(final ViewHolderPanier holder, final Panier panier, final int position) {

        sCountDownTimer = new CountDownTimer(panier.getTimeToExpire(), 1000){
            @Override
            public void onTick(long millisUntilFinished) {

                panier.setTimeToExpire(millisUntilFinished);
                DataBase.upDateUserPanier(sCurrentUser);
                updateTimer(holder, panier);
                Log.d("TIMERRR", String.valueOf(panier.getTimeToExpire()));
            }

            @Override
            public void onFinish() {

                Bien bien = getBienById(sCurrentUser.getPanier().get(position)); // Je récupére le bien.
                if (bien.getEtatBien() == -1)
                    bien.setEtatBien(0);
                sCurrentUser.getPanier().remove(position); // Je supprime le bien dans le panier.
                DataBase.upDateUserPanier(sCurrentUser); // Je Le update dans la base de données.
                bien.setNombreBien(bien.getNombreBien()+panier.getQuantiteAchat()); // Je rajoute du bien la quantité qui été dans le panier.
                DataBase.updateBien(bien); // Je update le bien aussi dans la base de données.
            }
        }.start();

       // mContext.startService(new Intent(mContext, ServiceMinuteur.class));
    }

    /**
     * Pertmet de formater le temps en h:mm:ss et l'afficher
     * @param holder
     */
    private static void updateTimer(ViewHolderPanier holder, Panier panier) {

        int hour = (int) (panier.getTimeToExpire() / 1000) / 36000;
        int minute = (int) ((panier.getTimeToExpire() / 1000) % 3600) / 60;
        int seconde = (int) (panier.getTimeToExpire() / 1000 % 60);
        String timer2 = String.format(Locale.getDefault(),"%02d:%02d\n:%02d", hour, minute, seconde);
        holder.mExpiration.setText(timer2);
    }

    /**
     * Permet de gérer les boutons qui augmentent ou diminues le nombre à commander.
     */
    private void PlusMoin(final ViewHolderPanier holder, final Bien mCurrentBien, final Panier panier, final int position) {
        Log.d("TAGNBBIEN",mNbBien+"");
        //final int max = mCurrentBien.getNombreBien();
        // Augmenter le nombre à acheter.

        holder.mBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentBien.getNombreBien() > 0) {

                    if ((mCurrentBien.getNombreBien()-1) == 0) {

                        mCurrentBien.setEtatBien(-1);
                    }

                    mNbBien = Integer.parseInt(holder.mNombreAchat.getText().toString()) + 1;
                    holder.mNombreAchat.setText(mNbBien+"");
                    mCurrentBien.setNombreBien(mCurrentBien.getNombreBien()-1);
                    DataBase.updateBien(mCurrentBien);
                    sCurrentUser.getPanier().get(position).setQuantiteAchat(panier.getQuantiteAchat()+1);
                    DataBase.upDateUserPanier(sCurrentUser);
                } else {
                    holder.mBtnPlus.setEnabled(false);
                    holder.mBtnMoin.setEnabled(true);
                }
            }
        });

        // Diminuer le nombre à acheter.
        holder.mBtnMoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAGNBBIEN",mNbBien+"");
                Log.d("TAGNBBIENNMoin",mCurrentBien.getNombreBien()+"");

                if (mNbBien > 1) {
                    //holder.mBtnMoin.setEnabled(true);
                    //holder.mBtnPlus.setEnabled(true);
                    if (mCurrentBien.getEtatBien() == -1) {

                        mCurrentBien.setEtatBien(0);
                    }
                    Log.d("TAGNBBIENNMoinIF",mCurrentBien.getNombreBien()+"");
                    mNbBien = Integer.parseInt(holder.mNombreAchat.getText().toString()) - 1;
                    holder.mNombreAchat.setText(mNbBien+"");
                    mCurrentBien.setNombreBien(mCurrentBien.getNombreBien()+1);
                    DataBase.updateBien(mCurrentBien);
                    sCurrentUser.getPanier().get(position).setQuantiteAchat(panier.getQuantiteAchat()-1);
                    DataBase.upDateUserPanier(sCurrentUser);
                } else {
                    Log.d("TAGNBBIENNMoinELSE",mCurrentBien.getNombreBien()+"");

                    holder.mBtnMoin.setEnabled(false);
                    holder.mBtnPlus.setEnabled(true);
                }

            }
        });

        Log.d("NOMBREEE", mNbBien+"");
    }

}
