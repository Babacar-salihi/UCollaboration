package com.babacar.ucollaboration.UInfos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

    /**
     * Permet d'afficher la documentation...
     * @param view
     */
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

    /**
     * Permet d'acceder à mon profile dans les réseaux sociaux...
     * @param view
     */
    public void reseaux(View view) {


        switch (view.getId()) {

            /* LinkedInd */
            case R.id.uinfos_linkedIn :
                connectionTo(Uri.parse("https://www.linkedin.com/in/babacar-ndong-757a271b3/"), "com.linkedInd.android");
                break;

            /* Twitter */
            case R.id.uinfos_twitter :
                connectionTo(Uri.parse("https://twitter.com/Babacar37633838"), "com.twitter.android");
                break;

            /* Instagram */
            case R.id.uinfos_insta :
                connectionTo(Uri.parse("http://instagram.com/_u/bab4car"), "com.instagram.android");
                break;

            /* Facebook */
            case R.id.uinfos_facebook :
                connectionTo(Uri.parse("https://web.facebook.com/babacar.ndong.758"), "com.facebook.android");
                break;

            /* Youtube */
            case R.id.uinfos_youtube :
                connectionTo(Uri.parse("http://instagram.com/_u/bab4car"), "com.instagram.android");
                break;
        }
    }

    /**
     * Permet de gérer les package et conenxion dans les différents réseaux sociaux.
     */
    private void connectionTo(Uri uri, String str_package) {

        Intent instagram = new Intent(Intent.ACTION_VIEW, uri);
        instagram.setPackage(str_package);
        try {
            startActivity(instagram);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
}
