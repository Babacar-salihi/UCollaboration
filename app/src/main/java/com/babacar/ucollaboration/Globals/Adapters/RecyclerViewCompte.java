package com.babacar.ucollaboration.Globals.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.babacar.ucollaboration.Globals.Activitys.CreerComptePro;
import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.Globals.Models.FonctionnaliteCompte;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Activitys.ListeUser;
import com.babacar.ucollaboration.UMarket.Activitys.ListeVente;
import com.babacar.ucollaboration.UMarket.Activitys.MainActivity;

import java.util.List;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;


public class RecyclerViewCompte extends RecyclerView.Adapter<ViewHolderCompte> {

    private final Context mContext;
    private final List<FonctionnaliteCompte> mCompteList;

    public RecyclerViewCompte(Context context, List<FonctionnaliteCompte> list){

        this.mContext = context;
        this.mCompteList = list;
    }

    @NonNull
    @Override
    public ViewHolderCompte onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.global_adapter_compte_fonct, null);

        return new ViewHolderCompte(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCompte holder, final int position) {

        FonctionnaliteCompte fonct = mCompteList.get(position);

        int res = mContext.getResources().getIdentifier(fonct.getIcon(), "drawable", mContext.getPackageName());
        holder.mIcon.setImageResource(res);
        holder.mTitre.setText(fonct.getText());

        if (sCurrentUser != null) {
            holder.mCardView.setEnabled(true);
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (position){
                        case 0: // Liste d'biens en vente.
                            Intent bienVendu = new Intent(mContext, ListeVente.class);
                            bienVendu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(bienVendu);
                            Toast.makeText(mContext,"Bien en vente", Toast.LENGTH_SHORT).show(); break;
                        case 1: // Liste d'avis.
                            Intent bienFavorite = new Intent(mContext, ListeUser.class);
                            bienFavorite.putExtra("TASK", "listeEnvie");
                            bienFavorite.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(bienFavorite);
                            Toast.makeText(mContext,"Favorites", Toast.LENGTH_SHORT).show(); break;
                        case 2: // Gerer compte.
                            Toast.makeText(mContext,"Changer", Toast.LENGTH_SHORT).show(); break;
                        case 3: // Déconnexion

                            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                            alert.setTitle("Déconnexion de compte");
                            alert.setMessage("Voulez vous vraiment vous déconnectez?");
                            alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext, "déconnexion", Toast.LENGTH_SHORT).show();
                                    DataBase.deconnexionUser();
                                    sCurrentUser = null; mContext.startActivity(new Intent(mContext, MainActivity.class));
                                }
                            });
                            alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alert.show(); break;

                        case 4: // Suppression compte.
                            AlertDialog.Builder alert1 = new AlertDialog.Builder(mContext);
                            alert1.setTitle("Suppression de compte");
                            alert1.setMessage("Voulez vous vraiment supprimer votre compte?");
                            alert1.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DataBase.supprimerUser();
                                    Toast.makeText(mContext, "Supprimer", Toast.LENGTH_SHORT).show();
                                    mContext.startActivity(new Intent(mContext, MainActivity.class));
                                    Toast.makeText(mContext,"Votre compte est supprimer avec succés!", Toast.LENGTH_LONG).show();
                                }
                            });
                            alert1.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alert1.show(); break;

                        case 5:
                            Intent comptePro = new Intent(mContext, CreerComptePro.class);
                            comptePro.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            if (sCurrentUser != null)
                                mContext.startActivity(comptePro);
                            else
                                Toast.makeText(mContext, "Connectez à votre compte", Toast.LENGTH_LONG).show();
                            break;
                    }

                }
            });
        } else { // Fonctionnalités désactivés si aucun l'user n'est connecté.

            holder.mCardView.setEnabled(false);
        }

    }

    @Override
    public int getItemCount() {
        return mCompteList.size();
    }
}
