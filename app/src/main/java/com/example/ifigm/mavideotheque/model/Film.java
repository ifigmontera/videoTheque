package com.example.ifigm.mavideotheque.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ifigm on 16/04/2016.
 */
public class Film implements Parcelable {
    private int id;
    private boolean emprunt;
    private String titre;
    private String genre;
    private String auteur;
    private String nomEmprunteur;

    public Film(int id,String titre, String auteur, String genre,boolean emprunt,String nomEmprunteur ){
        this.id=id;
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
        this.emprunt =emprunt;
        this.nomEmprunteur = nomEmprunteur;
    }

    protected Film(Parcel in) {
        id = in.readInt();
        titre = in.readString();
        auteur= in.readString();
        genre = in.readString();
        emprunt = in.readString().equals("true");
        nomEmprunteur = in.readString();


    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    public int getId(){
        return id;
    }
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public boolean isEmprunt() {
        return emprunt;
    }

    public void setEmprunt(boolean emprunt) {
        this.emprunt = emprunt;
    }

    public String getNomEmprunteur() {
        return nomEmprunteur;
    }

    public void setNomEmprunteur(String nomEmprunteur) {
        this.nomEmprunteur = nomEmprunteur;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titre);
        dest.writeString(auteur);
        dest.writeString(genre);
        if(isEmprunt())dest.writeString("true"); else dest.writeString("false");
        dest.writeString(getNomEmprunteur());


    }
}
