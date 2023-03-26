package Service;

import Theater.Artist.Actor;
import Theater.Artist.Dancer;
import Theater.Artist.Singer;
import Theater.Spectacle.*;
import Theater.*;

import java.util.*;

public class TheaterService {
    private static TheaterService singleInstance = null;
    private List<Spectacle> spectacles;
    private List<Category> categories;
    private List<Actor> actors;
    private List<Singer> singers;
    private List<Dancer> dancers;
    private List<Director> directors;
    private final List<Stage> stages;
    private List<Event> events;

    private TheaterService() {
        this.spectacles = new ArrayList<>();
        this.categories = new ArrayList<>();
        addCategories();
        this.actors = new ArrayList<>();
        this.singers = new ArrayList<>();
        this.dancers = new ArrayList<>();
        this.directors = new ArrayList<>();
        this.stages = new ArrayList<>();
        addStages();
        this.events = new ArrayList<>();
    }

    public static synchronized TheaterService getInstance() {
        if (singleInstance == null)
            singleInstance = new TheaterService();
        return singleInstance;
    }

    public void addCategories() {
        Category category = new Category("Comedy");
        categories.add(category);
        category = new Category("Tragedy");
        categories.add(category);
        category = new Category("Historical");
        categories.add(category);
        category = new Category("Theater of the Absurd");
        categories.add(category);
    }

    public void addStages() {
        Stage stage = new Stage("The Little", 10, 5);
        stages.add(stage);
        stage = new Stage("The Big", 15, 10);
        stages.add(stage);
        stage = new Stage("The Studio", 5, 15);
        stages.add(stage);
        stage = new Stage("The Workshop", 10, 8);
        stages.add(stage);
        stage = new Stage("The Painting", 7, 5);
        stages.add(stage);
    }

    public List<Spectacle> getSpectacles() {
        return spectacles;
    }
    public List<Category> getCategories() {
        return categories;
    }
    public List<Director> getDirectors() {
        return directors;
    }
    public List<Actor> getActors() {
        return actors;
    }
    public List<Singer> getSingers() {
        return singers;
    }
    public List<Dancer> getDancers() {
        return dancers;
    }
    public List<Stage> getStages() {
        return stages;
    }
    public List<Event> getEvents() {
        return events;
    }
}