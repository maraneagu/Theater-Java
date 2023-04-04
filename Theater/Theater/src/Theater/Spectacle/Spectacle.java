package Theater.Spectacle;

import Theater.Director;

abstract public class Spectacle {
    protected int id;
    protected String name;
    protected Director director;
    protected String duration;

    public Spectacle() {
        this.director = new Director();
    }
    public Spectacle(String name, Director director, String duration) {
        this.name = name;
        this.director = director;
        this.duration = duration;
    }

    public abstract void toRead();
    @Override
    public abstract String toString();

    public String getName() {
        return name;
    }
    public Director getDirector() {
        return director;
    }
    public String getDuration() {
        return duration;
    }
}
