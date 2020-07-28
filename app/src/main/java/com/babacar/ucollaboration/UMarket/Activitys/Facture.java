 package com.babacar.ucollaboration.UMarket.Activitys;

 import android.os.Bundle;
 import android.widget.TextView;

 import androidx.appcompat.app.AppCompatActivity;

 import com.babacar.ucollaboration.R;
 import com.babacar.ucollaboration.UMarket.Modeles.Bien;
 import com.babacar.ucollaboration.UMarket.Modeles.DetailsPrestation;
 import com.google.gson.Gson;

 import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;
 import static com.babacar.ucollaboration.UMarket.Activitys.ListeUser.getBienById;


 public class Facture extends AppCompatActivity {

     private TextView mTextDate;
     private int factureDispo;

     // Info Acheteur
     private TextView mTextNomAcheteur;

     // Détails facture
     private TextView mTextQuantite, mTextDesignation, mTextPrixUnitaire, mTextTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umarket_activity_facture);

        factureDispo = getIntent().getIntExtra("Facture", -1);
        referenceWidgets(); // Méthode pour lier les composants graphiques avec le code Java.
        infalterFacture(); // Permet d'afficher les details du facture.

    }

     /**
      * Permet de référencer les widgets.
      */
     private void referenceWidgets() {

         // Info facture
         TextView textNumFacture = findViewById(R.id.facture_numeroFacture);
         this.mTextDate = findViewById(R.id.facture_dateFacture);
         // Info vendeur
         TextView textNomVendeur = findViewById(R.id.facture_nomVendeur);
         TextView textNumVendeur = findViewById(R.id.facture_numeroVendeur);

         this.mTextNomAcheteur = findViewById(R.id.facture_nomAcheteur);

         this.mTextQuantite = findViewById(R.id.facture_quantite);
         this.mTextDesignation = findViewById(R.id.facture_designation);
         this.mTextPrixUnitaire = findViewById(R.id.facture_prixUnit);
         this.mTextTotal = findViewById(R.id.facture_Total);
     }

     /**
      * Permet d'infalter la facture.
      */
     private void infalterFacture() {

         DetailsPrestation details = new Gson().fromJson(sCurrentUser.getDetailsPrestations().get(factureDispo), DetailsPrestation.class);
         Bien bien = getBienById(details.getBiens());

         this.mTextDate.setText("Date:"+ details.getDateRV());
         this.mTextNomAcheteur.setText(sCurrentUser.getPrenomEtu()+" "+sCurrentUser.getNomEtu());
         this.mTextQuantite.setText(details.getQuantite()+"");
         this.mTextDesignation.setText(bien.getLibelle());
         this.mTextPrixUnitaire.setText(details.getPrixAchat()+"\nfcfa");
         this.mTextTotal.setText(details.getPrixAchat()*details.getQuantite()+"\nfcfa");
     }
}
