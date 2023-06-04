package Theater;

public class Category {
    private String name;

    public Category() {}
    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "\uF09F " + name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
