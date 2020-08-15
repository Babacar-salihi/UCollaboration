package com.babacar.ucollaboration.UMarket.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Adapters.RecyclerViewBien;
import com.babacar.ucollaboration.UMarket.Adapters.ViewPagerAdapter;
import com.babacar.ucollaboration.UMarket.Fragments.FragmentAcceuil;
import com.babacar.ucollaboration.UMarket.Modeles.Bien;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.getUserById;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.mBiensNvendu;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.vendeur1;

public class DetailsBIen extends AppCompatActivity {

    private List<Bien> mBiensList;
    private Bien mCurrentBien;
    //private ImageView mPhotos;
    private RecyclerView mPhotoList;
    private TextView mCateg, mLibelle, mPrixUnit, mGarantie, mNombre, mDescription; // Informations sur le produit.
    private LinearLayout mCall, mWhatsApp;


    private TextView mNomVendeur, mAdresseVendeur, mLivraison;  //Informations sur le vendeur.
    private CircleImageView mPpVendeur;
    private RatingBar mNoteBAr;

    private Button mBtnAjoutPanier, mBtnAcheter;
    private ImageButton mBtnPlus, mBtnMoins;
    private TextView mNombreAchat, mSomme;

    private RecyclerView mRecyclerView;
    private static int sNb; // Nombre d'article à acheter.
    private final int REQUEST_CALL = 23;

