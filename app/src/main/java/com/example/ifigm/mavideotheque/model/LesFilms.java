package com.example.ifigm.mavideotheque.model;

import java.util.List;

/**
 * Created by ifigm on 30/10/2016.
 */

public class LesFilms {
    private List<Film> lesFilms;


    public LesFilms(List<Film> lesFilms) {
        this.lesFilms = lesFilms;
    }

    public List<Film> getLesFilms() {
        return lesFilms;
    }

    public void setLesFilms(List<Film> lesFilms) {
        this.lesFilms = lesFilms;
    }
    public void add(Film film){
        lesFilms.add(film);
    }
    public void remove(Film film){
        lesFilms.remove(film);
    }
    public Film getFilm(int position){
        return lesFilms.get(position);
    }

}
