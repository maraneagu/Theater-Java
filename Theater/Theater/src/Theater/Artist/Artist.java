package Theater.Artist;

import Theater.Spectacle.Spectacle;

import java.util.ArrayList;
import java.util.List;

abstract public class Artist {
    protected String name;
    protected List<Spectacle> spectacles;

    public Artist() {
        spectacles = new ArrayList<>();
    }
    public Artist(String name) {
        this.name = name;
    }

    public abstract void toRead();
    @Override
    public abstract String toString();

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Spectacle> getSpectacles() {
        return spectacles;
    }
    public void setSpectacles(List<Spectacle> spectacles) {
        this.spectacles = spectacles;
    }
}
