package Theater.Artist;

abstract public class Artist {
    protected int id;
    protected String name;

    public Artist() {}
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
}
