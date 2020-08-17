package com.babacar.ucollaboration.UMarket.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Activitys.DetailsBIen;
import com.babacar.ucollaboration.UMarket.Modeles.Bien;
import com.babacar.ucollaboration.UMarket.Modeles.Panier;
import com.babacar.ucollaboration.UMarket.Popups.AlertDialogOnClickBien;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;
import static com.babacar.ucollaboration.UMarket.Fragments.FragmentAcceuil.selectBien;

public class RecyclerviewCategorieBien extends RecyclerView.Adapter<RecyclerviewCategorieBien.ViewHolderCategBien> {

    private Context mContext;
    private List<Bien> mBienList;

    public RecyclerviewCategorieBien(Context context, List<Bien> bienList) {
        mContext = context;
        mBienList = bienList;
    }

    @NonNull
    @Override
    public ViewHolderCategBien onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.umarket_adapter_categ_bien, null);
        ViewHolderCategBien viewHolderCategBien = new ViewHolderCategBien(view);
        return viewHolderCategBien;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderCategBien holder, final int position) {

        final Bien bien = mBienList.get(position);
        Log.d("BJGHJKN", bien.toString());

        if (bien.getTypeVente().equals("Flash")) {

            holder.mVenteType.setVisibility(View.VISIBLE);
        }
        Picasso.with(mContext)
                .load(bien.getImages().get(0).getPhoto())
                .skipMemoryCache()
                .placeholder(R.drawable.progess_bar)
                .into(holder.mImageBien);
        /*Glide.with(mContext)
                .load(bien.getImages().get(0).getPhoto())
                .placeholder(R.drawable.progess_bar)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.mImageBien);*/
        holder.mLibelle.setText(bien.getLibelle());
        holder.mPrixBien.setText(bien.getPrixBien()+"fcfa");
        holder.mAdrVendeur.setText(bien.getVendeur().getNewAdresse());
        holder.mQuantite.setText(bien.getNombreBien()+"");
        holder.mNbLike.setText(bien.getLike()+"");

        /* Aimer le bien */
        if (sCurrentUser != null) {

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
        }  //holder.mLikeFalse.setVisibility(View.VISIBLE);



        /* Aimmer un bien */
        holder.mLayoutLike
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (sCurrentUser != null) {

                            int teste = 0;
                            for (String favorite : sCurrentUser.getFavorie()) {

                                if (favorite.equals(bien.getIdBien())) {

                                    sCurrentUser.getFavorie().remove(favorite); teste = 1;
                                    bien.setLike(bien.getLike()-1);
                                    holder.mLikeFalse.setVisibility(View.VISIBLE);
                                    holder.mLikeTrue.setVisibility(View.GONE);
                                    break;
                                }
                            }

                            Log.d("Favorieee", String.valueOf(teste));
                            if (teste == 0) {

                                sCurrentUser.getFavorie().add(bien.getIdBien());
                                bien.setLike(bien.getLike()+1);
                                holder.mLikeFalse.setVisibility(View.GONE);
                                holder.mLikeTrue.setVisibility(View.VISIBLE);
                            }
                            holder.mNbLike.setText(bien.getLike()+"");
                            DataBase.updateBien(bien);
                            DataBase.upDateUserFavorite(sCurrentUser);
                        } else {

                            Toast.makeText(mContext, "Connecté vous d'abord à votre compte!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        /* Ouvrir un popup */
        holder.mBienObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBien = bien;
                final AlertDialogOnClickBien dialog = new AlertDialogOnClickBien(mContext);
                dialog.setTitre("Choisissez une options");
                dialog.setMessage("Que voulez vous faire?");

                if (sCurrentUser != null) {

                    // Ajouter au panier.
                    dialog.getBtnAjouter()
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Panier panier = new Panier();

                        /*Bien bien2 = selectBien;
                        bien2.setNombreBien(selectBien.getNombreBien()+1);*/

                                    panier.setBiens(selectBien.getIdBien()); // Bien en String.

                                    //Log.d("teste", "Contient = "+sCurrentUser.getPanier().contains(panier));

                                    int teste = 0;
                                    int indexBien = 0;
                                    Log.d("teste", "Av testeVar ="+teste);
                                    Log.d("teste", "Av indexVar ="+indexBien);

                                    // Teste si le panier contient déjà le bien qu'on veut ajouter
                                    for (Panier panier1 : sCurrentUser.getPanier()) {

                                        if (selectBien.getIdBien().equals(panier1.getBiens())) {
                                            Log.d("BIENEGAL", "OKOKOKOKOKOK");
                                            teste = 1; break;
                                        } else {
                                            Log.d("BIENEGAL", "KOKOKOKOKOKOK");
                                        }
                                        indexBien++;
                                    }

                                    if (teste == 0) { // Si le bien n'est pas encore dans le panier.

                                        panier.setQuantiteAchat(1);
                                        // 864001000
                                        panier.setTimeToExpire(864001000); // Le bien reste dans le panier que pour 24H.
                                        //selectBien.setNombreBien(selectBien.getNombreBien() - 1);
                                        //if (selectBien.)
                                        sCurrentUser.getPanier().add(panier); // Bien définitivement ajouter au panier.
                                        sCurrentUser.getPanier().get(indexBien).setSommeTotale(bien.getPrixBien()); // Soux total.
                                    } else { // Si le bien est déjà dans le panier.

                                        panier.setQuantiteAchat(sCurrentUser.getPanier().get(indexBien).getQuantiteAchat()+1);
                                        //selectBien.setNombreBien(selectBien.getNombreBien() - 1);
                                        sCurrentUser.getPanier().get(indexBien).setQuantiteAchat(panier.getQuantiteAchat());
                                        sCurrentUser.getPanier().get(indexBien).setSommeTotale(sCurrentUser.getPanier().get(indexBien).getQuantiteAchat()*bien.getPrixBien()); // Soux total.
                                    }

                                    Log.d("BIENLOG", panier.getQuantiteAchat()+"");
                                    //DataBase.updateBien(selectBien);
                                    DataBase.upDateUserPanier(sCurrentUser);
                                    selectBien.setNombreBien(selectBien.getNombreBien() - 1);

                                    if (selectBien.getNombreBien() == 0) { // Si le nombre de bien est supperieur à 0.

                                        selectBien.setEtatBien(-1); // Mettre le bien en indisponible.
                                    }
                                    DataBase.updateBien(selectBien);


                                    Toast.makeText(mContext, "C'est fait", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                } else {

                    Toast.makeText(mContext, "Vous n'êtes pas connecté!", Toast.LENGTH_SHORT).show();
                }


                // Voir les détails.
                dialog.getBtnDetails()
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                selectBien = bien;
                                mContext.startActivity(new Intent(mContext, DetailsBIen.class));
                                Toast.makeText(mContext, "Details", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                dialog.buil();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBienList.size();
    }


    public class ViewHolderCategBien extends RecyclerView.ViewHolder {

        public final CardView mBienObject;
        public final ImageView mImageBien;
        public final ImageView mLikeFalse;
        public final ImageView mLikeTrue;
        public final TextView mLibelle;
        public final TextView mPrixBien;
        public final TextView mAdrVendeur;
        public final TextView mVenteType;
        public final TextView mQuantite;
        public final TextView mNbLike;
        public final LinearLayout mLayoutLike;


        public ViewHolderCategBien(@NonNull View itemView) {
            super(itemView);

            /* Like */
            this.mLayoutLike = itemView.findViewById(R.id.adapter_catalogue_layoutLike);
            this.mLikeFalse = itemView.findViewById(R.id.adapter_catalogue_LikeFalse);
            this.mNbLike = itemView.findViewById(R.id.adapter_catalogue_nbLike);
            this.mLikeTrue = itemView.findViewById(R.id.adapter_catalogue_likeTrue);

            mVenteType = itemView.findViewById(R.id.adapter_catalogue_typeVente);
            mImageBien = itemView.findViewById(R.id.adapter_catalogue_produit_imgProd);
            mLibelle = itemView.findViewById(R.id.adapter_catalogue_produit_libelleProd);
            mPrixBien = itemView.findViewById(R.id.adapter_catalogue_produit_prixProd);
            mAdrVendeur = itemView.findViewById(R.id.adapter_catalogue_produit_adressVendeur);
            mBienObject = itemView.findViewById(R.id.adapter_catalogue_cardview);
            this.mQuantite = itemView.findViewById(R.id.adapter_catalogue_quantite);
        }
    }

}
