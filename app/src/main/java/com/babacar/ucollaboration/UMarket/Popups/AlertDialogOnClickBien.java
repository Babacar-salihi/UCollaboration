package com.babacar.ucollaboration.UMarket.Popups;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.babacar.ucollaboration.R;


public class AlertDialogOnClickBien extends Dialog {

    private TextView mTitre, mMessage;
    private Button mBtnAjouter, mBtnDetails;
    private String titre, message;

    public AlertDialogOnClickBien(@NonNull Context context) {
        super(context);
        setContentView(R.layout.umarket_adapter_alert_dialogue_bien);
        mTitre = findViewById(R.id.popup_bien_titre);
        mMessage = findViewById(R.id.popup_bien_message);
        mBtnAjouter = findViewById(R.id.popup_btnPanier);
        mBtnDetails = findViewById(R.id.popup_btnDetails);
    }

    public Button getBtnAjouter() {
        return mBtnAjouter;
    }

    public Button getBtnDetails() {
        return mBtnDetails;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void buil(){
        show();
        mTitre.setText(titre);
        mMessage.setText(message);
    }
}
