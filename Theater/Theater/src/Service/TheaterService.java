package Service;

import Theater.Artist.Actor;
import Theater.Artist.Dancer;
import Theater.Artist.Singer;
import Theater.Spectacle.*;
import Theater.*;

import java.util.*;

public class TheaterService {
    private static TheaterService singleInstance = null;
    private Map<Integer, Spectacle> spectacles;
    private int spectacleId;
    private Map<Integer, Category> categories;
    private int categoryId;
    private Map<Integer, Actor> actors;
    private int actorId;
    private Map<Integer, Singer> singers;
    private int singerId;
    private Map<Integer, Dancer> dancers;
    private int dancerId;
    private Map<Integer, Director> directors;
    private int directorId;
    private final Map<Integer, Stage> stages;
    private int stageId;
    private List<Event> events;

    private TheaterService() {
        this.spectacles = new HashMap<>();
        this.categories = new HashMap<>();
        addCategories();
        this.actors = new HashMap<>();
        this.singers = new HashMap<>();
        this.dancers = new HashMap<>();
        this.directors = new HashMap<>();
        this.stages = new HashMap<>();
        addStages();
        this.events = new ArrayList<>();
        addSetup();
    }

    public static synchronized TheaterService getInstance() {
        if (singleInstance == null)
            singleInstance = new TheaterService();
        return singleInstance;
    }

    public void addCategories() {
        Category category = new Category("Comedy");
        setCategoryId(getCategoryId() + 1);
        categories.put(categoryId, category);

        category = new Category("Tragedy");
        setCategoryId(getCategoryId() + 1);
        categories.put(categoryId, category);

        category = new Category("Historical");
        setCategoryId(getCategoryId() + 1);
        categories.put(categoryId, category);

        category = new Category("Theater of the Absurd");
        setCategoryId(getCategoryId() + 1);
        categories.put(categoryId, category);
    }

    public void addStages() {
        Stage stage = new Stage("The Little", 10, 5);
        setStageId(getStageId() + 1);
        stages.put(stageId, stage);

        stage = new Stage("The Big", 15, 10);
        setStageId(getStageId() + 1);
        stages.put(stageId, stage);

        stage = new Stage("The Studio", 5, 15);
        setStageId(getStageId() + 1);
        stages.put(stageId, stage);

        stage = new Stage("The Workshop", 10, 8);
        setStageId(getStageId() + 1);
        stages.put(stageId, stage);

        stage = new Stage("The Painting", 7, 5);
        setStageId(getStageId() + 1);
        stages.put(stageId, stage);
    }

