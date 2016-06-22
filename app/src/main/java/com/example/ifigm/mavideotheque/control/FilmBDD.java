package com.example.ifigm.mavideotheque.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ifigm.mavideotheque.handler.FilmHandler;
import com.example.ifigm.mavideotheque.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifigm on 16/04/2016.
 */
public class FilmBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "film.db";
    private static final String TABLE_FILM = "film";
    private static final String FILM_ID = "Id";
    private static final String FILM_TITRE = "titre";
    private static final String FILM_AUTEUR = "Auteur";
    private static final String FILM_GENRE = "genre";
    private static final String FILM_EMPRUNT = "emprunt";
    private static final String FILM_NOM_EMPRUNT = "nom_emprunt";

    private static final int NUM_FILM_ID = 0;
    private static final int NUM_FILM_TITRE = 1;
    private static final int NUM_FILM_AUTEUR = 2;
    private static final int NUM_FILM_GENRE = 3;
    private static final int NUM_FILM_EMPRUNT = 4;
    private static final int NUM_FILM_NOM_EMPRUNT = 5;


    private SQLiteDatabase bdd;

    private FilmHandler maBaseSQLite;
    private List<Film> filmEmprunte;

    public FilmBDD(Context context) {
        //On créer la BDD et sa table
        maBaseSQLite = new FilmHandler(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open() {
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD() {
        return bdd;
    }

    public FilmHandler getMaBaseSQLite() {
        return maBaseSQLite;
    }

    public long insertFilm(Film film) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)

        values.put(FILM_TITRE, film.getTitre());
        values.put(FILM_AUTEUR, film.getAuteur());
        values.put(FILM_GENRE, film.getGenre());
        values.put(FILM_EMPRUNT, film.isEmprunt()?0:1);
        values.put(FILM_NOM_EMPRUNT, film.getNomEmprunteur());
        //on insère l'objet dans la BDD via le ContentValuere

        return bdd.insert(TABLE_FILM, null, values);
    }

    public long updateFilm(Film film){
        ContentValues value = new ContentValues();
        value.put(FILM_TITRE,film.getTitre());
        value.put(FILM_AUTEUR, film.getAuteur());
        value.put(FILM_GENRE, film.getGenre());
        value.put(FILM_EMPRUNT, film.isEmprunt()?0:1);
        value.put(FILM_NOM_EMPRUNT, film.getNomEmprunteur());
        String clause = FILM_ID+" = "+film.getId();
        return bdd.update(TABLE_FILM, value, clause, null);
    }

    public void deleteFilm(String s) {
        bdd.delete(TABLE_FILM,FILM_TITRE+" LIKE '%"+s+"%'",null);
    }

    public List<Film> getFilm() {
        List<Film> list = new ArrayList<Film>();
        //Récupère dans un Cursor les valeur correspondant à un mot contenu dans la BDD

        Cursor c = bdd.query(TABLE_FILM, new String[]{FILM_ID,FILM_TITRE,FILM_AUTEUR,FILM_GENRE,FILM_EMPRUNT,FILM_NOM_EMPRUNT}, FILM_ID + " IS NOT NULL", null, null,null,  FILM_TITRE+" ASC");

        if (c.moveToFirst()) {
            list.add(cursorToFilm(c));
        }

        while (c.moveToNext()) {
            list.add(cursorToFilm(c));
        }

        //On ferme le cursor
        c.close();
        return list;
    }

    //Cette méthode permet de convertir un cursor en un score

    private Film cursorToFilm(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0) {
            return null;
        } else {

            //On créé un score
            boolean a = false;
            if((c.getInt(NUM_FILM_EMPRUNT)) ==0) a = true;

            Film film = new Film(
                    c.getInt(NUM_FILM_ID),
                    c.getString(NUM_FILM_TITRE),
                    c.getString(NUM_FILM_AUTEUR),
                    c.getString(NUM_FILM_GENRE),
                    a,
                    c.getString(NUM_FILM_NOM_EMPRUNT));



            //On retourne le score
            return film;
        }
    }

    public List<Film> getFilmEmprunte() {
        List<Film> list = new ArrayList<Film>();
        //Récupère dans un Cursor les valeur correspondant à un mot contenu dans la BDD
        Cursor c = bdd.query(TABLE_FILM, new String[]{FILM_ID,FILM_TITRE,FILM_AUTEUR,FILM_GENRE,FILM_EMPRUNT,FILM_NOM_EMPRUNT}, FILM_ID + " IS NOT NULL AND "+FILM_EMPRUNT+"=0 ", null, null, null, FILM_TITRE+" ASC");

        if (c.moveToFirst()) {
            list.add(cursorToFilmEmprunte(c));
        }

        while (c.moveToNext()) {
            list.add(cursorToFilmEmprunte(c));
        }
        //On ferme le cursor
        c.close();
        return list;
    }

    private Film cursorToFilmEmprunte(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0) {
            return null;
        } else {

                        //On créé un score
            boolean a = false;
            if((c.getInt(NUM_FILM_EMPRUNT)) ==0) a = true;

            Film film = new Film(
                    c.getInt(NUM_FILM_ID),
                    c.getString(NUM_FILM_TITRE),
                    c.getString(NUM_FILM_AUTEUR),
                    c.getString(NUM_FILM_GENRE),
                    a,
                    c.getString(NUM_FILM_NOM_EMPRUNT));




            //On retourne le score
            return film;
        }
    }

    public int getInt() {
        return getFilm().size();
    }
}
