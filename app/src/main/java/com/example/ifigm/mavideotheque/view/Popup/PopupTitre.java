package com.example.ifigm.mavideotheque.view.Popup;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ifigm.mavideotheque.R;
import com.example.ifigm.mavideotheque.control.FilmAdapter;
import com.example.ifigm.mavideotheque.control.FilmBDD;
import com.example.ifigm.mavideotheque.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifigm on 16/04/2016.
 */
public class PopupTitre extends Dialog{
    private Dialog pop;
    private Context pContext;

    private EditText editTextTitre;
    private Button buttonTitre;
    private Film filme;
    private FilmBDD filmBD;
    public PopupTitre(Context context, final Film film, final FilmBDD filmBDD) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.popup_titre);
        pContext = context;
        this.filme = film;
        this.filmBD = filmBDD;
        pop = this;
        
        editTextTitre = (EditText)findViewById(R.id.edit_text_titre_popup);
        editTextTitre.setText(film.getTitre().toString());
        buttonTitre = (Button)findViewById(R.id.button_titre);
        buttonTitre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filme.setTitre(editTextTitre.getText().toString());
                Log.e("test", filme.getTitre());
                filmBD.open();
                filmBD.updateFilm(filme);
                List<Film> films = new ArrayList<Film>();
                films = filmBD.getFilm();
                filmBD.close();
                FilmAdapter adapter = new FilmAdapter(films,pContext);
                adapter.notifyDataSetChanged();

                pop.cancel();
            }
        });


    }
}
