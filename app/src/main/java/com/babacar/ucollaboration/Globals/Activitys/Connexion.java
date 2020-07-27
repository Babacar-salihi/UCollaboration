package com.babacar.ucollaboration.Globals.Activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    public void connexion(){
        mProgress.setVisibility(View.GONE);
        mBtnContinuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cacheClavier(); // Méthode pour cacher le clavier.

                String email = mEmail.getText().toString().trim();
                String pwd = mPwd.getText().toString().trim();

                /**
                 * Données obligatoires pour se connecter.
                 */
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Saisissez votre email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(getApplicationContext(), "Saisissez votre email", Toast.LENGTH_SHORT).show();
                    return;
                }
                mBtnContinuer.setEnabled(false);
                mProgress.setVisibility(View.VISIBLE);
                sConnexTest = false;
                Log.d("ConnexTest", String.valueOf(sConnexTest));
                DataBase.connexion(getApplicationContext(), email, pwd);
                // Attente de 7s .
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ConnexTest", String.valueOf(sConnexTest));
                        if(sConnexTest){

                            Log.d("ConnexTest", String.valueOf(sConnexTest));
                            Intent versSplash = new Intent(getApplicationContext(), SplashCreenOK.class);
                            startActivity(versSplash);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Veuillez vérifier votre connexion à internet!", Toast.LENGTH_SHORT).show();
                            mBtnContinuer.setEnabled(true);
                            mProgress.setVisibility(View.GONE);
                        }

                    }
                }, 10000);
            }
        });
    }

    /**
     * Permet de récupérer son mot de passe.
     */
    private void pwdOublie(){

        mPwdOublie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(mEmail.getText().toString())) {

                    DataBase.pwdOublié(getApplicationContext(), mEmail.getText().toString());
                } else {

                    Toast.makeText(getApplicationContext() , "Donnez votre email!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * Permet de cacher le clavier après saisie et soumission.
     */
    private void cacheClavier() {

        View view = getCurrentFocus();

        Log.d("VIEWWWW", view+"");
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
}