    public void addSetup() {
        Actor actor = new Actor ("Ileana Olteanu");
        List<Actor> sActors = new ArrayList<>();
        sActors.add(actor);
        Director director = new Director("Erwin Șimșenshon");
        Spectacle spectacle = new Play("The Mousetrap", director, "02:15", categories.get(2), sActors);
        setSpectacleId(getSpectacleId() + 1);
        spectacles.put(spectacleId, spectacle);
        setDirectorId(getDirectorId() + 1);
        directors.put(directorId, director);
        setActorId(getActorId() + 1);
        actors.put(getActorId(), actor);

        actor = new Actor ("Oana Pellea");
        sActors = new ArrayList<>();
        sActors.add(actor);
        director = new Director("Mariana Cămărășan");
        spectacle = new Play("The Effect of Gama Rays on Man-in-the-Moon Marygolds", director, "02:20", categories.get(2), sActors);
        setSpectacleId(getSpectacleId() + 1);
        spectacles.put(spectacleId, spectacle);
        setDirectorId(getDirectorId() + 1);
        directors.put(directorId, director);
        setActorId(getActorId() + 1);
        actors.put(getActorId(), actor);

        actor = new Actor ("Alina Șerban");
        sActors = new ArrayList<>();
        sActors.add(actor);
        director = new Director("Alina Șerban");
        spectacle = new Play("The Best Kid in the World", director, "01:30", categories.get(2), sActors);
        setSpectacleId(getSpectacleId() + 1);
        spectacles.put(spectacleId, spectacle);
        setDirectorId(getDirectorId() + 1);
        directors.put(directorId, director);
        setActorId(getActorId() + 1);
        actors.put(getActorId(), actor);

        actor = new Actor ("Costel Constantin");
        sActors = new ArrayList<>();
        sActors.add(actor);
        director = new Director("Dan Tudor");
        spectacle = new Play("The Man who saw Death", director, "02:00", categories.get(1), sActors);
        setSpectacleId(getSpectacleId() + 1);
        spectacles.put(spectacleId, spectacle);
        setDirectorId(getDirectorId() + 1);
        directors.put(directorId, director);
        setActorId(getActorId() + 1);
        actors.put(getActorId(), actor);

        actor = new Actor ("Marius Manole");
        sActors = new ArrayList<>();
        sActors.add(actor);
        director = new Director("Radu Afrim");
        spectacle = new Play("Reherseal for a Better World", director, "02:50", categories.get(2), sActors);
        setSpectacleId(getSpectacleId() + 1);
        spectacles.put(spectacleId, spectacle);
        setDirectorId(getDirectorId() + 1);
        directors.put(directorId, director);
        setActorId(getActorId() + 1);
        actors.put(getActorId(), actor);

        actor = new Actor ("Oana Burcescu");
        sActors = new ArrayList<>();
        sActors.add(actor);
        director = new Director("Andrei Cotarcea");
        spectacle = new Play("A Marriage Proposal", director, "02:00", categories.get(1), sActors);
        setSpectacleId(getSpectacleId() + 1);
        spectacles.put(spectacleId, spectacle);
        setDirectorId(getDirectorId() + 1);
        directors.put(directorId, director);
        setActorId(getActorId() + 1);
        actors.put(getActorId(), actor);

        actor = new Actor ("Dana Coza");
        sActors = new ArrayList<>();
        sActors.add(actor);
        director = new Director("Edda Coza");
        spectacle = new Play("Offending the Audience", director, "01:15", categories.get(2), sActors);
        setSpectacleId(getSpectacleId() + 1);
        spectacles.put(spectacleId, spectacle);
        setDirectorId(getDirectorId() + 1);
        directors.put(directorId, director);
        setActorId(getActorId() + 1);
        actors.put(getActorId(), actor);

        actor = new Actor ("Horațiu Mălăele");
        sActors = new ArrayList<>();
        sActors.add(actor);
        director = new Director("Ion Caramitru");
        spectacle = new Play("The Dinner Game", director, "02:00", categories.get(1), sActors);
        setSpectacleId(getSpectacleId() + 1);
        spectacles.put(spectacleId, spectacle);
        setDirectorId(getDirectorId() + 1);
        directors.put(directorId, director);
        setActorId(getActorId() + 1);
        actors.put(getActorId(), actor);

        Event event = new Event(spectacles.get(1), stages.get(1), "09.04.2023", "18:00", "20:15", 25.00);
        events.add(event);
        event = new Event(spectacles.get(2), stages.get(5), "09.04.2023", "19:00", "21:20", 20.00);
        events.add(event);
        event = new Event(spectacles.get(3), stages.get(3), "09.04.2023", "20:00", "21:30", 30.00);
        events.add(event);
    }

    public Map<Integer, Spectacle> getSpectacles() {
        return spectacles;
    }
    public void setSpectacles(Map<Integer, Spectacle> spectacles) {
        this.spectacles = spectacles;
    }
    public int getSpectacleId() {
        return spectacleId;
    }
    public void setSpectacleId(int spectacleId) {
        this.spectacleId = spectacleId;
    }

    public Map<Integer, Category> getCategories() {
        return categories;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Map<Integer, Director> getDirectors() {
        return directors;
    }
    public int getDirectorId() {
        return directorId;
    }
    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    public Map<Integer, Actor> getActors() {
        return actors;
    }
    public int getActorId() {
        return actorId;
    }
    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public Map<Integer, Singer> getSingers() {
        return singers;
    }
    public int getSingerId() {
        return singerId;
    }
    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }

    public Map<Integer, Dancer> getDancers() {
        return dancers;
    }
    public int getDancerId() {
        return dancerId;
    }
    public void setDancerId(int dancerId) {
        this.dancerId = dancerId;
    }

    public Map<Integer, Stage> getStages() {
        return stages;
    }
    public int getStageId() {
        return stageId;
    }
    public void setStageId(int stageId) {
        this.stageId = stageId;
    }

    public List<Event> getEvents() {
        return events;
    }
}