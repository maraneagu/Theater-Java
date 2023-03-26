package Theater.Artist;

import java.util.Scanner;

public class Dancer extends Artist {
    public Dancer() {}
    public Dancer(String name) {
        this.name = name;
    }

    @Override
    public void toRead() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the name of the dancer: ");
        this.name = in.nextLine();

        System.out.println();
    }
    @Override
    public String toString() {
        return "\uF09F " + name;
    }
}