    private LinearLayout mFieldSetBien, mFieldSetVendeur, mFieldSetImage;
    private RelativeLayout mFieldSetAjouter;
    private Animation bottom;
    private Animation top;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umarket_activity_details_bien);


        mCurrentBien = RecyclerViewBien.selectBien; // Le bien sur le quel l'utilisateur à cliquer.
        sNb = 0;
        referenceWidgets(); // Méthode pour référencer les widgets.

    }

    /**
     * C'est là ou se font les traitements.
     */
    @Override
    protected void onStart() {
        super.onStart();

        getUserById(mCurrentBien.getVendeur().getIdEtu());
        remplirTextViews(); // Méthode pour référencer les widgets.
        recycler(); // Méthode pour afficher les biens de la même catégorie que le bien courant.
        PlusMoin(); // Méthode permettant de incrémenter ou décrémenter le nombre de bien.
        appelerVendeur(); // Méthode permettant à l'acheteur d'appeler directement le vendeur.
        //ajouterPanier(); // Méthode pour ajouter le produit sélectionné au panier.
        inflateViewPager(); // Méthode pour afficher les images dans le ViewPager2.
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        // Bien courant.
        this.mBiensList = mBiensNvendu;

        // Informations sur le produit.
        //this.mPhotos = findViewById(R.id.detailsImages);
        this.mPhotoList = findViewById(R.id.Details_viewPagerImages);
        this.mCateg = findViewById(R.id.detailsCategorie);
        this.mLibelle = findViewById(R.id.detailsLibelle);
        this.mPrixUnit = findViewById(R.id.detailsPrix);
        this.mGarantie = findViewById(R.id.detailsGarantie);
        this.mNombre = findViewById(R.id.detailsNombre);
        this.mDescription = findViewById(R.id.detailsDescript);
        this.mNombreAchat = findViewById(R.id.detailsNombreAchat);

        // Information sur le vendeur.
        this.mAdresseVendeur = findViewById(R.id.details_Vendeur_Adresse);
        this.mLivraison = findViewById(R.id.details_Vendeur_livDispo);
        this.mCall = findViewById(R.id.details_BtnAppeler);
        this.mWhatsApp = findViewById(R.id.details_BtnWhatsApp);
        this.mPpVendeur = findViewById(R.id.details_Vendeur_PpVendeur);
        this.mNomVendeur = findViewById(R.id.details_Vendeur_NomVendeur);
        this.mNoteBAr = findViewById(R.id.details_Vendeur_note);


        //RecyclerView: suggestion de bien de même catégorie.
        this.mRecyclerView = findViewById(R.id.recyclerMemeCateg);

        // Autres fonctionnalités pour acheter.
        this.mBtnPlus = findViewById(R.id.details_BtnPlus);
        this.mBtnMoins = findViewById(R.id.details_BtnMoins);
        this.mBtnAjoutPanier = findViewById(R.id.details_BtnAjout);
        this.mBtnAcheter = findViewById(R.id.details_BtnContinuer);
        this.mNombreAchat = findViewById(R.id.detailsNombreAchat);
        this.mSomme = findViewById(R.id.details_somme);

        //Pour les animations.
        this.mFieldSetBien = findViewById(R.id.Details_LayoutProduit);
        this.mFieldSetVendeur = findViewById(R.id.Details_LayoutVendeur);
        this.mFieldSetImage = findViewById(R.id.Details_LayoutImage);
        this.mFieldSetAjouter = findViewById(R.id.Details_LayoutAjouter);
        animations();
    }
    /**
     * Animations
     */
    private void animations(){

        Animation left_initPosition, right_initPosition, top_initPosition;
        left_initPosition = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_left_position);
        right_initPosition = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_right_init_position);
        top_initPosition = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_top_init_position);
        mFieldSetBien.setAnimation(left_initPosition);
        mFieldSetVendeur.setAnimation(right_initPosition);
        mFieldSetImage.setAnimation(top_initPosition);
    }

    /**
     * Permet d'afficher les images.
     */
    private void inflateViewPager() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getApplicationContext(), mCurrentBien.getImages());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        this.mPhotoList.setLayoutManager(layoutManager);
        this.mPhotoList.setAdapter(adapter);
    }


    /**
     * Gére les propositions de même catégorie que le bien séléctionné.
     */
    private void recycler() {
        List<Bien> list = memeCateg(mCurrentBien, mBiensList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecyclerViewBien(getApplicationContext(), list));
    }

    /**
     * Permet de remplir les textViews de l'Activité "DétailsBien".
     */
    private void remplirTextViews() {

        // Informations sur le produit.
        //Picasso.with(getApplicationContext()).load(mCurrentBien.getImages().get(0).getPhoto()).into(mPhotos);
        mCateg.setText("Catégorie: " + mCurrentBien.getCategorie());
        mLibelle.setText("Libellé: " + mCurrentBien.getLibelle());
        mPrixUnit.setText("Prix unitaire: " + mCurrentBien.getPrixBien()+"fcfa");
        mGarantie.setText("Garantie: " + mCurrentBien.getGarantie());
        mNombre.setText("Nombre: " + mCurrentBien.getNombreBien());
        mDescription.setText("Déscription: " + mCurrentBien.getDescription());

        // Informations sur le vendeur.
        if (mCurrentBien.getVendeur().getPhoto() != null)
           // Picasso.with(getApplicationContext()).load(mCurrentBien.getVendeur().getPhoto()).into(mPpVendeur);
            Glide.with(getApplicationContext())
                    .load(mCurrentBien.getVendeur().getPhoto())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(mPpVendeur);
        mNomVendeur.setText(mCurrentBien.getVendeur().getPrenomEtu());
        mAdresseVendeur.setText("Adresse: " + mCurrentBien.getVendeur().getNewAdresse());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (vendeur1 != null)
                    mNoteBAr.setRating(vendeur1.getNote()/vendeur1.getNbEval());
            }
        }, 3000); // Wait two seconds.
        if (mCurrentBien.getLivraison())
            mLivraison.setText("Livraison: Disponible");
        else
            mLivraison.setText("Livraison: Insdisponible");

    }

    /**
     * Permet de séléctionner les biens de même catégorie que
     * le bien courramment séléctionner pour le proposer au visiteur.
     */
    private List<Bien> memeCateg(Bien bien, final List<Bien> bienList) {

        List<Bien> list = new ArrayList<>();
        list.clear();
        for (Bien bien1 : bienList) {
            if (bien1.getCategorie().equalsIgnoreCase(bien.getCategorie()) && bien1 != bien) {
                list.add(bien1);
            }
        }

        return list;
    }

    /**
     * Permet de gérer les boutons qui augmentent ou diminues le nombre à commander.
     */
    private void PlusMoin() {

        // Augmenter le nombre à acheter.
        mBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sNb < mCurrentBien.getNombreBien()) {

                    sNb = Integer.parseInt(mNombreAchat.getText().toString()) + 1;
                    mNombreAchat.setText(sNb+"");
                    mSomme.setText("Sous total: "+sNb*mCurrentBien.getPrixBien()+"fcfa");
                    mBtnPlus.setEnabled(true);
                } else {
                    mBtnPlus.setEnabled(false);
                    mBtnMoins.setEnabled(true);
                    Toast.makeText(DetailsBIen.this, "le Max est atteint", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Diminuer le nombre à acheter.
        mBtnMoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sNb > 0) {

                    sNb = Integer.parseInt(mNombreAchat.getText().toString()) - 1;
                    mNombreAchat.setText(sNb+"");
                    mSomme.setText(sNb*mCurrentBien.getPrixBien()+"fcfa");
                    mBtnPlus.setEnabled(true);
                } else {

                    mBtnMoins.setEnabled(false);
                    mBtnPlus.setEnabled(true);
                    Toast.makeText(DetailsBIen.this, "le Min est atteint", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Log.d("NOMBREEE", sNb+"");

        // Click on ajouter au panier
        mBtnAjoutPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                com.babacar.ucollaboration.UMarket.Modeles.Panier panier = new com.babacar.ucollaboration.UMarket.Modeles.Panier();

                        /*Bien bien2 = selectBien;
                        bien2.setNombreBien(selectBien.getNombreBien()+1);*/

                panier.setBiens(mCurrentBien.getIdBien()); // Bien en String.

                //Log.d("teste", "Contient = "+sCurrentUser.getPanier().contains(panier));

                int teste = 0;
                int indexBien = 0;
                Log.d("teste", "Av testeVar ="+teste);
                Log.d("teste", "Av indexVar ="+indexBien);

                // Teste si le panier contient déjà le bien qu'on veut ajouter
                for (com.babacar.ucollaboration.UMarket.Modeles.Panier panier1 : sCurrentUser.getPanier()) {

                    if (mCurrentBien.getIdBien().equals(panier1.getBiens())) {
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
                    sCurrentUser.getPanier().get(indexBien).setSommeTotale(mCurrentBien.getPrixBien()); // Soux total.
                } else { // Si le bien est déjà dans le panier.

                    panier.setQuantiteAchat(sCurrentUser.getPanier().get(indexBien).getQuantiteAchat()+1);
                    //selectBien.setNombreBien(selectBien.getNombreBien() - 1);
                    sCurrentUser.getPanier().get(indexBien).setQuantiteAchat(panier.getQuantiteAchat());
                    sCurrentUser.getPanier().get(indexBien).setSommeTotale(sCurrentUser.getPanier().get(indexBien).getQuantiteAchat()*mCurrentBien.getPrixBien()); // Soux total.
                }

                //DataBase.updateBien(selectBien);
                DataBase.upDateUserPanier(sCurrentUser);
                mCurrentBien.setNombreBien(mCurrentBien.getNombreBien() - 1);

                if (mCurrentBien.getNombreBien() == 0) { // Si le nombre de bien est supperieur à 0.

                    mCurrentBien.setEtatBien(-1); // Mettre le bien en indisponible.
                }
                DataBase.updateBien(mCurrentBien);

                Toast.makeText(getApplicationContext(), "C'est fait", Toast.LENGTH_SHORT).show();
            }
        });

        // Si on click sur acheter.
        mBtnAcheter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), AcheterBien.class));
            }
        });
    }

    /**
     * Permet à l'acheteur d'appeler directement le vendeur.
     */
    private void appelerVendeur() {

        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(DetailsBIen.this, new String[]{ Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                } else {
                    Intent appeler = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+mCurrentBien.getVendeur().getNumTelephoneEtu()));
                    startActivity(appeler);
                }
            }
        });

        mWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (appIsInstalled()) {

                    Intent whatsApp = new Intent(Intent.ACTION_VIEW);
                    whatsApp.setData(Uri.parse("https://api.whatsapp.com/send?phone=+221"+mCurrentBien.getVendeur().getNumTelephoneEtu()+"text=Salut "+mCurrentBien.getVendeur().getNomEtu()+"! j\'aimerais acheter un de vos biens sur UMarcket."));
                    startActivity(whatsApp);
                } else {

                    Toast.makeText(DetailsBIen.this, "WhatsApp n'est pas installé!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * Permet d'ouvrir whatsApp pour contacter le vendeur.
     * @return
     */
    private boolean appIsInstalled() {

        PackageManager packageManager = getPackageManager();
        boolean isInstall;
        try {

            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            isInstall = true;
        } catch (PackageManager.NameNotFoundException e) {

            isInstall = false;
        }

        return isInstall;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                appelerVendeur();
        }
    }
}
