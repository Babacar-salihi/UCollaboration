package com.babacar.ucollaboration.Globals.Activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.Globals.Models.Etudiant;
import com.babacar.ucollaboration.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.babacar.ucollaboration.Globals.Activitys.Connexion.checkConnectivity;
import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sInscriptTest;


public class Inscription extends AppCompatActivity {

    private CircleImageView mUserProfilePic;
    private Spinner mSpinnerFac;
    private EditText mPrenom, mNom, mNumTel, mNumChambre, mEmail, mPwd, mDomicile, mEditTextDepart;
    private String mFac;
    private Button mBtnContinuer;
    private ProgressBar mInsProgress;
    private final int REQUEST_CODE = 133;
    private Uri mImageUri;
    //private Bitmap mImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.global_activity_inscription);

        referenceWidgets(); // Méthode permettant de référencer les widgets.
        choisirPhoto(); // Méthode permettant de choisir une photo de profile.
        faculte(); // Méthode permettant de choisir une faculté.
        creerCompte(); // Méthode permettant de créer un compte utilisateur.
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        this.mUserProfilePic = findViewById(R.id.inscription_userProfile);
        this.mInsProgress = findViewById(R.id.inscription_progress);
        this.mSpinnerFac = findViewById(R.id.inscription_spinner_userDepart);
        this.mPrenom = findViewById(R.id.inscription_userPrenom);
        this.mNom = findViewById(R.id.inscription_userNom);
        this.mNumTel = findViewById(R.id.inscription_userTel);
        this.mNumChambre = findViewById(R.id.inscription_userChambre);
        this.mEmail = findViewById(R.id.inscription_userEmail);
        this.mPwd = findViewById(R.id.inscription_userPwd);
        this.mDomicile = findViewById(R.id.inscription_userAdresse);
        this.mEditTextDepart = findViewById(R.id.inscription_userDepart);
        this.mBtnContinuer = findViewById(R.id.inscription_btnContinuer);
    }

    /**
     * Permet de choisir une photo.
     */
    public void choisirPhoto() {

        mUserProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageUri = null;
                Intent choisirPhoto = new Intent();
                choisirPhoto.setType("image/*");
                choisirPhoto.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(choisirPhoto, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            mImageUri = data.getData();
            mUserProfilePic.setImageURI(mImageUri);
            /*mImageBitmap = (Bitmap) data.getExtras().get("data");
            mUserProfilePic.setImageBitmap(mImageBitmap);*/

        }
    }

    /**
     * Permert de renseigner sa faculté
     */
    private void faculte() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplication(), R.array.facultes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinnerFac.setAdapter(adapter);
        mSpinnerFac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mFac = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Permet de créer un compte pour l'utilisateur.
     */
    public void creerCompte() {

        mBtnContinuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cacheClavier(); // Méthode pour cacher le clavier.

                if (checkConnectivity(Inscription.this)) {

                    String prenom = mPrenom.getText().toString().trim();
                    String nom = mNom.getText().toString().trim();
                    String tel = mNumTel.getText().toString().trim();
                    String chambre = mNumChambre.getText().toString().trim();
                    String email = mEmail.getText().toString().trim();
                    String pwd = mPwd.getText().toString().trim();
                    String adresse = mDomicile.getText().toString().trim();

                    //Données obligatoires pour s'inscrire.
                    if (TextUtils.isEmpty(prenom)) {
                        Toast.makeText(getApplicationContext(), "Donnez votre prenom", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(nom)) {
                        Toast.makeText(getApplicationContext(), "Donnez votre nom", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (tel != null && tel.length() != 9) {

                        Toast.makeText(getApplicationContext(), "Le numéro doit contenir que 9 chiffres!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Donnez un email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(pwd)) {
                        Toast.makeText(getApplicationContext(), "Donnez un mot de passe", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (pwd.length() < 6) { // 6 caractères au moins.
                        Toast.makeText(getApplicationContext(), "Pour plus de sécurité, votre mot de passe doit contenir au minimum 6 caractères!", Toast.LENGTH_LONG).show();
                        return;
                    }
                /*if (mFaculte.equalsIgnoreCase("autre")){
                    mFaculte = mEditTextDepart.getText().toString().trim();
                    if(TextUtils.isEmpty(mFaculte)){
                        Toast.makeText(getApplicationContext(), "Donnez votre departement", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }*/

                    mBtnContinuer.setEnabled(false);
                    mInsProgress.setVisibility(View.VISIBLE);

                    final Etudiant etudiant = new Etudiant();
                    etudiant.setPrenomEtu(prenom);
                    etudiant.setNomEtu(nom);
                    etudiant.setNumTelephoneEtu(Integer.parseInt(tel));
                    etudiant.setNumChambre(chambre);
                    etudiant.setEmail(email);
                    etudiant.setPassword(pwd);
                    etudiant.setAdresse(adresse);
                    etudiant.setNewAdresse(adresse);
                    etudiant.setFaculte(mFac);

                    Log.d("ETUDIANTTT", etudiant.toString());

                    sInscriptTest = false;
                    if (mImageUri != null) { // Inscription avec photo de profile.
                        DataBase.creationCompte(getApplicationContext(), etudiant, mImageUri);
                        // Cette partie permet de bloquer l'ecran de l'utilisateur le temps que l'inscription passe.
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("mInscriptTest1", sInscriptTest + "");
                                if (sInscriptTest) {

                                    Toast.makeText(getApplicationContext(), "Inscription reussi", Toast.LENGTH_SHORT).show();
                                    Intent versAcceuil = new Intent(getApplicationContext(), EmailVerification.class); // accéder à la 2ème étape (Vérification email).
                                    startActivity(versAcceuil);
                                    finish();
                                } else { // Else on attend encore 3 secondes.

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            if (sInscriptTest) {

                                                Toast.makeText(getApplicationContext(), "Inscription reussi", Toast.LENGTH_SHORT).show();
                                                Intent versAcceuil = new Intent(getApplicationContext(), EmailVerification.class);
                                                startActivity(versAcceuil);
                                                finish();
                                            } else { // Sinon on a redonne la main à l'utilisateur.
                                                Toast.makeText(getApplicationContext(), "Votre connexion est faible réessayez ulterieurment", Toast.LENGTH_LONG).show();
                                                mBtnContinuer.setEnabled(true);
                                                mInsProgress.setVisibility(View.GONE);
                                            }
                                        }
                                    }, 2000);

                                }
                            }
                        }, 10000);

                        // Cette partie permet de bloquer l'ecran de l'utilisateur le temps que l'inscription passe.
                    /*if (handler(10000)){ // On attend 10s.
                        Toast.makeText(getApplicationContext(), "Inscription reussi0", Toast.LENGTH_SHORT).show();
                    } else {
                        if (handler(5000)){ // On attend 5s.

                            Toast.makeText(getApplicationContext(), "Inscription reussi1", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Votre connexion est faible reessayez ulterieurment", Toast.LENGTH_LONG).show();
                            mBtnContinuer.setEnabled(true);
                            mInsProgress.setVisibility(View.GONE);
                        }

                    }*/

                    } else { // Inscription sans photo de profile.

                        DataBase.creationCompte(getApplicationContext(), etudiant);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (sInscriptTest) {
                                    Toast.makeText(getApplicationContext(), "Inscription reussi", Toast.LENGTH_SHORT).show();
                                    Intent versAcceuil = new Intent(getApplicationContext(), EmailVerification.class);
                                    startActivity(versAcceuil);
                                    finish();
                                } else {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (sInscriptTest) {
                                                Toast.makeText(getApplicationContext(), "Inscription reussi", Toast.LENGTH_SHORT).show();
                                                Intent versAcceuil = new Intent(getApplicationContext(), EmailVerification.class); // accéder à la 2ème étape (Vérification email).
                                                startActivity(versAcceuil);
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Votre connexion est faible réessayez ulterieurment", Toast.LENGTH_LONG).show();
                                                mBtnContinuer.setEnabled(true);
                                                mInsProgress.setVisibility(View.GONE);
                                            }
                                        }
                                    }, 3000);
                                }
                            }
                        }, 5000);
                    }

                } else {

                    alertDialog();
                }

            }
        });

    }

    /**
     * Permet d'attendre un certain temps, le temps que l'inscripttion se termine (Cas de bande passante faible).
     */
    /*private boolean handler(long duration){
        Log.d("DANSHANDLER", "DANS HANDLER OK");
        final boolean[] test = {false};
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(sInscriptTest){
                    Intent versAcceuil = new Intent(getApplicationContext(), SplashCreenOK.class);
                    startActivity(versAcceuil);
                    finish();
                    test[0] = true;
                    Log.d("DANSHANDLER", "DANS HANDLER OK test =" +test.toString());
                } else {
                    test[0] = false;
                }

            }
        }, duration);
        Log.d("DANSHANDLER", "FIN HANDLER");

        Log.d("DANSHANDLER", "FIN HANDLER OK test =" +test.toString());


        return test[0];

    }*/

    /**
     * Permet de cacher le clavier après saisie et soumission.
     */
    private void cacheClavier() {

        View view = getCurrentFocus();
        if(view != null) {

            InputMethodManager inputMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.slider_left_position, R.anim.slider_out_right);
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

                        startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
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
}
