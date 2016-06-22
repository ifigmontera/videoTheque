package com.example.ifigm.mavideotheque.view.Popup;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ifigm.mavideotheque.MainActivity;
import com.example.ifigm.mavideotheque.R;
import com.example.ifigm.mavideotheque.control.FilmAdapter;
import com.example.ifigm.mavideotheque.control.FilmBDD;
import com.example.ifigm.mavideotheque.model.Film;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifigm on 16/04/2016.
 */
public class PopupEmprunt extends Dialog{
    private Dialog pop;
    private Context pContext;
    private TextView textViewEmprunt;
    private EditText editTextEmprunt;
    private Button buttonEmprunt;
    private Film filme;
    private FilmBDD filmBDD;
    public PopupEmprunt(Context context, final Film film, final FilmBDD filmBDD) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.popup_emprunt);
        pContext = context;
        this.filme = film;
        this.filmBDD = filmBDD;
        pop = this;
        textViewEmprunt = (TextView)findViewById(R.id.textViewFilmEmprunt);
        editTextEmprunt = (EditText)findViewById(R.id.editTextEmprunt);

        buttonEmprunt = (Button)findViewById(R.id.buttonAjouterEmprunt);
        buttonEmprunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filme.setEmprunt(true);
                filme.setNomEmprunteur(editTextEmprunt.getText().toString());
                filmBDD.open();
                filmBDD.updateFilm(filme);
                List<Film> films = new ArrayList<Film>();
                films = filmBDD.getFilm();
                filmBDD.close();
                FilmAdapter adapter = new FilmAdapter(films,pContext);
                adapter.notifyDataSetChanged();

                pop.cancel();
            }
        });


    }
}
