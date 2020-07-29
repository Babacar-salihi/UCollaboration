package com.babacar.ucollaboration.UMarket.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.babacar.ucollaboration.Globals.Activitys.Acceuil;
import com.babacar.ucollaboration.Globals.Activitys.Connexion;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Fragments.FragmentAcceuil;
import com.babacar.ucollaboration.UMarket.Fragments.FragmentCategorieBien;
import com.babacar.ucollaboration.UMarket.Fragments.FragmentVendre;
import com.babacar.ucollaboration.UMarket.Modeles.Bien;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sCurrentUser;
import static com.babacar.ucollaboration.UMarket.Fragments.FragmentAcceuil.sBienList;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umarket_activity_main);

        referenceWidgets(); // Methode pour référencer les widgets de umarket_activity_main.

        mBottomNav.setOnNavigationItemSelectedListener(navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment0, new FragmentAcceuil()).commit();
    }

    /**
     * Permet de référencer les widgets de umarket_activity_main.
     */
    private void referenceWidgets(){

        mBottomNav = findViewById(R.id.navigation);
    }

    /**
     * Permet de changer le fragment choisi lors du clique sur un item du menu.
     */
    private final BottomNavigationView.OnNavigationItemSelectedListener navigation = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectFragment = new Fragment();
            switch (menuItem.getItemId()) {
                case R.id.menu_acceuil :
                    selectFragment = new FragmentAcceuil();
                    break;
                case R.id.menu_vendre :
                    if (sCurrentUser == null) {
                        startActivity(new Intent(getApplicationContext(), Connexion.class)); break;
                    } else {
                        selectFragment = new FragmentVendre(); break;
                    }
                case R.id.menu_categ :
                    selectFragment = new FragmentCategorieBien();
                    break;

                default :
                    return false;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment0, selectFragment).commit();

            return true;
        }
    };

    /**
     * Permet de récupérer le bien corréspondant à l'id bien du panier passé en paramétre.
     * @param panier
     * @return
     */
    public static Bien getBienById(com.babacar.ucollaboration.UMarket.Modeles.Panier panier) {

        Bien bien = new Bien();
        for (Bien bien1 : sBienList) {

            if (bien1.getIdBien().equals(panier.getBiens())) {

                bien = bien1;
                break;
            }
        }

        return bien;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Acceuil.class));
    }



    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.slider_left_position, R.anim.slider_out_right);
    }
}
