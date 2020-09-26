package com.babacar.ucollaboration.UInfos.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UInfos.Adapters.RecyclerviewAccueilActu;
import com.babacar.ucollaboration.UInfos.Models.Article;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.mRefUinfo;

public class Uinfo_Accueil extends AppCompatActivity {

    private RecyclerView mRecyclerActu;
    private List<Article> mArticleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uinfo_activity_uinfo__accueil);

        mArticleList = new ArrayList<>();
        referencerWidget(); // Méthode pour référencer les widgets.
    }

    @Override
    protected void onStart() {
        super.onStart();

        mRefUinfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mArticleList.clear();
                for (DataSnapshot articleSnapshot : dataSnapshot.getChildren()) {

                    Article article = articleSnapshot.getValue(Article.class);
                    mArticleList.add(article);
                }

                inflater();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Permet de réferencer les widget
     */
    private void referencerWidget() {

        this.mRecyclerActu = findViewById(R.id.uinfos_recycleview);
    }

    /**
     * Permet de remplir la liste des actulités.
     */
    private void inflater() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerActu.setLayoutManager(layoutManager);
        mRecyclerActu.setAdapter(new RecyclerviewAccueilActu(getApplicationContext(), mArticleList));
    }
}
