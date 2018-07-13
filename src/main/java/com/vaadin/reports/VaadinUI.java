package com.vaadin.reports;

import com.vaadin.data.Binder;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringUI
public class VaadinUI extends UI {

    @Autowired
    private FilmService filmService;

    private Film film;
    private Binder<Film> binder = new Binder<Film>(Film.class);
    private Grid<Film> grid = new Grid(Film.class);
    private TextField title = new TextField("Title");
    private TextField description = new TextField("Description");
//    private TextField release_year = new TextField("Release Year");
    private TextField language = new TextField("Language");
//    private TextField length = new TextField("Length");
    private TextField rating = new TextField("Rating");
//    private Button save = new Button("Save", e -> saveCustomer());
//    private Button add = new Button("Add new film", e -> addNewFilm());
    private Button print = new Button("Print", e -> {
        try {
            filmService.printTable();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    });


    @Override
    protected void init(VaadinRequest request) {

        updateGrid();

        grid.setColumns("title", "description", "language", "length", "rating");
        grid.addSelectionListener(e -> updateForm());
        grid.setSizeFull();

        binder.bindInstanceFields(this);

        VerticalLayout layout = new VerticalLayout(grid, title, description, language, rating, print);
        setContent(layout);
    }

    private void updateGrid() {
        ArrayList<Film> films = filmService.getAllFilms();
        grid.setItems(films);
        setFormVisible(false);
    }

    private void updateForm() {
        if (grid.asSingleSelect().isEmpty()) {
            setFormVisible(false);
        } else {
            film = grid.asSingleSelect().getValue();
            binder.setBean(film);
            setFormVisible(true);
        }
    }

    private void setFormVisible(boolean visible) {
        title.setVisible(visible);
        description.setVisible(visible);
//        release_year.setVisible(visible);
        language.setVisible(visible);
//        length.setVisible(visible);
        rating.setVisible(visible);
//        save.setVisible(visible);
    }

//    private void addNewFilm() {
//        grid.asSingleSelect().isEmpty();
//        setFormVisible(true);
//        film = grid.asSingleSelect().getValue();
//        binder.setBean(film);
//    }

//    private void saveCustomer() {
//        service.update(film);
//        updateGrid();
//    }
}