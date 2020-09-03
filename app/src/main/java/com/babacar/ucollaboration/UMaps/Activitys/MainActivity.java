package com.babacar.ucollaboration.UMaps.Activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


import com.babacar.ucollaboration.Globals.Activitys.Acceuil;
import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMaps.Models.Histo;
import com.babacar.ucollaboration.UMaps.Models.Lieu;
import com.babacar.ucollaboration.UMaps.SqliteDB.HistoHelper;
import com.babacar.ucollaboration.UMaps.Utilitaires.UcadCarte;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.babacar.ucollaboration.Globals.Activitys.CreerComptePro.sBosseurEmp;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sConnexTest;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sLieux;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sNomLieu;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sRefLieux;
import static com.babacar.ucollaboration.UMaps.Utilitaires.UcadCarte.addMarker;
import static com.babacar.ucollaboration.UMaps.Utilitaires.UcadCarte.location;
import static com.babacar.ucollaboration.UMaps.Utilitaires.UcadCarte.moveCamera;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private Fragment mFragment; // Fragment contenant la carte.
    private List<Lieu> mLieux; // Listes de lieux.
    private LocationManager mLocationManager; // Localisation de l'utilisateur.
    private final int REQUEST_CODE = 30;
    private Lieu mYourPosition;
    private FloatingActionButton mBtnPlus, mBtnExplorer, mBtnHisto; // Boutons flottants.
    private CircleImageView mMaPosition;
    private LinearLayout mMoreOptions;
    private boolean testeToogle = false; // autres boutons en vue ou pas.
    private AutoCompleteTextView mAutoSearch; // AutoCompletion

    private static RelativeLayout mRelativeLayout; // Pour l'affichage du Snackbar.
    public static int sNbMarker = 0;
    public static boolean sSearchByHisto; // Chercher un lieu depuis l'historique.

    public static Context sContextUmaps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umaps_activity_main);

        referenceWidgets(); // Méthode pour référencer les widgets.
        mLieux = new ArrayList<>(4);
    }

    @Override
    protected void onStart() {
        super.onStart();

        sRefLieux.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                sLieux.clear();
                sNomLieu.clear();
                for (DataSnapshot lieu : dataSnapshot.getChildren()) {

                    Lieu lieu1 = lieu.getValue(Lieu.class);
                    sLieux.add(lieu1);
                    sNomLieu.add(lieu1.getPosition());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*TODO: Refaire ca*/
        if (sCurrentUser != null && (sCurrentUser.getPhoto().length() > 0)) {

            Glide.with(this)
                    .load(sCurrentUser.getPhoto())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mMaPosition);
        }

        if (!sBosseurEmp)
            plus(); // Méthode pour afficher plus options.
        else {

            mBtnPlus.setEnabled(false);
            sContextUmaps = this;
        }
        //getLieux(); // Méthode pour remplir sLieux.
        //chercher(); // Méthode pour chercher un lieu sur la carte.
        chercher2(); // Méthode pour chercher un lieu sur la carte avec auto-completion.

    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        mRelativeLayout = findViewById(R.id.umaps_layout);
        mFragment = getSupportFragmentManager().findFragmentById(R.id.fragment1);
        mYourPosition = new Lieu();
        mMaPosition = findViewById(R.id.umaps_user_maPosition);
        mAutoSearch = findViewById(R.id.search_lieu);
        mMoreOptions = findViewById(R.id.umaps_moreOptions);
        mBtnPlus = findViewById(R.id.umaps_floattingBtn_ajouter_lieu);
        mBtnExplorer = findViewById(R.id.umaps_floattingBtn_explorer);
        mBtnHisto = findViewById(R.id.umaps_floattingBtn_historiques);
    }


    /**
     * Permet de récupérer les lieux depuis la base de données.
     */
    private void getLieux() {

        //Database.getLieux();
        Log.d("Lieux", sLieux.size()+"");
    }


    /**
     * Abbonnement aux services de localisation
     */
    @Override
    protected void onResume() {
        super.onResume();

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        checkPermission(); // Méthode permettant de demander les permissions de localisation.
    }

    /**
     * Désabonnement aux services de localisation.
     */
    @Override
    protected void onPause() {
        super.onPause();

        if (mLocationManager != null)
            mLocationManager.removeUpdates(this);
    }

    /**
     * Permet de demander les permissions de localisation.
     */
    private void checkPermission() {

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ||
            ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ||
            ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET },
                    this.REQUEST_CODE);
            return;
        }

        // GPS
        if (mLocationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ))
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, this);

        // Données cellulaires (3G, 4G, ...)
        if (mLocationManager.isProviderEnabled( LocationManager.PASSIVE_PROVIDER ))
            mLocationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 5000, 1, this);

        // WIFI
        if (mLocationManager.isProviderEnabled( LocationManager.NETWORK_PROVIDER ))
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1, this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode != this.REQUEST_CODE )
            checkPermission(); // Méthode permettant de demander les permissions de localisation.
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Permet d'avoir des informations sur la position de l'utilisateur.
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {

        double lat = location.getLatitude();
        double lng = location.getLongitude();

        mYourPosition.setLat(lat);
        mYourPosition.setLong(lng);
        mYourPosition.setPosition("Ma position");

        //location(mYourPosition);
        Log.d("MaPosition", mYourPosition.toString());
    }

    /**
     *  Permet de gérer la recherche d'un lieu sur la carte avec auto complétion.
     */
    private void chercher2() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1, sNomLieu);
        mAutoSearch.setAdapter(adapter);

        mAutoSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId) {

                    case EditorInfo
                            .IME_ACTION_SEARCH :

                        hideKeyBoard(); // Cacher le clavier.

                        if (mAutoSearch.getText().toString().trim().length() != 0) {

                            if (sNomLieu.contains(mAutoSearch.getText().toString().trim())) { // Le lieu est connu.

                                for (Lieu lieu : sLieux) {

                                    if (lieu.getPosition().equalsIgnoreCase(mAutoSearch.getText().toString())) {

                                        insertIntoDB(lieu);
                                        moveCamera(lieu);
                                        mAutoSearch.clearFocus(); // Pour effacer la zone de texte.
                                    }
                                }
                            } else { // Si le lieu demandez n'est pas dans la liste proposé.
                                sNbMarker = 0;
                                for(Lieu lieu : sLieux) {
                                    /* Parcourir l'ensemble des lieux et récupérer ceux qui commence par la valeur du AutoCompleteTextView.getText()*/

                                    if ((mAutoSearch.getText().toString().trim().length() > 2) && (lieu.getPosition().toLowerCase().startsWith(mAutoSearch.getText().toString().trim().toLowerCase()))) { // Si le lieu n'est pas proposé mais qu'il ressemble à un lieu déja ajouté (Faculté, Toilette, ...).

                                        addMarker(lieu); // Méthode pour ajouter autant de marqueurs qu'il y a de lieu commencant par la valeur courante du champs de recherche.
                                        ++sNbMarker;
                                    }
                                }
                                if(sNbMarker == 0) { // Le lieu n'est pas connu.

                                    if (!sBosseurEmp)
                                        showSnackBar(); // Méthode permettant d'afficher le Snackbar.
                                    Toast.makeText(MainActivity.this, "Désolé! mais ce lieu n'est pas encore connu\n réessayez dans un court instant.", Toast.LENGTH_LONG).show();
                                }
                                Log.d("NBLIEUSSS", sNbMarker+"");

                            }
                        }
                        break;
                }

                return false;
            }
        });
    }

    /**
     * Permet d'inserer les historiques de recherche dans la base de données.
     */
    private void insertIntoDB(Lieu lieu) {

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        Histo histo = new Histo(lieu.getPosition(), lieu.getLat(), lieu.getLong(), currentDate);
        HistoHelper helper = new HistoHelper(getApplicationContext());
        boolean insert = helper.insertIntoDB(histo);

        /*if (insert)
            Toast.makeText(this, "OKKKKKKKKK", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "KOOOOOOOOO", Toast.LENGTH_SHORT).show();*/
    }

    /**
     * Permet d'afficher plus options.
     */
    private void plus() {

        mBtnPlus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                if (testeToogle) {

                    mMoreOptions.setVisibility(View.GONE);
                    testeToogle = false;
                } else {

                    Animation showOpstions = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.umaps_show_more_options);
                    mMoreOptions.setVisibility(View.VISIBLE);
                    mMoreOptions.setAnimation(showOpstions);

                    /* Position actuelle de l'utilisateur */
                    mMaPosition.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            location(mYourPosition);
                        }
                    });

                    /* Exloration */
                    mBtnExplorer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            UcadCarte.explorer();
                        }
                    });

                    /* Historique */
                    mBtnHisto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            startActivity(new Intent(getApplicationContext(), Historique.class));
                        }
                    });
                    testeToogle = true;

                }
            }
        });
    }

    /**
     * Permet de cacher le clavier.
     */
    private void hideKeyBoard() {
        View view = getCurrentFocus();

        if (view != null) {
            InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Permet d'afficher le Snackbar pour aider à ajouter le lieu.
     */
    private void showSnackBar() {

        Snackbar.make(mRelativeLayout, "Aidez à améliorer la carte", Snackbar.LENGTH_INDEFINITE)
                .setAction("OUI", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent ameliorer = new Intent(getApplicationContext(), Aide.class);
                        ameliorer.putExtra("inconnu", mAutoSearch.getText().toString().trim());
                        startActivity(ameliorer);
//                        DataBase.addLieuInconnu(mAutoSearch.getText().toString().trim()); // Ajouter le lieu dans la liste des lieux inconnus.

                    }
                })
                .setDuration(7000)
                .show();
    }

    /**
     * Permet d'afficher le Snackbar pour permettre aux bosseurs d'ajouter leur emplacement.
     */
    public void ajouterEmpBosseurSnackBar() {

        Snackbar.make(mRelativeLayout, "Voulez vous ajoutez cet emplacement? ", Snackbar.LENGTH_INDEFINITE)
                .setAction("OUI", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent ameliorer = new Intent(getApplicationContext(), Aide.class);
                        startActivity(ameliorer);
//                        DataBase.addLieuInconnu(mAutoSearch.getText().toString().trim()); // Ajouter le lieu dans la liste des lieux inconnus.

                    }
                })
                .setDuration(7000)
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (sBosseurEmp)
            sBosseurEmp = false;

        startActivity(new Intent(getApplicationContext(), Acceuil.class));
    }

}
