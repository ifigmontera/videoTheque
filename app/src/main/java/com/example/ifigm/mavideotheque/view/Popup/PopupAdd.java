package com.example.ifigm.mavideotheque.view.Popup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ifigm.mavideotheque.R;
import com.example.ifigm.mavideotheque.control.FilmAdapter;
import com.example.ifigm.mavideotheque.control.FilmBDD;
import com.example.ifigm.mavideotheque.model.Film;
import com.example.ifigm.mavideotheque.view.Activity.MainActivity;
import com.example.ifigm.mavideotheque.view.Fragment.FragmentToutFilm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifigm on 16/04/2016.
 */
public class PopupAdd extends Dialog{
    private Dialog pop;
    private Context pContext;
    private EditText editTextTitre;
    private EditText editTextAuteur;
    private EditText editTextGenre;
    private EditText editTextNomEmprunteur;
    private CheckBox disponible;
    private Button buttonAdd;
    private Film filme;
    private FilmBDD filmBDD;

    public PopupAdd(final Context context, final FilmBDD filmBDD) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.popup_add);
        pContext = context;


        this.filmBDD = filmBDD;
        pop = this;
        editTextTitre = (EditText)findViewById(R.id.edit_text_titre);
        editTextAuteur = (EditText)findViewById(R.id.edit_text_auteur);
        editTextGenre = (EditText)findViewById(R.id.edit_text_genre);
        disponible = (CheckBox)findViewById(R.id.edit_text_emprunt);
        editTextNomEmprunteur = (EditText)findViewById(R.id.edit_text_nom_emprunt);
        buttonAdd = (Button)findViewById(R.id.button_add_film);
        Log.e("test",this.filmBDD+"");
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filmBDD.open();
                filme = new Film(filmBDD.getInt(),editTextTitre.getText().toString()
                        ,editTextAuteur.getText().toString()
                        ,editTextGenre.getText().toString()
                        , disponible.isChecked()
                        ,disponible.isChecked() ? editTextNomEmprunteur.getText().toString() : ""
                );
                filmBDD.insertFilm(filme);
                List<Film> films =filmBDD.getFilm();
                filmBDD.close();
                FilmAdapter adapter = new FilmAdapter(films, pContext);
                adapter.notifyDataSetChanged();
                pop.cancel();

            }
        });


    }
}
