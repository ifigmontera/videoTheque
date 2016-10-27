package com.example.ifigm.mavideotheque.view.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifigm on 14/09/2016.
 */
public class FragmentToutFilm extends Fragment implements FilmAdapter.FilmAdapterListener, SwipeRefreshLayout.OnRefreshListener {

    //variable
    private AppCompatActivity pContext;

    private List<Film> films;
    private ListView listView;
    private FilmBDD filmBDD;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FilmAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment_tout_film, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        listView = (ListView) rootView.findViewById(R.id.liste_item);

        pContext = (AppCompatActivity) getActivity();
        filmBDD = new FilmBDD(pContext);

        filmBDD.open();
        if (!filmBDD.getMaBaseSQLite().isCreate(filmBDD.getBDD())) {
            filmBDD.getMaBaseSQLite().reinitializeTable(filmBDD.getBDD());
        } else {
            films = filmBDD.getFilm();
            adapter = new FilmAdapter(films, pContext);
            adapter.addListener(this);
            listView.setAdapter(adapter);
        }
        mSwipeRefreshLayout.setOnRefreshListener(this);
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
        if (!item.isEmprunt()) {
            PopupEmprunt pop = new PopupEmprunt(pContext, item, filmBDD);
            pop.show();

        } else {
            item.setEmprunt(false);
            item.setNomEmprunteur("");
            filmBDD.open();
            filmBDD.updateFilm(item);
            filmBDD.close();

        }

        FilmAdapter adapter = new FilmAdapter(films, pContext);
        adapter.addListener(FragmentToutFilm.this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        List<Film> tFilms = new ArrayList<Film>();
        filmBDD.open();
        tFilms = filmBDD.getFilm();



        films.clear();

        for (Film f : tFilms) {
            films.add(f);
            Log.e("test", films.size()+"");

        }

        adapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
        listView.setAdapter(adapter);
    }
}
