package com.example.ifigm.mavideotheque.view.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ifigm.mavideotheque.R;
import com.example.ifigm.mavideotheque.control.FilmAdapter;
import com.example.ifigm.mavideotheque.control.FilmBDD;
import com.example.ifigm.mavideotheque.model.Film;

import com.example.ifigm.mavideotheque.view.Activity.MainActivity;
import com.example.ifigm.mavideotheque.view.Popup.PopupAdd;
import com.example.ifigm.mavideotheque.view.Popup.PopupAuteur;
import com.example.ifigm.mavideotheque.view.Popup.PopupEmprunt;
import com.example.ifigm.mavideotheque.view.Popup.PopupGenre;
import com.example.ifigm.mavideotheque.view.Popup.PopupTitre;

import java.util.List;

/**
 * Created by ifigm on 14/09/2016.
 */
public class FragmentToutFilm extends Fragment implements FilmAdapter.FilmAdapterListener {

    //variable
    private AppCompatActivity pContext;
    private FloatingActionButton add;
    private List<Film> films;
    private ListView listView;
    private FilmBDD filmBDD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment_tout_film, container, false);
        listView = (ListView) rootView.findViewById(R.id.liste_item);
        add = (FloatingActionButton) rootView.findViewById((R.id.add));
        pContext = (AppCompatActivity) getActivity();
        filmBDD = new FilmBDD(pContext);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupAdd popAdd = new PopupAdd(pContext, filmBDD);
                popAdd.show();

            }
        });
        filmBDD.open();
        if(!filmBDD.getMaBaseSQLite().isCreate(filmBDD.getBDD())) {
            filmBDD.getMaBaseSQLite().reinitializeTable(filmBDD.getBDD());
        }else {
            films = filmBDD.getFilm();
            FilmAdapter adapter = new FilmAdapter(films, pContext);
            adapter.addListener(this);
            listView.setAdapter(adapter);
        }
        filmBDD.close();
        return rootView;
    }


    @Override
    public void onClickFilm(Film item, int position) {
        PopupTitre pop = new PopupTitre(pContext, item, filmBDD);
        pop.show();
    }

    @Override
    public void onClickAuteur(Film item, int position) {
        PopupAuteur pop = new PopupAuteur(pContext, item, filmBDD);
        pop.show();
    }

    @Override
    public void onClickGenre(Film item, int position) {
        PopupGenre pop = new PopupGenre(pContext, item, filmBDD);
        pop.show();
    }

    @Override
    public void onClickDisponible(Film item, int position) {
        if(!item.isEmprunt()) {
            PopupEmprunt pop = new PopupEmprunt(pContext, item, filmBDD);
            pop.show();

        }else {
            item.setEmprunt(false);
            item.setNomEmprunteur("");
            filmBDD.open();
            filmBDD.updateFilm(item);
            filmBDD.close();

        }

        FilmAdapter adapter = new FilmAdapter(films,pContext);
        adapter.addListener(FragmentToutFilm.this);
        listView.setAdapter(adapter);
    }

    public void display(Film film){
        films.add(film);


        FilmAdapter adapter = new FilmAdapter(films, pContext);
        adapter.addListener(this);
        listView.setAdapter(adapter);


    }
}
