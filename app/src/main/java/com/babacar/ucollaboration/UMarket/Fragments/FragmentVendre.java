package com.babacar.ucollaboration.UMarket.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.babacar.ucollaboration.Globals.Activitys.Connexion;
import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.Globals.Models.Etudiant;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Activitys.MainActivity;
import com.babacar.ucollaboration.UMarket.Modeles.Bien;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVendre extends Fragment implements AdapterView.OnItemSelectedListener {

    private View mView;
    private ImageView mPhotoBienF, mPhotoBienA, mPhotoBienD, mPhotoBienG;
    private String mCategorie;
    private Spinner mSPCategorie;
    private EditText mLibelle, mPrix, mGarantie, mNombre, mDescription, mNewAdresse;
    private ImageButton mBtnAdr;
    private RadioGroup mRGTypeVente, mRGLivraison;
    private String mTypeVente = "Normale";
    private boolean mLivraison ;
    private Button mBtnContinuer;

    private static final int REQUEST_CODE_F = 200;
    private static final int REQUEST_CODE_A = 201;
    private static final int REQUEST_CODE_D = 202;
    private static final int REQUEST_CODE_G = 203;

    private List<Bitmap> mBitmapList = new ArrayList<>();

    private Bitmap mImageBitmapF;
    private Bitmap mImageBitmapA;
    private Bitmap mImageBitmapD;
    private Bitmap mImageBitmapG;

    public FragmentVendre() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.umarket_fragment_vendre, container, false);
        ReferenceWidgets(); // Permet de referencer les widgets.
        choisirCategorie(); //Gestion du spinner categorie.
        newAdresse(); // Nouvelle adresse de vente.
        typeVente(); //Type de vente: flash ou normal.
        prendrePhotos(); //Ouvrir le camera et prendre des photos.
        publier(); //Mettre le bien dans la base de données Firebase.

        return mView;
    }

    /**
     *  Référencé les widgets.
     */
    public void ReferenceWidgets(){

        // Les diférrentes positions des photos
        this.mPhotoBienF = mView.findViewById(R.id.vendre_btnPrendrePhotoF); // Avant
        this.mPhotoBienA = mView.findViewById(R.id.vendre_btnPrendrePhotoA); // Arriére
        this.mPhotoBienD = mView.findViewById(R.id.vendre_btnPrendrePhotoD); // Droit
        this.mPhotoBienG = mView.findViewById(R.id.vendre_btnPrendrePhotoG); // Gauche

        this.mSPCategorie = mView.findViewById(R.id.vendre_categorie);

        this.mRGLivraison = mView.findViewById(R.id.vendre_radioGroupLivraison);
        this.mLibelle = mView.findViewById(R.id.vendre_libelle);
        this.mPrix = mView.findViewById(R.id.vendre_prix);
        this.mGarantie = mView.findViewById(R.id.vendre_garantie);
        this.mNombre = mView.findViewById(R.id.vendre_nombre);
        this.mDescription = mView.findViewById(R.id.vendre_description);
        this.mBtnAdr = mView.findViewById(R.id.vendre_btnNewAdr);
        this.mRGTypeVente = mView.findViewById(R.id.vendre_typeVente);
        this.mNewAdresse = mView.findViewById(R.id.vendre_newAdresse);
        this.mBtnContinuer = mView.findViewById(R.id.vendre_btnContinuer);

    }

    /**
     * Géré le spinner categorie.
     */
    public void choisirCategorie(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.categorie, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSPCategorie.setAdapter(adapter);
        mSPCategorie.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mCategorie = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mCategorie = "Autres";
    }

    public void newAdresse(){

        mBtnAdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNewAdresse.getVisibility() == View.GONE){
                    Animation left = AnimationUtils.loadAnimation(getContext(), R.anim.slider_left_position);
                    mNewAdresse.setAnimation(left);
                    mNewAdresse.setVisibility(View.VISIBLE);

                } else {
                    Animation right = AnimationUtils.loadAnimation(getContext(), R.anim.slider_right_hide);
                    mNewAdresse.setAnimation(right);
                    mNewAdresse.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Permet de choisir le type de vente : Vente normale ou flash.
     * et renseigne sur la disponibilité ou pas, de livraison par le vendeur.
     */
    public void typeVente(){

        // Choisir un type de vente.
        mRGTypeVente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()){
                    case R.id.vendre_venteNormale :
                        mTypeVente = "Normale"; break;
                    case R.id.vendre_venteFlash:
                        mTypeVente = "Flash"; break;
                }
                if(TextUtils.isEmpty(mTypeVente))
                    mTypeVente = "Normale";
            }
        });

        // Livraison
        mRGLivraison.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (group.getCheckedRadioButtonId()) {

                    case R.id.vendre_livDispo :
                        mLivraison = true; break;
                    case R.id.vendre_livInDispo :
                        mLivraison = false; break;
                    default :
                        mLivraison = false; break;
                }
            }
        });
    }

    /**
     * Permet de récupérer les widgets, faire des testes et les mettres dans la base de donnée Firebase.
     * */
    public void publier(){
        mBtnContinuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String libelle = mLibelle.getText().toString().trim();
                String prix = mPrix.getText().toString().trim();
                String garantie = mGarantie.getText().toString().trim();
                String nombre = mNombre.getText().toString().trim();
                String descrip = mDescription.getText().toString().trim();
                String newVente = mNewAdresse.getText().toString().trim();

                /**
                 * Données obligatoires pour publier un produit.
                 */
                if(TextUtils.isEmpty(libelle)){
                    Toast.makeText(getContext(), "Donnez un libelle", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(prix)){
                    Toast.makeText(getContext(), "Donnez un prix", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(garantie)){
                    Toast.makeText(getContext(), "Donnez une garantie", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(nombre)){
                    Toast.makeText(getContext(), "Donnez le nombre de bien", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(descrip)){
                    Toast.makeText(getContext(), "Donnez une descrip", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(newVente)){
                    newVente = sCurrentUser.getAdresse();
                }

                Calendar calendar = Calendar.getInstance();
                String  currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());


                if(sCurrentUser != null){

                    Etudiant vendeur = new Etudiant(sCurrentUser.getIdEtu(), sCurrentUser.getPrenomEtu(), sCurrentUser.getNomEtu(),
                            sCurrentUser.getNumTelephoneEtu(), sCurrentUser.getPhoto(), newVente, sCurrentUser.getDetailsPrestations(), sCurrentUser.getNote(), sCurrentUser.getNbEval()); // Le vendeur.

                    Bien newBien = new Bien(); // Le bien.

                    newBien.setVendeur(vendeur);
                    newBien.setDatePublication(currentDate);
                    newBien.setLivraison(mLivraison);
                    newBien.setCategorie(mCategorie);
                    newBien.setLibelle(libelle);
                    newBien.setPrixBien(Integer.parseInt(prix));
                    newBien.setGarantie(garantie);
                    newBien.setNombreBien(Integer.parseInt(nombre));
                    newBien.setDescription(descrip);
                    newBien.setTypeVente(mTypeVente);

                    if(mBitmapList.size() > 0){
                        Log.d("Bitmapp2 ", mBitmapList.toString());
                        //DataBase.publierBien(getContext(), mBitmapList, newBien);
                        //Log.d("TESTTTTTTTTT1",sPUBTest+"");

                        if(DataBase.publierBien(getContext(), mBitmapList, newBien)){
                            //Log.d("TESTTTTTTTTT2",sPUBTest+"");

                            Toast.makeText(getContext(), "Publication réussi", Toast.LENGTH_LONG).show();
                            Intent versCatalogue = new Intent(getContext(), MainActivity.class);
                            startActivity(versCatalogue);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getContext(), "Erreur de Publication", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Choissiez une image svp", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(getContext(), "Vous devez vous authentifiez, avant de publier un bien", Toast.LENGTH_LONG).show();
                    Intent connexion = new Intent(getContext(), Connexion.class);
                    boolean vente = true;
                    connexion.putExtra("vente", vente);
                    startActivity(connexion);
                }

            }
        });
    }

    /**
     * Permet de récupérer le resultat retourné par intent (takePic)
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Execution", "Bien");

        switch (requestCode) {

            case REQUEST_CODE_F :
                mImageBitmapF = (Bitmap) data.getExtras().get("data");
                mBitmapList.add(mImageBitmapF);
                mPhotoBienF.setImageBitmap(mImageBitmapF); break;

            case REQUEST_CODE_A :
                mImageBitmapA = (Bitmap) data.getExtras().get("data");
                mBitmapList.add(mImageBitmapA);
                mPhotoBienA.setImageBitmap(mImageBitmapA); break;

            case REQUEST_CODE_D :
                mImageBitmapD = (Bitmap) data.getExtras().get("data");
                mBitmapList.add(mImageBitmapD);
                mPhotoBienD.setImageBitmap(mImageBitmapD); break;

            case REQUEST_CODE_G :
                mImageBitmapG = (Bitmap) data.getExtras().get("data");
                mBitmapList.add(mImageBitmapG);
                mPhotoBienG.setImageBitmap(mImageBitmapG); break;
        }

    }


    /**
     * Permet de prendre plusieur photos
     */
    private void prendrePhotos() {

        /* Photo frontale */
        mPhotoBienF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityResult(REQUEST_CODE_F);
            }
        });

        /* Photo arrière */
        mPhotoBienA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityResult(REQUEST_CODE_A);
            }
        });

        /* Photo droite */
        mPhotoBienD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityResult(REQUEST_CODE_D);
            }
        });

        /* Photo gauche */
        mPhotoBienG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityResult(REQUEST_CODE_G);
            }
        });

    }

    /**
     * Permet de prerndre une photo avec un request code diférrent
     * @param rq
     */
    private void startActivityResult(int rq) {

        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.CAMERA }, rq);
        } else {

            if(takePic.resolveActivity(getContext().getPackageManager()) != null ){

                startActivityForResult(takePic, rq);
            } else {
                Toast.makeText(getContext(), "Appareil photo non disponible sur cet appareil !", Toast.LENGTH_LONG).show();
            }
        }

    }
}
