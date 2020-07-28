package com.babacar.ucollaboration.UMarket.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.babacar.ucollaboration.Globals.Models.Categories;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Activitys.MemeCategorie;
import com.babacar.ucollaboration.UMarket.Modeles.Bien;

import java.util.ArrayList;
import java.util.List;

import static com.babacar.ucollaboration.UMarket.Fragments.FragmentAcceuil.sBienList;

public class RecyclerViewCategorie extends RecyclerView.Adapter<ViewHolderCategorie> {

    private final Context mContext;
    private final List<Categories> mCategories;
    public static final List<Bien> mBienCateg = new ArrayList<>();
    public static String categ; // Le titre de cardView séléctionné.

    public RecyclerViewCategorie(Context context, List<Categories> list){

        this.mContext = context;
        this.mCategories = list;
    }

    @NonNull
    @Override
    public ViewHolderCategorie onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.umarket_adapter_catalogue_categorie_bien, null);

        return new ViewHolderCategorie(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategorie holder, int position) {

        final Categories categories = mCategories.get(position);

        int res = mContext.getResources().getIdentifier(categories.getIconCateg(), "drawable", mContext.getPackageName());
        holder.mIconCateg.setImageResource(res);
        holder.mTitreCateg.setText(categories.getTitreCateg());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               categ = categories.getTitreCateg();
                Log.d("Categggggggg", categ);
                mBienCateg.clear();
                for (Bien bien : sBienList) {
                    if (bien.getCategorie().equalsIgnoreCase(categ)) {

                        mBienCateg.add(bien); // Liste de categorie "categ".
                    }
                }

                Intent memeCateg = new Intent(mContext, MemeCategorie.class);
                memeCateg.putExtra("TitreActivity", categ);
                memeCateg.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(memeCateg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }
}
