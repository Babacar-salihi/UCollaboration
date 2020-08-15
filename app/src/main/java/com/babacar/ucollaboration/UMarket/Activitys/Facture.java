 package com.babacar.ucollaboration.UMarket.Activitys;

 import android.os.Bundle;
 import android.view.View;
 import android.widget.TextView;
 import android.widget.Toast;

 import androidx.appcompat.app.AppCompatActivity;

 import com.babacar.ucollaboration.Globals.Adapters.PopUpEvaluation;
 import com.babacar.ucollaboration.Globals.DataAccessObject.DataBase;
 import com.babacar.ucollaboration.R;
 import com.babacar.ucollaboration.UMarket.Modeles.Bien;
 import com.babacar.ucollaboration.UMarket.Modeles.DetailsPrestation;


 import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.acheteurName;
 import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.getDetailById;
 import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.getUserById;
 import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;
 import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.vendeur1;
 import static com.babacar.ucollaboration.UMarket.Activitys.ListeUser.getBienById;


 public class Facture extends AppCompatActivity {

     private TextView mTextDate;
     private String factureDispo;

     // Info Acheteur
     private TextView mTextNomAcheteur;

     // Détails facture
     private TextView mTextQuantite, mTextDesignation, mTextPrixUnitaire, mTextTotal;

     // Vendeur
     private TextView mTextNomVendeur;
     private TextView mTextNumVendeur;

     private DetailsPrestation details;
     private Bien bien;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umarket_activity_facture);


        factureDispo = getIntent().getStringExtra("Facture");
        referenceWidgets(); // Méthode pour lier les composants graphiques avec le code Java.
         details = getDetailById(factureDispo);
         bien = getBienById(details.getBiens());
        infalterFacture(); // Permet d'afficher les details du facture.
        pupUp(); // Afficher le popUp d'évaluation.

    }

     /**
      * Permet de référencer les widgets.
      */
     private void referenceWidgets() {

         // Info facture
         TextView textNumFacture = findViewById(R.id.facture_numeroFacture);
         this.mTextDate = findViewById(R.id.facture_dateFacture);
         // Info vendeur
         mTextNomVendeur = findViewById(R.id.facture_nomVendeur);
         mTextNumVendeur = findViewById(R.id.facture_numeroVendeur);

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


         // Vendeur
         mTextNomVendeur.setText(bien.getVendeur().getPrenomEtu()+" "+bien.getVendeur().getNomEtu());
         mTextNumVendeur.setText(bien.getVendeur().getNumTelephoneEtu()+"");

         // Acheteur
         this.mTextNomAcheteur.setText(acheteurName);

         this.mTextDate.setText("Date:"+ details.getDateRV());
         this.mTextQuantite.setText(details.getQuantite()+"");
         this.mTextDesignation.setText(bien.getLibelle());
         this.mTextPrixUnitaire.setText(details.getPrixAchat()+"\nfcfa");
         this.mTextTotal.setText(details.getPrixAchat()*details.getQuantite()+"\nfcfa");
     }

     /**
      * Afficher PopUp d'évaluation.
      */
     private void pupUp() {

         if (!details.isNoted() && (details.getVendeur().compareTo(sCurrentUser.getIdEtu()) != 0)) {

             final PopUpEvaluation alert = new PopUpEvaluation(this);
             alert.setTitre("Evaluation");
             alert.setMsg("Manifestez votre satisfaction");
             alert.getButtonOui().setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     Toast.makeText(Facture.this, alert.getRat().getRating()+"", Toast.LENGTH_SHORT).show();
                     //DataBase.getUserById();
                     if (alert.getRat().getRating() > 0 ) {

                         DataBase.setElevaluation(bien.getVendeur().getIdEtu(), alert.getRat().getRating());
                         Toast.makeText(Facture.this, "Merci", Toast.LENGTH_SHORT).show();
                         details.setNoted(true);
                         DataBase.upDateDetailsPrestation(details);
                         alert.dismiss();
                     } else {

                         Toast.makeText(Facture.this, "Ajouter des étoiles !", Toast.LENGTH_SHORT).show();
                         return;
                     }

                 }
             });
             alert.getButtonNon().setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     //DataBase.getUserById();
                     details.setNoted(true);
                     DataBase.upDateDetailsPrestation(details);
                     alert.dismiss();
                 }
             });
             alert.build();
         }
     }
}
