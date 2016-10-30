package com.example.ifigm.mavideotheque.view.Fragment;


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

import com.example.ifigm.mavideotheque.R;
import com.example.ifigm.mavideotheque.control.FilmAdapter;
import com.example.ifigm.mavideotheque.control.FilmBDD;
import com.example.ifigm.mavideotheque.model.Film;

import com.example.ifigm.mavideotheque.view.Popup.PopupAuteur;
import com.example.ifigm.mavideotheque.view.Popup.PopupEmprunt;
import com.example.ifigm.mavideotheque.view.Popup.PopupGenre;
import com.example.ifigm.mavideotheque.view.Popup.PopupTitre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifigm on 14/09/2016.
 */
public class FragmentFilmEmprunt extends Fragment implements FilmAdapter.FilmAdapterListener, SwipeRefreshLayout.OnRefreshListener {

    //variable
    private AppCompatActivity pContext;
    private List<Film> films;
    private ListView listView;
    private FilmBDD filmBDD;
    private FilmAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment_fil_emprunt, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_emprunt);
        listView = (ListView) rootView.findViewById(R.id.liste_item_emprunt);
        pContext = (AppCompatActivity) getActivity();


        Log.e("test", listView+"");

        filmBDD = new FilmBDD(pContext);
        filmBDD.open();
        if(!filmBDD.getMaBaseSQLite().isCreate(filmBDD.getBDD())) {
            filmBDD.getMaBaseSQLite().reinitializeTable(filmBDD.getBDD());
            Log.e("test2", filmBDD.toString()+ "2");
        }else {
            films = filmBDD.getFilmEmprunte();
            Log.e("test2",films.size()+"");
            for(Film film : films){
                Log.e("test2",film.getAuteur()+"2");
            }
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

        adapter = new FilmAdapter(films,pContext);
        adapter.addListener(FragmentFilmEmprunt.this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        List<Film> tFilms = new ArrayList<Film>();
        filmBDD.open();
        tFilms = filmBDD.getFilmEmprunte();
        Log.e("test","test5");


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
