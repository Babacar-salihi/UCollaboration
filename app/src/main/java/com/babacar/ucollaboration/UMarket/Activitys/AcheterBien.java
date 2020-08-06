package com.babacar.ucollaboration.UMarket.Activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.babacar.ucollaboration.Globals.Activitys.UserSpace;
import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.Globals.Models.Etudiant;
import com.babacar.ucollaboration.Globals.Utilitaires.SplashCreenOK;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Adapters.RecyclerViewLivreur;
import com.babacar.ucollaboration.UMarket.Modeles.Bien;
import com.babacar.ucollaboration.UMarket.Modeles.DetailsPrestation;
import com.babacar.ucollaboration.UMarket.Modeles.Livreur;
import com.babacar.ucollaboration.UService.Models.Bosseur;
import com.google.android.gms.common.internal.Objects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.babacar.ucollaboration.Globals.Activitys.Connexion.checkConnectivity;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.getUserById;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.keyDetails;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sRefUService;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sReference;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sTestGettingSalesMan;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.vendeur1;
import static com.babacar.ucollaboration.UMarket.Activitys.ListeUser.getBienById;


public class AcheterBien extends AppCompatActivity {

    private com.babacar.ucollaboration.UMarket.Modeles.Panier mAchatBien;
    private CalendarView mCalendarView;

    private RecyclerView mRecyclerView;
    private CheckBox mBoxEtape0, mBoxEtape1, mBoxEtape2, mBoxEtape3;
    private CardView mLayoutEtape0;
    private LinearLayout mLayoutEtape1, mLayoutEtape2, mLayoutEtape3, mLayoutEtape1AvLiv;
    private RadioGroup mRadioGroupChoixLiv, mRadioGroupModeLiv, mRadioGroupChoixPaiement, mRadioGroupChoixFacture;
    private EditText mAdrLivraison, mPrixAchat;
    private Button mBtnFinaliser;
    private String mDateRV;

