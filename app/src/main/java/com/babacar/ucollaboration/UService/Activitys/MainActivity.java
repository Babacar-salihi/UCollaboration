package com.babacar.ucollaboration.UService.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.babacar.ucollaboration.Globals.Activitys.Acceuil;
import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UService.Fragments.FragmentAcceuil;
import com.babacar.ucollaboration.UService.Fragments.FragmentCategorie;
import com.babacar.ucollaboration.UService.Fragments.FragmentCompte;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNav;
    private FrameLayout mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uservice_activity_main);

        referenceWidget(); // Méthode permettant de référencer les widgets de l'uservice_activity_main.
        clicMenu(); // Méthode permettant de gérer les transitions entre fragments.

        getSupportFragmentManager().beginTransaction().replace(R.id.uservice_fragment0, new FragmentAcceuil()).commit();
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidget() {

        this.mFragment = findViewById(R.id.uservice_fragment0);
        this.mBottomNav = findViewById(R.id.uservice_bottomNav);
    }

    /**
     * permet de gérer les transitions entre fragments.
     */
    private void clicMenu() {

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment selectedFragment = null;

                switch (menuItem.getItemId()) {

                    case R.id.uservice_menu_acceuil:
                        selectedFragment = new FragmentAcceuil(); break;

                    case R.id.uservice_menu_categ:
                        selectedFragment = new FragmentCategorie(); break;

                    case R.id.uservice_menu_compte:
                        selectedFragment = new FragmentCompte(); break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.uservice_fragment0, selectedFragment).commit();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Acceuil.class));
    }
}
