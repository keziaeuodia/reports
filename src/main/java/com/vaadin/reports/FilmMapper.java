package com.vaadin.reports;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface FilmMapper {

    String GET_FILMS = "SELECT f.film_id, f.title, f.description, f.release_year, l.name as `language`, f.length, " +
            "f.rating FROM film f JOIN language l  ON l.language_id = f.language_id;";
    String ADD_NEW_FILMS = "INSERT INTO f.title, f.description, l.name as `language`, " +
            "f.rating FROM film f JOIN language l ON l.language_id = f.language_id " +
            "VALUES #{title}, #{description}, #{language}, #{rating};";

    @Select(GET_FILMS)
    public ArrayList<Film> getAllFilms();

    @Insert(ADD_NEW_FILMS)
    public void addNewFilm();

}
