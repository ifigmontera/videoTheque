package com.example.ifigm.mavideotheque.view.Popup;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.ifigm.mavideotheque.R;
import com.example.ifigm.mavideotheque.control.FilmAdapter;
import com.example.ifigm.mavideotheque.control.FilmBDD;
import com.example.ifigm.mavideotheque.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifigm on 16/04/2016.
 */
public class PopupAuteur extends Dialog{
    private Dialog pop;
    private Context pContext;

    private EditText editTextAuteur;
    private Button buttonAuteur;
    private Film filme;
    private FilmBDD filmBDD;
    public PopupAuteur(Context context, final Film film, final FilmBDD filmBDD) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.popup_auteur);
        pContext = context;
        this.filme = film;
        this.filmBDD = filmBDD;
        pop = this;
        
        editTextAuteur = (EditText)findViewById(R.id.edit_text_auteur_popup);
        editTextAuteur.setText(filme.getAuteur().toString());
        buttonAuteur = (Button)findViewById(R.id.button_auteur);
        buttonAuteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filme.setAuteur(editTextAuteur.getText().toString());
                Log.e("test",filme.getAuteur() );
                filmBDD.open();
                filmBDD.updateFilm(filme);
                List<Film> films = new ArrayList<Film>();
                films = filmBDD.getFilm();
                filmBDD.close();
                FilmAdapter adapter = new FilmAdapter(films, pContext);
                adapter.notifyDataSetChanged();

                pop.cancel();
            }
        });


    }
}
