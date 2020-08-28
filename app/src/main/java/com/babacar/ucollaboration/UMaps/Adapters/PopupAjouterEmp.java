package com.babacar.ucollaboration.UMaps.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.babacar.ucollaboration.R;

public class PopupAjouterEmp extends Dialog {

    private TextView mTitre, mMessage;
    private Button mBtnOui, mBtnNon;
    private EditText mNomEmplacement;


    public PopupAjouterEmp(@NonNull Context context) {
        super(context);
        setContentView(R.layout.umaps_popup_ajouter_emp);

        mTitre = findViewById(R.id.umaps_ajouter_emplace_titre);
        mMessage = findViewById(R.id.umaps_ajouter_emplace_message);
        mBtnOui = findViewById(R.id.umaps_ajouter_emplace_btnOui);
        mBtnNon = findViewById(R.id.umaps_ajouter_emplace_btnNon);
        mNomEmplacement = findViewById(R.id.umaps_ajouter_emplace_nomEmplace);

    }

    public Button getBtnOui() {
        return mBtnOui;
    }

    public Button getBtnNon() {
        return mBtnNon;
    }

    public EditText getNomEmplacement() {
        return mNomEmplacement;
    }

    public void setTitre(String titre) {
        mTitre.setText(titre);
    }

    public void setMessage(String message) {
        mMessage.setText(message);
    }

    public void build() {

        show();
    }
}
