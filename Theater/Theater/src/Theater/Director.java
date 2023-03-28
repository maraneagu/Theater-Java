package Theater;

import java.util.Scanner;

public class Director {
    private int id;
    private String name;

    public Director() {}
    public Director(String name) {
        this.name = name;
    }

    public void toRead() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the name of the director: ");
        this.name = in.nextLine().trim();
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
