package com.babacar.ucollaboration.UMarket.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.Globals.Models.Etudiant;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Activitys.Facture;
import com.babacar.ucollaboration.UMarket.Modeles.Bien;
import com.babacar.ucollaboration.UMarket.Modeles.DetailsPrestation;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.upDateUserDetails;
import static com.babacar.ucollaboration.UMarket.Activitys.ListeUser.getBienById;


public class RecyclerViewDetails extends RecyclerView.Adapter<ViewHolderDetails> {

    private final Context mContext;
    private final List<DetailsPrestation> mDetailsPrestations;

    public RecyclerViewDetails(Context context, List<DetailsPrestation> list) {

        this.mContext = context;
        this.mDetailsPrestations = list;
    }


    @NonNull
    @Override
    public ViewHolderDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.umarket_adapter_liste_vente, null);
        return new ViewHolderDetails(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderDetails holder, final int position) {

        final DetailsPrestation detailsPrestation = mDetailsPrestations.get(position);

        // Qui est l'utilisateur connecté.
        //if (sCurrentUser.getIdEtu().equals(new Gson().fromJson(detailsPrestation.getAcheteur(), Acheteur.class).getIdEtu()))
        //Récupérer le vendeur depuis le bien de detail.
        Bien bien = getBienById(detailsPrestation.getBiens());
        if (sCurrentUser.getIdEtu().equals(bien.getVendeur().getIdEtu()))
            holder.mBoxAchevee.setVisibility(View.VISIBLE); // Si vendeur Show CheckBox.
        else
            holder.mBoxAchevee.setVisibility(View.GONE); // Si acheteur Hide CheckBox


        if (detailsPrestation.isAchatAcheve()) {

            holder.mBoxAchevee.setChecked(true);
            holder.mBoxAchevee.setVisibility(View.VISIBLE);
            holder.mBoxAchevee.setEnabled(false);
        }

        Picasso.with(mContext).load(bien.getImages().get(0).getPhoto()).into(holder.mImageBien);
        holder.mLibelle.setText(bien.getLibelle());
        holder.mDateVente.setText(detailsPrestation.getDateVente());
        holder.mDateRV.setText(detailsPrestation.getDateRV());
        holder.mPrixUnitaire.setText(detailsPrestation.getPrixAchat()+"fcfa");
        holder.mQuantite.setText(detailsPrestation.getQuantite()+"");

        if (detailsPrestation.isEstLivre()) {

            holder.mLivraison.setText("Oui");
        } else {

            holder.mLivraison.setText("Non");
        }

        // Achever une vente.
        holder.mBoxAchevee
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                DetailsPrestation details = new Gson().fromJson(sCurrentUser.getDetailsPrestations().get(position), DetailsPrestation.class);

                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());

                if (isChecked) {

                    detailsPrestation.setAchatAcheve(true);
                    detailsPrestation.setDateRV(currentDate);
                    /*Acheteur*/

                } else {

                    detailsPrestation.setAchatAcheve(false);
                }


                DataBase.upDateDetailsPrestation(detailsPrestation);
                ///*Vendeur*/sCurrentUser.getDetailsPrestations().set(position, new Gson().toJson(detailsPrestation));
                /*Vendeur*/
                //upDateUserDetails(sCurrentUser);

                /*Acheteur*/
                //Etudiant acheteur = new Gson().fromJson(detailsPrestation.getAcheteur(), Etudiant.class);
                //acheteur.getDetailsPrestations().clear();
                //Log.d("ACHETRERRE_AV", acheteur.toString());
                //acheteur.getDetailsPrestations().add(new Gson().toJson(detailsPrestation));
                //Log.d("ACHETRERRE_AP", acheteur.toString());

                //upDateUserDetails(acheteur);

            }
        });

        /* Au clique sur  un élément de la liste */
        holder.mCardViewListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (detailsPrestation.isAchatAcheve()) {

                    Intent facture = new Intent(mContext, Facture.class);
                    facture.putExtra("Facture", detailsPrestation.getIdDetail());
                    facture.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(facture);
                } else {

                    Toast.makeText(mContext, "Achat pas encore réglé", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDetailsPrestations.size();
    }
}
