package com.example.ifigm.mavideotheque.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.ifigm.mavideotheque.R;
import com.example.ifigm.mavideotheque.control.FilmAdapter;
import com.example.ifigm.mavideotheque.control.FilmBDD;
import com.example.ifigm.mavideotheque.model.Film;
import com.example.ifigm.mavideotheque.view.Activity.MainActivity;
import com.example.ifigm.mavideotheque.view.Popup.PopupAuteur;
import com.example.ifigm.mavideotheque.view.Popup.PopupEmprunt;
import com.example.ifigm.mavideotheque.view.Popup.PopupGenre;
import com.example.ifigm.mavideotheque.view.Popup.PopupTitre;

import java.util.List;

/**
 * Created by ifigm on 14/09/2016.
 */
public class FragmentFilm extends Fragment implements FilmAdapter.FilmAdapterListener {

    //variable
    private AppCompatActivity pContext;
    private List<Film> films;
    private ListView listView;
    private FilmBDD filmBDD;
    @Override
    public void onActivityCreated (Bundle b){
        super.onActivityCreated(b);

        pContext = (AppCompatActivity) getActivity();

        listView = (ListView) pContext.findViewById(R.id.listItem);
        Log.e("test", pContext+"");
        filmBDD = new FilmBDD(pContext);
        filmBDD.open();
        if(!filmBDD.getMaBaseSQLite().isCreate(filmBDD.getBDD())) {
            filmBDD.getMaBaseSQLite().reinitializeTable(filmBDD.getBDD());
            Log.e("test2", filmBDD.toString()+ "2");
        }else {
            films = filmBDD.getFilm();
            Log.e("test2",films.size()+"");
            for(Film film : films){
                Log.e("test2",film.getAuteur()+"2");
            }
            FilmAdapter adapter = new FilmAdapter(films, pContext);
            adapter.addListener(this);

            listView.setAdapter(adapter);
        }
        filmBDD.close();

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
        adapter.addListener(FragmentFilm.this);
        listView.setAdapter(adapter);
    }
}