    private int  mLivreur; // Choix Livreur: autre livreur: -1, rien: 0, vendeur: 1.
    private int mLiv; // Choix livraison: sans livraison: -1, rien: 0, avec livraison: 1.
    private int mTypePaiement; // Choix paiement: Operateur: -1, rien 0, Arrivé payé: 1.
    private int mFacture; // Choix facture: Sans facture: -1, rien 0, Avec facture: 1.
    private List<Etudiant> mLivreurs = new ArrayList<>(10);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umarket_activity_acheter_bien);

        referenceWidgets(); // Méthode pour référencer les widgets.
        etapeTerminer(); // Méthode pour mettre fin à une étape.
        radioGroupManager(); // Méthode pour gérer le RadioGroup choixLiv.
        finalisationAchat(); // Méthode pour finaliser l'achat.
        dateRV(); // Méthode pour choisir la date de rendez-vous.
        int position = getIntent().getIntExtra("PositionAchatBien", 0);


        Log.d("Positionnnnnnn", position+"");
    }

    @Override
    protected void onStart() {
        super.onStart();

        sRefUService.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mLivreurs.clear();
                for (DataSnapshot livreur : dataSnapshot.getChildren()) {

                    Bosseur bosseur = livreur.getValue(Bosseur.class);
                    Log.d("Bosseiiie", bosseur.toString());
                    if (bosseur.getIdEtu() != null && bosseur.getCategorieSocioProf().equalsIgnoreCase("livreur") && bosseur.isOnLine()) {

                        sReference.child("Etudiants")
                                .child(bosseur.getIdEtu())
                                .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Etudiant etudiant = dataSnapshot.getValue(Etudiant.class);
                                mLivreurs.add(etudiant);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                afficheLiv(mLivreurs); // Méthode pour afficher les livreurs.

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        mAchatBien = (com.babacar.ucollaboration.UMarket.Modeles.Panier) getIntent().getSerializableExtra("AchatBien");

        mCalendarView = findViewById(R.id.acheter_calendrier);

        mBoxEtape0 = findViewById(R.id.acheter_checkEtape0);
        mLayoutEtape0 = findViewById(R.id.acheter_layoutEtape0);
        mBoxEtape1 = findViewById(R.id.acheter_checkEtape1);
        mLayoutEtape1 = findViewById(R.id.acheter_layoutEtape1);
        mBoxEtape2 = findViewById(R.id.acheter_checkEtape2);
        mLayoutEtape2 = findViewById(R.id.acheter_layoutEtape2);
        mBoxEtape3 = findViewById(R.id.acheter_checkEtape3);
        mLayoutEtape3 = findViewById(R.id.acheter_layoutEtape3);


        mRadioGroupChoixLiv = findViewById(R.id.acheter_radioGroupLiv);
        mLayoutEtape1AvLiv = findViewById(R.id.acheter_layoutEtape1AvLiv);

        mRadioGroupModeLiv = findViewById(R.id.acheter_radioGroupModeLiv);

        mRadioGroupChoixPaiement = findViewById(R.id.acheter_radioGroupModePaiement);

        mRadioGroupChoixFacture = findViewById(R.id.acheter_radioGroupFacture);

        mRecyclerView = findViewById(R.id.acheter_recycleviewLivreur);

        mAdrLivraison = findViewById(R.id.acheter_ediAdrLiv);
        mPrixAchat = findViewById(R.id.acheter_editPrixAchat);
        mBtnFinaliser = findViewById(R.id.acheter_btnFinaliser);
    }

    /**
     * Permet d'afficher les livreurs
     */
    private void afficheLiv(List<Etudiant> livreurs) {

        /*List<Livreur> livreurs = new ArrayList<>(10);
        livreurs.add(new Livreur("Babacar", "Ndong", 781401217));
        livreurs.add(new Livreur("Nabou", "Ndong", 781739204));
        livreurs.add(new Livreur("Fallou", "Ndong", 708979067));
        livreurs.add(new Livreur("Mourtalla", "Ndong", 776879098));
        livreurs.add(new Livreur("Thierno", "Fall", 765678467));
        livreurs.add(new Livreur("Modou", "Fall", 776789015));*/

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecyclerViewLivreur(getApplicationContext(), livreurs));
    }

    /**
     * Permet de mettre fin à une étape, lors de l'achat d'un bien.
     */
    private void etapeTerminer() {

        // Etape0: - Rendez-vous - terminer
        mBoxEtape0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (mBoxEtape0.isChecked()) {

//                  mLayoutEtape1.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sli));
                    mLayoutEtape0.setVisibility(View.GONE);
                } else {

                    mLayoutEtape0.setVisibility(View.VISIBLE);
                }
            }
        });

        // Etape1: - Livraison - terminer
        mBoxEtape1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (mBoxEtape1.isChecked()) {

//                  mLayoutEtape1.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sli));
                    mLayoutEtape1.setVisibility(View.GONE);
                } else {

                    mLayoutEtape1.setVisibility(View.VISIBLE);
                }
            }
        });

        // Etape2: - Paiement - terminer
        mBoxEtape2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (mBoxEtape2.isChecked()) {

                    mLayoutEtape2.setVisibility(View.GONE);
                } else {

                    mLayoutEtape2.setVisibility(View.VISIBLE);
                }
            }
        });

        // Etape3: - Facturation - terminer
        mBoxEtape3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (mBoxEtape3.isChecked()) {

                    mLayoutEtape3.setVisibility(View.GONE);
                } else {

                    mLayoutEtape3.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * Permet de gérer les radioGroups
     */
    private void radioGroupManager() {

        // RadioGroup Livraison.
        mRadioGroupChoixLiv.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (group.getCheckedRadioButtonId()) {

                    case R.id.acheter_checkSansLiv :
                        mLiv = -1;
                        mLayoutEtape1AvLiv.setVisibility(View.GONE);
                        break;
                    case R.id.acheter_checkAVLiv :
                        mLiv = 1;
                        mLayoutEtape1AvLiv.setVisibility(View.VISIBLE);
                        livreur(); break; // Méthode pour déterminer le livreur.
                    default:
                        mLiv = 0;
                }
            }
        });

        // RadioGroup Paiement.
        mRadioGroupChoixPaiement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (group.getCheckedRadioButtonId()) {

                    case R.id.acheter_operateur :
                        mTypePaiement = -1; break;
                    case R.id.acheter_AP :
                        mTypePaiement = 1; break;
                    default :
                        mTypePaiement = 0; break;
                }

            }
        });

        // RadioGroup Facture
        mRadioGroupChoixFacture.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (group.getCheckedRadioButtonId()) {

                    case R.id.acheter_checkSansfacture :
                        mFacture = -1; break;
                    case R.id.acheter_checkAVfacture:
                        mFacture = 1; break;
                    default :
                        mFacture = 0;
                }

            }
        });
    }

    /**
     * Choix du livreur (Le vendeur ou un autre livreur).
     */
    private void livreur() {

        mRadioGroupModeLiv.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (mLivreurs.size() == 0) {

                    RadioButton autreLivs = findViewById(R.id.acheter_livAutreLivreur);
                    autreLivs.setEnabled(false);
                    autreLivs.setChecked(false);
                }

                switch (group.getCheckedRadioButtonId()) {

                    case R.id.acheter_livVendeur :
                        mLivreur = 1;
                        mRecyclerView.setVisibility(View.GONE); break;
                    case R.id.acheter_livAutreLivreur :
                        mLivreur = -1;
                        mRecyclerView.setVisibility(View.VISIBLE); break;
                    default:
                        mLivreur = 0;
                }

            }
        });
    }

    /**
     * Permet de finaliser l'achat
     */
    private void finalisationAchat() {

        mBtnFinaliser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkConnectivity(getApplicationContext())) {

                    /* Les testes */
                    // Choix livraison.
                    if (mLiv == 1) { // Avec livraison, il faut forcement donner l'adresse de livraison

                        // Choix du livreur.
                        if (mLivreur == 1) { // Le vendeur doit livrer le bien.
                            //detail.getLivreur().add(vendeur.getIdEtu());
                            Toast.makeText(AcheterBien.this, "Vendeur livreur", Toast.LENGTH_SHORT).show();
                        } else if (mLivreur == -1) { // Autre livreur.

                            Toast.makeText(AcheterBien.this, "Autre livreur", Toast.LENGTH_SHORT).show();
                        } else { // Pas de choix.

                            Toast.makeText(AcheterBien.this, "Votre choix livreur n'est pas faites!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        String adrLivraison = mAdrLivraison.getText().toString().trim();
                        if (TextUtils.isEmpty(adrLivraison)) {

                            Toast.makeText(getApplicationContext(), "Donnez l'adresse de livraison", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } else if (mLiv == -1){ // Sans livraison

                        Toast.makeText(AcheterBien.this, "Sans livraison", Toast.LENGTH_SHORT).show();
                    } else { // Pas de choix.

                        Toast.makeText(AcheterBien.this, "Votre choix sur la livraison n'est pas faites!", Toast.LENGTH_SHORT).show();
                    }

                    // Choix mode de paiement.
                    if (mTypePaiement == 1) { // Paiement arrivé payé.

                        Toast.makeText(AcheterBien.this, "Arrivé payé", Toast.LENGTH_SHORT).show();
                    } else if (mTypePaiement == -1) { // Paiement avec service opérateur.

                        Toast.makeText(AcheterBien.this, "Operateur", Toast.LENGTH_SHORT).show();
                    } else { // Pas de choix.

                        Toast.makeText(AcheterBien.this, "Votre choix sur le type de paiement n'est pas faites!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    // Choix de facturation.
                    if (mFacture == 1) { // Avec facture.

                        Toast.makeText(AcheterBien.this, "Avec facturation", Toast.LENGTH_SHORT).show();
                    } else if (mFacture == -1) { // Sans facture.

                        Toast.makeText(AcheterBien.this, "Sans facturation", Toast.LENGTH_SHORT).show();

                    } else { // Pas de choix.

                        Toast.makeText(AcheterBien.this, "Votre choix sur la facturation n'est pas faites!", Toast.LENGTH_LONG).show();
                    }

                    // Prix d'achat
                    final String prixAchat = mPrixAchat.getText().toString().trim();
                    if (TextUtils.isEmpty(prixAchat)) { // Il faut donner le prix d'achat aprés négociation.

                        Toast.makeText(AcheterBien.this, "Donnez le prix d'achat aprés négociation", Toast.LENGTH_LONG).show();
                        return;
                    }

                    final ProgressDialog progressDialog = new ProgressDialog(AcheterBien.this);
                    progressDialog.setTitle("Enregistrement en cours");
                    progressDialog.setMessage("Veuillez patientez s'il vous plait!");
                    progressDialog.show();

                    /* Création d'objets */
                    Bien bien = getBienById(mAchatBien.getBiens());
                    getUserById(bien.getVendeur().getIdEtu()); // DownLoading salesman informations.

                /*int text = 0;
                Log.d("GettingTeste", sTestGettingSalesMan+"");*/

                    // Waitting... to get salesman informations. IMPORTANT: NE PAS SUPPRIMER.
                /*do {
                    Log.d("WaitAchat", "Waitting... "+text);

                    if (text == 1)
                        getUserById(bien.getVendeur().getIdEtu()); // DownLoading salesman informations.
                    if (sTestGettingSalesMan == -1) {

                        Toast.makeText(getApplicationContext(), "Vérifier votre connexion", Toast.LENGTH_LONG).show();
                        break;
                    }
                    ++text;
                } while (sTestGettingSalesMan != 1);*/

                    final int position = getIntent().getIntExtra("PositionAchatBien", 0);
                    final DetailsPrestation detail = new DetailsPrestation();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            final Etudiant vendeur = vendeur1;
                            Log.d("GettingTeste", vendeur1+"");

                            if (sCurrentUser != null && vendeur.getIdEtu() != null) {

                                Calendar calendar = Calendar.getInstance();
                                String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime()); // Jour de l'achat.

                                detail.setIdDetail(keyDetails); // Id du detail(idAcheteur+today+idPanier
                                detail.setBiens(mAchatBien.getBiens());
                                detail.setQuantite(mAchatBien.getQuantiteAchat()); // Quantité de bien acheter.
                                detail.setDateVente(currentDate); // Date d'achat du bien (Date de négociation), déterminer par l'acheteur.
                                detail.setDateRV(mDateRV); // Date de rendez-vous, Déterminée par le vendeur.
                                /*Ligne de fantôme*///detail.setAcheteur(new Gson().toJson(sCurrentUser));
                                detail.setPrixAchat(Integer.parseInt(prixAchat));
                                detail.setAchatAcheve(false);
                                //detail.setLivreur();
                                //detail.setCodeLivraison();
                                /*==Modif==*/detail.setAcheteur(sCurrentUser.getIdEtu());
                                /*==Modif==*/detail.setVendeur(vendeur.getIdEtu());
                                /*==Modif==*/sCurrentUser.getDetailsPrestations().add(detail.getIdDetail()); // retail's id instead all retail infos into buyer.
                                /*==Modif==*/vendeur.getDetailsPrestations().add(detail.getIdDetail()); // retail's id instead all retail infos into salesman.
                                //upDateVendeurInBien(bien);
                                DataBase.upDateUserDetails(vendeur);
                                DataBase.ajouterDetails(detail); // Ajouter un nouveau details.
                                sCurrentUser.getPanier().remove(position); // Supprimer le bien au panier de l'acheteur.
                                DataBase.upDateUserDetails(sCurrentUser); // Save information to database

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        progressDialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), SplashCreenOK.class));
                                        finish();
                                    }

                                }, 2000); // Attendre 2s.
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(AcheterBien.this, "Vous n'êtes pas connecté à votre compte", Toast.LENGTH_LONG).show();
                            }

                        }
                    }, 5000);
                } else {

                    alertDialog();
                }

            }
        });
    }

    /**
     * Permet de renvoyer l'utilisateur dans les parametres
     * pour qu'il active sa connection à internet (Wifi ou Mobile).
     */
    private void alertDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Pas de connexion à internet")
                .setMessage("Allumer votre connexion à internet ou réessayer plus tard!")
                .setCancelable(false)
                .setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(getApplicationContext(), UserSpace.class));
                        finish();
                    }
                })
                .show();
    }

    /**
     * Permet de séléctionner la date de rendez-vous entre l'acheteur et le vendeur.
     */
    private void dateRV() {

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                mDateRV = dayOfMonth +"/"+ month+"/"+year;
            }
        });
    }

}
