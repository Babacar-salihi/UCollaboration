package com.babacar.ucollaboration.Globals.Activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.Globals.Utilitaires.SplashCreenOK;
import com.babacar.ucollaboration.R;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sConnexTest;


public class Connexion extends AppCompatActivity {

    private EditText mEmail, mPwd;
    private TextView mPwdOublie;
    private Button mBtnContinuer;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.global_activity_connexion);

        referenceWidgets(); // Permet de référencer les widgets.
        connexion(); // Permet de se connecter si on a déja un compte.
        pwdOublie(); // Méthode pour récupérer son mot de passe.
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        this.mEmail = findViewById(R.id.connex_email);
        this.mPwd = findViewById(R.id.connex_pwd);
        this.mPwdOublie = findViewById(R.id.pwdOublie);
        this.mBtnContinuer = findViewById(R.id.connex_btnContinuer);
        this.mProgress = findViewById(R.id.progress);

    }

    private void connexion() {
        mProgress.setVisibility(View.GONE);
        mBtnContinuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cacheClavier(); // Méthode pour cacher le clavier.

                if (checkConnectivity(Connexion.this)) {

                    String email = mEmail.getText().toString().trim();
                    String pwd = mPwd.getText().toString().trim();

                    // Données obligatoires pour se connecter.
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Saisissez votre email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(pwd)) {
                        Toast.makeText(getApplicationContext(), "Saisissez votre email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mBtnContinuer.setEnabled(false);
                    mProgress.setVisibility(View.VISIBLE);
                    sConnexTest = false;
                    DataBase.connexion(getApplicationContext(), email, pwd);
                    // Attente de 10s .
                    waitAndVerifConnex(10000); // Méthode permettant d'attendre la vérification.

                } else {

                    alertDialog();
                }
            }
        });
    }

    /**
     * Permet d'attendre un certain temps avant d'exécuter le code de verification de la connexion.
     */
    private void waitAndVerifConnex(final long time) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("ConnexTest", String.valueOf(sConnexTest));
                if (sConnexTest) {

                    Intent versSplash = new Intent(getApplicationContext(), SplashCreenOK.class);
                    startActivity(versSplash);
                    finish();
                } else {

                    Log.d("Timmer", (time/2)+"");

                    if (time/2 > 0) {
                        waitAndVerifConnex(time/2);
                    } else {
                        Toast.makeText(getApplicationContext(), "Veuillez vérifier votre connexion à internet!", Toast.LENGTH_SHORT).show();
                        mBtnContinuer.setEnabled(true);
                        mProgress.setVisibility(View.GONE);
                    }
                }

            }
        }, time);
    }


    /**
     * Permet de récupérer son mot de passe.
     */
    private void pwdOublie() {

        mPwdOublie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(mEmail.getText().toString())) {

                    DataBase.pwdOublie(getApplicationContext(), mEmail.getText().toString());
                } else {

                    Toast.makeText(getApplicationContext(), "Donnez votre email!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Permet de cacher le clavier après saisie et soumission.
     */
    private void cacheClavier() {

        View view = getCurrentFocus();

        Log.d("VIEWWWW", view + "");
        if (view != null) {
            InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.slider_left_position, R.anim.slider_out_right);
    }

    /**
     * Permet de vérifier si l'utilisateur est connecté à internet ou pas.
     */
    public static boolean checkConnectivity(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnex = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnex = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConnex != null && wifiConnex.isConnected()) || (mobileConnex != null && mobileConnex.isConnected()))
            return true;
        else
            return false;
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
}