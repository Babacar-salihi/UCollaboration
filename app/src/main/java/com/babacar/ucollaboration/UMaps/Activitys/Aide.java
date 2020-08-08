package com.babacar.ucollaboration.UMaps.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMaps.Models.Lieu;
import com.babacar.ucollaboration.UMaps.Models.LieuInconnu;

public class Aide extends AppCompatActivity {

    private EditText mNomLieu;
    private EditText mDescriptLieu;
    private String str_nomLieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umaps_activity_aide);

        str_nomLieu = getIntent().getStringExtra("inconnu");
        referencerWidget(); // Méthode permettant de référencer les widgets.
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(str_nomLieu.length() > 0)
            mNomLieu.setText(str_nomLieu);
    }

    /**
     * Permet de réferencer les widget
     */
    private void referencerWidget() {

        this.mNomLieu = findViewById(R.id.umaps_ajouterLieu_nom);
        this.mDescriptLieu = findViewById(R.id.umaps_ajouterLieu_descript);
    }

    /**
     * Permet de enregistrer le lieu.
     * @param view
     */
    public void validder(View view) {

        String nomLieu = mNomLieu.getText().toString().trim();
        String descript = mDescriptLieu.getText().toString().trim();

        if (TextUtils.isEmpty(nomLieu)) {

            Toast.makeText(getApplicationContext(), "Le nom du lieu est obligatoire", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(descript)) {

            Toast.makeText(getApplicationContext(), "Ajouter une description du lieu", Toast.LENGTH_LONG).show();
            return;
        }

        LieuInconnu newLieu = new LieuInconnu();
        newLieu.setNomLieu(nomLieu);
        newLieu.setDescriptLieu(descript);

        DataBase.addLieuInconnu(newLieu); // Ajouter le lieu dans la liste des lieux inconnus.

        Toast.makeText(getApplicationContext(),"Merci beaucoup", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
