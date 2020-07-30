package com.babacar.ucollaboration.Globals.Activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Activitys.MainActivity;


public class EmailVerification extends AppCompatActivity {

    private CardView mVerificationCard_Gmail;
    private TextView mTextViewPasser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.global_activity_email_verification);

        referenceWidgets(); // Méthode pour référencer les widgets.
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.mVerificationCard_Gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EmailVerification.this, "Verification", Toast.LENGTH_SHORT).show();
                String url = "https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
                Intent acceuil = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(acceuil);
                finish();
            }
        });
        passer(); // Méthode pour passer la validation du compte et le faire ultérieurement.
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        this.mVerificationCard_Gmail = findViewById(R.id.verification_cardview_gmail);
        this.mTextViewPasser = findViewById(R.id.verification_btnPasser);
    }

    /**
     * Permet de passer la validation du compte et le faire ultérieurement.
     */
    private void passer() {

        mTextViewPasser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Acceuil.class));
            }
        });
    }
}
