package com.example.ifigm.mavideotheque.control;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ifigm.mavideotheque.R;
import com.example.ifigm.mavideotheque.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifigm on 16/04/2016.
 */
public class FilmAdapter extends BaseAdapter {
    private List<Film> filmList;
    private Context pContext;
    private LayoutInflater mInflater;

    public FilmAdapter(List<Film> filmList, Context context) {
        this.pContext = context;
        this.filmList = filmList;
        this.mInflater = LayoutInflater.from(pContext);

    }

    @Override
    public int getCount() {
        return filmList.size();
    }

    @Override
    public Object getItem(int position) {
        return filmList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout;
        if (convertView == null) linearLayout = (LinearLayout) mInflater.inflate(R.layout.film_layout,parent,false);
        else linearLayout = (LinearLayout) convertView;

        TextView filmTitre = (TextView)linearLayout.findViewById(R.id.textViewFilm);
        TextView filmGenre = (TextView)linearLayout.findViewById(R.id.textViewFilmGenre);
        TextView filmAuteur = (TextView)linearLayout.findViewById(R.id.textViewFilmAuteur);
        TextView filmEmprunt = (TextView)linearLayout.findViewById(R.id.textViewFilmEmprunt);
        TextView filmNomEmprunt = (TextView)linearLayout.findViewById(R.id.textViewFilmNomEmprunt);

        Log.e("test",filmList.get(position).getTitre()+" "+ filmList.get(position).getAuteur()+" "+filmList.get(position).getGenre()+" "+position);

        filmTitre.setText(filmList.get(position).getTitre());
        filmAuteur.setText(filmList.get(position).getAuteur());
        filmGenre.setText(filmList.get(position).getGenre());
        filmEmprunt.setText(filmList.get(position).isEmprunt()?"est emprunter":"disponible");
        filmNomEmprunt.setText(filmList.get(position).getNomEmprunteur());

//------------ Début de l'ajout -------
//On mémorise la position de la "Personne" dans le composant textview
        filmTitre.setTag(position);
        filmAuteur.setTag(position);
        filmGenre.setTag(position);
        filmEmprunt.setTag(position);

//On ajoute un listener
        filmAuteur.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                //Lorsque l'on clique sur le nom, on récupère la position de la "Personne"
                Integer position = (Integer)v.getTag();

                //On prévient les listeners qu'il y a eu un clic sur le TextView "TV_Nom
                sendListenerAuteur(filmList.get(position), position);

                return true;
            }

        });
        filmEmprunt.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                //Lorsque l'on clique sur le nom, on récupère la position de la "Personne"
                Integer position = (Integer)v.getTag();

                //On prévient les listeners qu'il y a eu un clic sur le TextView "TV_Nom".
                sendListenerEmprunt(filmList.get(position), position);


                return true;
            }

        });
        filmTitre.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                //Lorsque l'on clique sur le nom, on récupère la position de la "Personne"
                Integer position = (Integer)v.getTag();
                //On prévient les listeners qu'il y a eu un clic sur le TextView "TV_Nom".
                sendListenerFilm(filmList.get(position), position);


                return true;
            }

        });
        filmGenre.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                //Lorsque l'on clique sur le nom, on récupère la position de la "Personne"
                Integer position = (Integer)v.getTag();

                //On prévient les listeners qu'il y a eu un clic sur le TextView "TV_Nom".
                sendListenergenre(filmList.get(position), position);


                return true;
            }

        });




        return linearLayout;
    }

    public interface FilmAdapterListener {
        public void onClickFilm(Film item, int position);
        public void onClickAuteur(Film item, int position);
        public void onClickGenre(Film item, int position);
        public void onClickDisponible(Film item, int position);

    }
    private ArrayList<FilmAdapterListener> mListListener = new ArrayList<FilmAdapterListener>();
    /**
     * Pour ajouter un listener sur notre adapter
     */
    public void addListener(FilmAdapterListener aListener) {
        mListListener.add(aListener);
    }

    private void sendListenerEmprunt(Film item, int position) {
        for(int i = mListListener.size()-1; i >= 0; i--) {
            mListListener.get(i).onClickDisponible(item, position);
        }
    }

    private void sendListenerFilm(Film item, int position) {
        for(int i = mListListener.size()-1; i >= 0; i--) {
            mListListener.get(i).onClickFilm(item, position);
        }
    }

    private void sendListenerAuteur(Film item, int position) {
        for(int i = mListListener.size()-1; i >= 0; i--) {
            mListListener.get(i).onClickAuteur(item, position);
        }
    }

    private void sendListenergenre(Film item, int position) {
        for(int i = mListListener.size()-1; i >= 0; i--) {
            mListListener.get(i).onClickGenre(item, position);
        }
    }



}
