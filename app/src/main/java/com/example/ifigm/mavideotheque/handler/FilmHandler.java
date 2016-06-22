package com.example.ifigm.mavideotheque.handler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ifigm on 16/04/2016.
 */
public class FilmHandler extends SQLiteOpenHelper {

    private static final String FILM_ID = "Id";
    private static final String FILM_TITRE = "titre";
    private static final String FILM_AUTEUR = "Auteur";
    private static final String FILM_GENRE = "genre";
    private static final String FILM_EMPRUNT = "emprunt";
    private static final String FILM_NOM_EMPRUNT = "nom_emprunt";

    public static final String FILM_TABLE_NAME = "film";
    public static final String FILM_TABLE_CREATE =
            "CREATE TABLE " + FILM_TABLE_NAME + " ( " +
                    FILM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FILM_TITRE +" NVARCHAR(100) NOT NULL, " +
                    FILM_AUTEUR+" NVARCHAR(100) NOT NULL, "+
                    FILM_GENRE+" NVARCHAR(100) NOT NULL, "+
                    FILM_EMPRUNT+" INT NOT NULL, "+
                    FILM_NOM_EMPRUNT+" NVARCHAR(100) NOT NULL"+
                    "); ";
    public static final String FILM_TABLE_DROP = "DROP TABLE IF EXISTS " + FILM_TABLE_NAME + ";";
    public static final String FILM_IS_CREATED = "SELECT id FROM "+FILM_TABLE_NAME+";";

    public FilmHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println(FILM_TABLE_CREATE);
        db.execSQL(FILM_TABLE_CREATE);
    }

    public boolean isCreate(SQLiteDatabase db){
        if((db.query(FILM_TABLE_NAME,new String[]{FILM_ID},null,null,null,null,null,null))!=null)return true;
        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FILM_TABLE_DROP);
        onCreate(db);
    }

    public void reinitializeTable(SQLiteDatabase db){
        db.execSQL(FILM_TABLE_DROP);
        onCreate(db);
    }
}
