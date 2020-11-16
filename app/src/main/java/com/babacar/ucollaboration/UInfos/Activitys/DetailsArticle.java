package com.babacar.ucollaboration.UInfos.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.babacar.ucollaboration.R;
import com.bumptech.glide.Glide;

import static com.babacar.ucollaboration.UInfos.Adapters.RecyclerviewAccueilActu.selectedActicle;

public class DetailsArticle extends AppCompatActivity {

    private ImageView mArticleImg;
    private TextView mTitre, mDescription, mAuteur, mDatePub;
    private Button mBtnOpenWebSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_article);

        referencerWidget(); // Lié les widgets.
    }

    @Override
    protected void onStart() {
        super.onStart();

        inflate(); // remplissage des informations.
        openWebSite(); // Au clique ouvrir le site web correspondant pour plus d'information.
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
