package com.babacar.ucollaboration.UMaps.SqliteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.babacar.ucollaboration.UMaps.Models.Histo;

public class HistoHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "historique.db";
    public static final String TABLE_NAME = "historiques";
    public static final String COLUMN_0_NAME = "idHisto";
    public static final String COLUMN_1_NAME = "nomLieu";
    public static final String COLUMN_2_NAME = "latLieu";
    public static final String COLUMN_3_NAME = "lngLieu";
    public static final String COLUMN_4_NAME = "heure";


    public HistoHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String createDB = "create table "+TABLE_NAME+"( " +
                COLUMN_0_NAME +" Integer PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_1_NAME+" text not null," +
                COLUMN_2_NAME+" real not null, " +
                COLUMN_3_NAME+" real not null," +
                COLUMN_4_NAME+" text not null)";

        db.execSQL(createDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    /**
     * Permet d'inserer les historiques de recherche dans la base de données.
     * @return
     */
    public boolean insertIntoDB(Histo histo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1_NAME, histo.getPosition());
        contentValues.put(COLUMN_2_NAME, histo.getLat());
        contentValues.put(COLUMN_3_NAME, histo.getLong());
        contentValues.put(COLUMN_4_NAME, histo.getDate());

        long insert = db.insert(TABLE_NAME, null, contentValues);

        if (insert > 0)
            return true;
        else
            return false;
    }

    /**
     * Permet de récupérer les historiques de recherche stockés dans la base de données.
     * @return
     */
    public Cursor getHistos() {

        SQLiteDatabase db = this.getWritableDatabase();

        String select = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);

        return cursor;
    }

    /**
     * Permet de vider la base de données historique.
     * @return
     */
    public boolean deleteAllHisto() {

        SQLiteDatabase db = this.getWritableDatabase();
        long deleted = db.delete(TABLE_NAME, null, null);

        if (deleted > 0)
            return true;
        else
            return false;
    }

    /**
     * Permet de supprimer un historique de la base de données historique.
     * @return
     */
    public boolean deleteHisto(Histo histo) {

        SQLiteDatabase db = this.getWritableDatabase();
        long deleted = db.delete(TABLE_NAME, COLUMN_0_NAME+"= ?", new String[] {String.valueOf(histo.getIdLieu())});

        if (deleted > 0)
            return true;
        else
            return false;
    }
}
