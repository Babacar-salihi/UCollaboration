package com.babacar.ucollaboration.Globals.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.Globals.Models.Etudiant;
import com.babacar.ucollaboration.Globals.Utilitaires.SplashCreenOK;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMaps.Activitys.MainActivity;
import com.babacar.ucollaboration.UService.Models.Bosseur;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;

public class CreerComptePro extends AppCompatActivity {

    private Spinner mSpinnerProfession;
    private String mCategSocio;
    private int mNci;
    private EditText mEditTextCategSocio, mEditTextNCI;
    private Button mBtnSave;
    //private CardView mCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte_pro);

        referencerWidget(); // Méthode permettant de référencer les widgets.
        categSocioEco(); // Méthode permettant de choisir une catégorie seocio économique.
        saveInfo(); // Méthode permettant de mettre les informations dans la base de données.
        //ajouterLieu(); // Méthode permettant d'ajouter un lieu sur la carte par un bosseur.
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referencerWidget() {

        this.mSpinnerProfession = findViewById(R.id.comptePro_spinner);
        this.mEditTextCategSocio = findViewById(R.id.autre_Profession);
        this.mBtnSave = findViewById(R.id.comptePro_BtnContinuer);
        this.mEditTextNCI = findViewById(R.id.comptePro_NCI);
        //this.mCardView = findViewById(R.id.uservice_addLieu);
    }

    /**
     * Permet de choisir parmis les catégories qui sont propososée.
     */
    public void categSocioEco() {

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.categorieSocioEconomique, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerProfession.setAdapter(adapter);
        mSpinnerProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mCategSocio = parent.getItemAtPosition(position).toString();
                if (mCategSocio.equalsIgnoreCase("autres")) {
                    mSpinnerProfession.setVisibility(View.GONE);
                    mEditTextCategSocio.setVisibility(View.VISIBLE);
                }
                if (mCategSocio.equalsIgnoreCase("Livreur")) {

                    mEditTextNCI.setVisibility(View.VISIBLE);
                } else {
                    mEditTextNCI.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCategSocio = "Etudiant";
            }
        });
    }

    /**
     * Permet de mettre les informations dans la base de données.
     */
    private void saveInfo() {

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "OKKK", Toast.LENGTH_SHORT).show();

                // Si l'utilisateur saisie une "autre" catégorie socio.
                if (mCategSocio.equalsIgnoreCase("autres")) {
                    mCategSocio = mEditTextCategSocio.getText().toString().trim();
                    if (TextUtils.isEmpty(mCategSocio)) {
                        Toast.makeText(getApplicationContext(), "Donnez votre catégorie socio-économique", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if (mCategSocio.equalsIgnoreCase("Livreur")) {
                    String str_nci = mEditTextNCI.getText().toString().trim();
                    if (TextUtils.isEmpty(str_nci)) {
                        Toast.makeText(getApplicationContext(), "Donnez votre numéro de carte", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        mNci = Integer.parseInt(str_nci);
                    }
                }

                Bosseur bosseur = new Bosseur(sCurrentUser.getIdEtu(), 0, mCategSocio, mNci, true);

                DataBase.createBosseur(bosseur);
                startActivity(new Intent(getApplicationContext(), SplashCreenOK.class));
            }
        });
    }

    /**
     * Permet d'accerder à la carte et ajouter un lieu
     */
    /*private void ajouterLieu() {

        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addLieu = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(addLieu);
            }
        });
    }*/

}
