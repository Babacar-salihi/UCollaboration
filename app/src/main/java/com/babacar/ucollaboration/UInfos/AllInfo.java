package com.babacar.ucollaboration.UInfos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.babacar.ucollaboration.MainActivity;
import com.babacar.ucollaboration.R;

public class AllInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uinfos_activity_all_info);
    }

    public void showDoc(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(AllInfo.this);

        switch (view.getId()) {

            case R.id.uinfos_umarketDocs:
                alert.setTitle("Documentation Umarket");
                alert.setMessage("Application e-commerce, permettant aux étudiant de vendre de acheter des biens.\n" +
                        "Elle englobe plusieurs fortionalités parmi les quelles je peux citer: \n - Acheter" +
                        "\n - Publier un bien" +
                        "\n - Appeler un vendeur " +
                        "\n - Fixer date une date de rendez vous avec le vendeur" +
                        "\n - Aimer un bien " +
                        "\n - Evaluer un vendeur" +
                        "\n - ...");
                alert.setPositiveButton("Voir plus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(AllInfo.this, "Video youtube Umarket", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setIcon(R.drawable.umarket);
                alert.show();
                break;

            case R.id.uinfos_userviceDocs:
                alert.setTitle("Documentation Uservice");
                alert.setMessage("Application qui traite tous ce qui est service.\n" +
                        "Elle englobe plusieurs fortionalités parmi les quelles je peux citer: \n - Acheter" +
                        "\n - Appeler un vendeur " +
                        "\n - Envoyer un message WhatsApp à un bosseur" +
                        "\n - Trouver l'emplacement d'un bosseur dans la carte Umaps " +
                        "\n - Evaluer un bosseur" +
                        "\n - ...");
                alert.setPositiveButton("Voir plus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(AllInfo.this, "Video youtube Uservice", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setIcon(R.drawable.umarket);
                alert.show();
                break;

            case R.id.uinfos_umapsDocs:
                alert.setTitle("Documentation Umaps");
                alert.setMessage("Application qui facilité aux étudiant leur l'intégration à l'UCAD.\n" +
                        "Elle englobe plusieurs fortionalités parmi les quelles je peux citer: \n - Acheter" +
                        "\n - Recherche de lieux " +
                        "\n - Localisation" +
                        "\n - Amélioration de la carte" +
                        "\n - Trouver l'emplacement d'un bosseur" +
                        "\n - Historique de recherche" +
                        "\n - ...");
                alert.setPositiveButton("Voir plus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(AllInfo.this, "Video youtube Umaps", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setIcon(R.drawable.umarket);
                alert.show();
                break;

        }
    }
}
