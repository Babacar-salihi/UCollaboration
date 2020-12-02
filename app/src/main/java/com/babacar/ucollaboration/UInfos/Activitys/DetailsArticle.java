package com.babacar.ucollaboration.UInfos.Activitys;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.babacar.ucollaboration.Globals.Utilitaires.SplashCreenOK;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UInfos.Adapters.CommentaireAdapter;
import com.babacar.ucollaboration.UInfos.Models.DetailsCommentaire;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.mRefDetailsComment;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.mRefUinfo;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;
import static com.babacar.ucollaboration.UInfos.Adapters.RecyclerviewAccueilActu.selectedActicle;

public class DetailsArticle extends AppCompatActivity {

    private ImageView mArticleImg;
    private TextView mTitre, mDescription, mAuteur, mDatePub, mBtnPublish;
//    private Button mBtnOpenWebSite;
    private TextView mBtnOpenWebSite;
    private EditText mNewComment;

    private RecyclerView mRecyclerViewCommantaire;
    private List<DetailsCommentaire> mCommentaires;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uinfo_activity_details_article);

        referencerWidget(); // Lié les widgets.
        mCommentaires = new ArrayList<>(5);
    }

    @Override
    protected void onStart() {
        super.onStart();

        inflate(); // remplissage des informations.
        openWebSite(); // Au clique ouvrir le site web correspondant pour plus d'information.
        addNewComment(); // Au clique sur ajouter un nouveau commentaire.
    }

    /**
     * Permet de réferencer les widget
     */
    private void referencerWidget() {

        this.mArticleImg = findViewById(R.id.uinfo_details_img);
        this.mTitre = findViewById(R.id.uinfo_details_titre);
        this.mDescription = findViewById(R.id.uinfo_details_description);
        this.mAuteur = findViewById(R.id.uinfo_details_auteur);
        this.mDatePub = findViewById(R.id.uinfo_details_date);
        this.mBtnOpenWebSite = findViewById(R.id.uinfo_btn_open_website);
        this.mRecyclerViewCommantaire = findViewById(R.id.uinfo_details_commentaire);
        this.mNewComment = findViewById(R.id.uinfo_details_addComment);
        this.mBtnPublish = findViewById(R.id.uinfo_details_btn_publier);
    }

    /**
     * Permet de remplir les informations.
     */
    private void inflate(){

        Glide.with(DetailsArticle.this).load(selectedActicle.getImage()).into(mArticleImg);
        mTitre.setText(selectedActicle.getTitre());
        mDescription.setText(selectedActicle.getDescription());
        mAuteur.setText(selectedActicle.getAuteur());
        mDatePub.setText(selectedActicle.getDate_save());

        inflateComment();
    }

    /**
     * Permet d'afficher les commentaires.
     */
    public void inflateComment(){

        Log.d("Commentairess", "==="+selectedActicle.toString());
        if (selectedActicle.getIdCommentaire() != null){

            List<String> listComment = selectedActicle.getIdCommentaire();
            for (String idComment : listComment){
                mCommentaires.clear();
                mRefDetailsComment.child(idComment).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        DetailsCommentaire commentaire = dataSnapshot.getValue(DetailsCommentaire.class);
                        mCommentaires.add(commentaire);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            mRecyclerViewCommantaire.setAdapter(new CommentaireAdapter(DetailsArticle.this, mCommentaires));
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailsArticle.this, LinearLayoutManager.VERTICAL, false);
            mRecyclerViewCommantaire.setLayoutManager(layoutManager);
        }
    }

    /**
     * Permet d'ajouter un autre commengtaire
     */
    public void addNewComment(){

        mBtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sCurrentUser != null) {
                    String texte = mNewComment.getText().toString().trim();
                    if(!TextUtils.isEmpty(texte)){

                        DetailsCommentaire detailsCommentaire = new DetailsCommentaire();
                        Calendar calendar = Calendar.getInstance();
                        String currenteDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
                        String key = sCurrentUser.getIdEtu()+selectedActicle.getIdArt();

                        selectedActicle.getIdCommentaire().add(key);
                        mRefUinfo.child("Articles").child(selectedActicle.getTag()).child(selectedActicle.getIdArt()).setValue(selectedActicle);
                        detailsCommentaire.setIdArticle(selectedActicle.getIdArt());
                        detailsCommentaire.setComment(texte);
                        detailsCommentaire.setDate_in(currenteDate);
                        detailsCommentaire.setNomEtu(sCurrentUser.getPrenomEtu());
                        detailsCommentaire.setPpEtu(sCurrentUser.getPhoto());

                        mRefDetailsComment.child(key).setValue(detailsCommentaire);
                        startActivity(new Intent(DetailsArticle.this, SplashCreenOK.class));
                    } else {
                        Toast.makeText(DetailsArticle.this, "Ecrivez du texte d'abord!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(DetailsArticle.this, "Veuillez vous connectez d'abord!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    /**
     * Permet d'ouvrir le site web correspondant pour plus d'information.
     */
    private void openWebSite(){

        mBtnOpenWebSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent versSite = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedActicle.getUrl()));
                startActivity(versSite);
            }
        });
    }
}
