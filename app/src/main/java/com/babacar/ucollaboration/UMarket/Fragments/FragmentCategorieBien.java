package com.babacar.ucollaboration.UMarket.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMarket.Activitys.CategoriesBien;

import java.net.Inet4Address;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategorieBien extends Fragment implements View.OnClickListener {

    private View mView;
    private CardView mCardDocs, mCardFourniture, mCardTicket, mCardHT, mCardFashion, mCardAlimentation, mCardBeaute, mCardElect, mCardTel;
    private String categ;


    public FragmentCategorieBien() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_categorie, container, false);
        referenceWidgets(); // Méthode pour référencer les widgets.

        this.mCardDocs.setOnClickListener(this);
        this.mCardFourniture.setOnClickListener(this);
        this.mCardTicket.setOnClickListener(this);
        this.mCardHT.setOnClickListener(this);
        this.mCardFashion.setOnClickListener(this);
        this.mCardAlimentation.setOnClickListener(this);
        this.mCardBeaute.setOnClickListener(this);
        this.mCardElect.setOnClickListener(this);
        this.mCardTel.setOnClickListener(this);

        return mView;
    }

    /**
     * Permet de référencer les widgets de l'adapter catalogue.
     */
    private void referenceWidgets(){

        this.mCardDocs = mView.findViewById(R.id.umarket_categ_docs);
        this.mCardFourniture = mView.findViewById(R.id.umarket_categ_fourniture);
        this.mCardTicket = mView.findViewById(R.id.umarket_categ_ticket);
        this.mCardHT = mView.findViewById(R.id.umarket_categ_highTech);
        this.mCardFashion = mView.findViewById(R.id.umarket_categ_mode);
        this.mCardAlimentation = mView.findViewById(R.id.umarket_categ_aliment);
        this.mCardBeaute = mView.findViewById(R.id.umarket_categ_beauty);
        this.mCardElect = mView.findViewById(R.id.umarket_categ_elect);
        this.mCardTel = mView.findViewById(R.id.umarket_categ_tel);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.umarket_categ_docs :
                categ = "Document"; break;
            case R.id.umarket_categ_fourniture:
                categ = "Fourniture"; break;
            case R.id.umarket_categ_ticket:
                categ = "Ticket"; break;
            case R.id.umarket_categ_highTech:
                categ = "High-Tech"; break;
            case R.id.umarket_categ_mode:
                categ = "Fashion"; break;
            case R.id.umarket_categ_aliment:
                categ = "Alimentation"; break;
            case R.id.umarket_categ_beauty:
                categ = "Santé et beauté"; break;
            case R.id.umarket_categ_elect:
                categ = "Electronique"; break;
            case R.id.umarket_categ_tel:
                categ = "Téléphone"; break;
        }
        Intent categorie = new Intent(getContext(), CategoriesBien.class);
        categorie.putExtra("CategorieBien", categ);
        startActivity(categorie);

    }
}
