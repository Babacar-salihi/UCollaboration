package com.babacar.ucollaboration.UInfos.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.babacar.ucollaboration.Globals.Activitys.Acceuil;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UInfos.Adapters.FiltreAdapter;
import com.babacar.ucollaboration.UInfos.Adapters.RecyclerviewAccueilActu;
import com.babacar.ucollaboration.UInfos.Models.Article;
import com.babacar.ucollaboration.UInfos.Models.Filter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.mRefUinfo;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.publierBien;

public class Uinfo_Accueil extends AppCompatActivity {

    private RecyclerView mRecyclerActu;
    private List<Article> mArticleList;

    private RecyclerView mRecyclerViewFilter;

    // A la une.
    private ImageView mALaUne_img;
    public TextView mALaUne_titre, mALaUne_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uinfo_activity_uinfo__accueil);

        mArticleList = new ArrayList<>();
        referencerWidget(); // Méthode pour référencer les widgets.
        inflaterFilter();// Filter.
    }

    /**
     * Permet de réferencer les widget
     */
    private void referencerWidget() {

        this.mRecyclerActu = findViewById(R.id.uinfos_recycleview);
        this.mRecyclerViewFilter = findViewById(R.id.uinfos_recycleview_filter);

        this.mALaUne_img = findViewById(R.id.uinfo_alaUne_Img);
        this.mALaUne_titre = findViewById(R.id.uinfo_alaUne_titre);
        this.mALaUne_desc = findViewById(R.id.uinfo_alaUne_descript);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mRefUinfo.child("Articles_COUD").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mArticleList.clear();
                for (DataSnapshot articleSnapshot : dataSnapshot.getChildren()) {

                    Article article = articleSnapshot.getValue(Article.class);
                    article.setIdArt(articleSnapshot.getKey());
                    mArticleList.add(article);
                    Log.d("Articlee", article.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRefUinfo.child("Inscription UCAD").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Article article = dataSnapshot.getValue(Article.class);
                article.setIdArt(dataSnapshot.getKey());
                mArticleList.add(article);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRefUinfo.child("YouTube_UCAD_Senegal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot articleSnapshot : dataSnapshot.getChildren()) {

                    Article article = articleSnapshot.getValue(Article.class);
                    article.setIdArt(articleSnapshot.getKey());
                    mArticleList.add(article);
                }
                inflater();
                setALaUne(); // Afficher l'article à la une.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
     * Permet de remplir la liste des actulités.
     */
    private void inflater() {

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerActu.setLayoutManager(layoutManager);
        mRecyclerActu.setAdapter(new RecyclerviewAccueilActu(getApplicationContext(), mArticleList));
    }

    /**
     * Permet de filter le contenu de l'activité.
     */
    public void inflaterFilter(){

        List<Filter> filters = new ArrayList<>(4);
        filters.add(new Filter("Accueil"));
        filters.add(new Filter("COUD"));
        filters.add(new Filter("UCAD_Sénégal"));
        filters.add(new Filter("FST"));
        filters.add(new Filter("FSJP"));
        filters.add(new Filter("FMPO"));
        filters.add(new Filter("FLSH"));
        filters.add(new Filter("FASEG"));

        FiltreAdapter adapter = new FiltreAdapter(getApplicationContext(), filters);
        mRecyclerViewFilter.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewFilter.setLayoutManager(layoutManager);
    }

    /**
     * Pour afficher l'article à la une.
     */
    public void setALaUne(){

        for (Article article : mArticleList){

            if (article.isAlaUne()){
                Glide.with(Uinfo_Accueil.this)
                        .load(article.getImage())
                        .placeholder(R.drawable.progess_bar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mALaUne_img);
                mALaUne_titre.setText(article.getTitre());
                mALaUne_desc.setText(article.getDescription());
                break;
            }
        }
    }

    /**
     * Au clique sur le bouton de retour afficher l'activité Accueil
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Acceuil.class));
        overridePendingTransition(R.anim.slider_right_init_position, R.anim.slider_out_left);
        finish();
    }


}
