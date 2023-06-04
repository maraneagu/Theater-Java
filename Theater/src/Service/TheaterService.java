package Service;

import Repository.ActorRepository;
import Repository.DancerRepository;
import Repository.SingerRepository;
import Theater.Artist.*;
import Theater.Spectacle.*;
import Theater.*;
import Repository.*;

import java.util.*;

public class TheaterService {
    private static TheaterService singleInstance = null;
    private List<Spectacle> spectacles;
    private Map<Integer, Category> categories;
    private Map<Integer, Actor> actors;
    private Map<Integer, Singer> singers;
    private Map<Integer, Dancer> dancers;
    private Map<Integer, Director> directors;
    private Map<Integer, Stage> stages;
    private List<Event> events;

    private TheaterService()
    {
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();
        spectacles = spectacleRepository.getSpectacles();

        ActorRepository actorRepository = ActorRepository.getInstance();
        actors = actorRepository.getActors();
        actorRepository.getPlayActors(spectacles, actors);

        SingerRepository singerRepository = SingerRepository.getInstance();
        singers = singerRepository.getSingers();
        singerRepository.getOperaSingers(spectacles, singers);
        singerRepository.getMusicalSingers(spectacles, singers);

        DancerRepository dancerRepository = DancerRepository.getInstance();
        dancers = dancerRepository.getDancers();
        dancerRepository.getMusicalDancers(spectacles, dancers);
        dancerRepository.getBalletDancers(spectacles, dancers);

        CategoryRepository categoryRepository = CategoryRepository.getInstance();
        categories = categoryRepository.getCategories();

        DirectorRepository directorRepository = DirectorRepository.getInstance();
        directors = directorRepository.getDirectors();

        StageRepository stageRepository = StageRepository.getInstance();
        stages = stageRepository.getStages();

        EventRepository eventRepository = EventRepository.getInstance();
        events = eventRepository.getEvents();
    }

    public static synchronized TheaterService getInstance()
    {
        if (singleInstance == null)
            singleInstance = new TheaterService();
        return singleInstance;
    }

    public List<Spectacle> getSpectacles() {
        return spectacles;
    }
    public void setSpectacles(List<Spectacle> spectacles) {
        this.spectacles = spectacles;
    }

    public Map<Integer, Category> getCategories() {
        return categories;
    }
    public Map<Integer, Director> getDirectors() {
        return directors;
    }
    public Map<Integer, Actor> getActors() {
        return actors;
    }
    public Map<Integer, Singer> getSingers() {
        return singers;
    }
    public Map<Integer, Dancer> getDancers() {
        return dancers;
    }
    public Map<Integer, Stage> getStages() {
        return stages;
    }
    public List<Event> getEvents() {
        return events;
    }
}