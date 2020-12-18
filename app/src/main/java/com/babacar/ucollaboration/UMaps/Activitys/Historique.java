package com.babacar.ucollaboration.UMaps.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.babacar.ucollaboration.R;
import com.babacar.ucollaboration.UMaps.Adapters.RecyclerViewHisto;
import com.babacar.ucollaboration.UMaps.Models.Histo;
import com.babacar.ucollaboration.UMaps.SqliteDB.HistoHelper;

import java.util.ArrayList;
import java.util.List;

public class Historique extends AppCompatActivity {

    private List<Histo> mHistoList;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umaps_activity_historique);

        referenceWidgets(); // Méthode permettant de réferencer les widgets.
        mHistoList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getHistoriques(); // Méthode permettant de récupérer et d'afficher l'historique de recherche.
    }

    /**
     * Permet de référencer les widgets.
     */
    private void referenceWidgets() {

        this.mRecyclerView = findViewById(R.id.umaps_recycler);
    }

    /**
     * Permet de récupérer et d'afficher l'historique de recherche.
     */
    private void getHistoriques() {

        HistoHelper helper = new HistoHelper(getApplicationContext());
        Cursor cursor = helper.getHistos();

        if (cursor != null) {
            mHistoList.clear();

            while (cursor.moveToNext()) {

                Histo histo = new Histo();

                histo.setIdLieu(String.valueOf(cursor.getInt(0)));
                histo.setPosition(cursor.getString(1));
                histo.setLat(cursor.getDouble(2));
                histo.setLong(cursor.getDouble(3));
                histo.setDate(cursor.getString(4));

                mHistoList.add(histo);
            }
            inflateHisto(mHistoList);
            if (mHistoList.size() == 0) {

                RelativeLayout hide = findViewById(R.id.umaps_contener1);
                hide.setVisibility(View.GONE);
                TextView showMsg = findViewById(R.id.umaps_notHisto);
                showMsg.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Permet d'afficher l'historique de recherche.
     */
    private void inflateHisto(List<Histo> histos) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        final RecyclerViewHisto adapter = new RecyclerViewHisto(getApplicationContext(), histos);
        mRecyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                Histo histo = mHistoList.get(viewHolder.getAdapterPosition());
                boolean isDeteted = new HistoHelper(getApplicationContext()).deleteHisto(histo);

                if (isDeteted) {

                    Toast.makeText(Historique.this, "Supprimé", Toast.LENGTH_SHORT).show();
                    mHistoList.remove(viewHolder.getAdapterPosition());
                    adapter.notifyDataSetChanged();
                    if (mHistoList.size() == 0) {

                        RelativeLayout hide = findViewById(R.id.umaps_contener1);
                        hide.setVisibility(View.GONE);
                        TextView showMsg = findViewById(R.id.umaps_notHisto);
                        showMsg.setVisibility(View.VISIBLE);
                    }
                } else {

                    Toast.makeText(Historique.this, "KOOOOO", Toast.LENGTH_SHORT).show();
                }

            }
        }).attachToRecyclerView(mRecyclerView);
    }

    /**
     * Permet de supprimer tout l'historique de recherche.
     * @param view
     */
    public void suppAll(View view) {

        boolean isSuccessed = new HistoHelper(getApplicationContext()).deleteAllHisto();

        if (isSuccessed) {

            mHistoList.clear();
            inflateHisto(mHistoList);
            RelativeLayout hide = findViewById(R.id.umaps_contener1);
            hide.setVisibility(View.GONE);
            TextView showMsg = findViewById(R.id.umaps_notHisto);
            showMsg.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Supprimer", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "KOOOOOOOO", Toast.LENGTH_SHORT).show();
    }



}
