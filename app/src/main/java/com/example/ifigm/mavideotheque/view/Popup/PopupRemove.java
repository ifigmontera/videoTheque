package com.example.ifigm.mavideotheque.view.Popup;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
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
public class PopupRemove extends Dialog{
    private Dialog pop;
    private Context pContext;
    private EditText editTextTitre;
    private Button buttonRemove;


    public PopupRemove(Context context, final FilmBDD filmBDD ) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.popup_remove);
        pContext = context;


        pop = this;
        editTextTitre = (EditText)findViewById(R.id.edit_text_titre_remove);

        buttonRemove = (Button)findViewById(R.id.button_remove_film);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filmBDD.open();
                List<Film> films = filmBDD.getFilm();
                if(editTextTitre.getText().length()==0){
                    editTextTitre.setError("Il manque un titre");
                }else {
                    filmBDD.deleteFilm(editTextTitre.getText().toString());
                    for (Film f : films) {
                        if (editTextTitre.getText().equals(f.getTitre()))
                            films.remove(f);
                    }
                    filmBDD.close();
                    FilmAdapter adapter = new FilmAdapter(films, pContext);
                    adapter.notifyDataSetChanged();

                    pop.cancel();
                }
            }
        });


    }
}
