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
public class PopupGenre extends Dialog{
    private Dialog pop;
    private Context pContext;

    private EditText editTextGenre;
    private Button buttonGenre;
    private Film filme;
    private FilmBDD filmBDD;
    public PopupGenre(Context context, final Film film, final FilmBDD filmBDD) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.popup_genre);
        pContext = context;
        this.filme = film;
        this.filmBDD = filmBDD;
        pop = this;
        
        editTextGenre = (EditText)findViewById(R.id.edit_text_genre_popup);
        editTextGenre.setText(film.getGenre().toString());
        buttonGenre = (Button)findViewById(R.id.button_genre);
        buttonGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filme.setGenre(editTextGenre.getText().toString());
                Log.e("test", filme.getGenre());
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
