package com.example.ifigm.mavideotheque;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.ifigm.mavideotheque.control.FilmAdapter;
import com.example.ifigm.mavideotheque.control.FilmBDD;
import com.example.ifigm.mavideotheque.model.Film;
import com.example.ifigm.mavideotheque.view.Popup.PopupAdd;
import com.example.ifigm.mavideotheque.view.Popup.PopupAuteur;
import com.example.ifigm.mavideotheque.view.Popup.PopupEmprunt;
import com.example.ifigm.mavideotheque.view.Popup.PopupGenre;
import com.example.ifigm.mavideotheque.view.Popup.PopupRemove;
import com.example.ifigm.mavideotheque.view.Popup.PopupTitre;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FilmAdapter.FilmAdapterListener {

    private Button buttonFilm;
    private Button buttonEmprunt;
    private Button buttonCriteria;
    private List<Film> films;
    private Context context;
    private ListView listView;
    private FilmBDD filmBDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        buttonFilm = (Button) findViewById(R.id.buttonFilm);
        buttonCriteria = (Button) findViewById(R.id.buttonCriteria);
        buttonEmprunt = (Button) findViewById(R.id.buttonEmprunt);
        Log.e("test2","2");
        listView = (ListView)findViewById(R.id.listItem);

        filmBDD = new FilmBDD(this);
        filmBDD.open();
        if(!filmBDD.getMaBaseSQLite().isCreate(filmBDD.getBDD())) {
            filmBDD.getMaBaseSQLite().reinitializeTable(filmBDD.getBDD());
            Log.e("test2", filmBDD.toString()+ "2");
        }else {
            films = filmBDD.getFilm();
            Log.e("test2",films.size()+"");
            for(Film film : films){
                Log.e("test2",film.toString()+"2");
            }
            FilmAdapter adapter = new FilmAdapter(films, context);
            adapter.addListener(MainActivity.this);
            listView.setAdapter(adapter);
        }
        filmBDD.close();
        buttonFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                doit affiché la liste des tous les films
                 */
                FilmBDD filmBDD = new FilmBDD(context);
                filmBDD.open();
                films = filmBDD.getFilm();
                filmBDD.close();

                FilmAdapter adapter = new FilmAdapter(films, context);
                adapter.addListener(MainActivity.this);
                listView.setAdapter(adapter);
            }
        });

        buttonEmprunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilmBDD filmBDD = new FilmBDD(context);
                filmBDD.open();
                films = filmBDD.getFilmEmprunte();
                Log.e("test2",films.size()+"");
                filmBDD.close();
                FilmAdapter adapter = new FilmAdapter(films, context);
                adapter.addListener(MainActivity.this);
                listView.setAdapter(adapter);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                PopupAdd popAdd = new PopupAdd(context, filmBDD);
                popAdd.show();
                return true;
            case R.id.menu_remove:
                PopupRemove popRemove = new PopupRemove(context, filmBDD);
                popRemove.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickFilm(Film item, int position) {
        PopupTitre pop = new PopupTitre(context, item, filmBDD);
        pop.show();
    }

    @Override
    public void onClickAuteur(Film item, int position) {
        PopupAuteur pop = new PopupAuteur(context, item, filmBDD);
        pop.show();
    }

    @Override
    public void onClickGenre(Film item, int position) {
        PopupGenre pop = new PopupGenre(context, item, filmBDD);
        pop.show();
    }

    @Override
    public void onClickDisponible(Film item, int position) {
        if(!item.isEmprunt()) {
            PopupEmprunt pop = new PopupEmprunt(context, item, filmBDD);
            pop.show();

        }else {
            item.setEmprunt(false);
            item.setNomEmprunteur("");
            filmBDD.open();
            filmBDD.updateFilm(item);
            filmBDD.close();

        }

        FilmAdapter adapter = new FilmAdapter(films,context);
        adapter.addListener(MainActivity.this);
        listView.setAdapter(adapter);
    }


}
