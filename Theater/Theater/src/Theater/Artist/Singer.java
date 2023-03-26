package Theater.Artist;

import java.util.Scanner;

public class Singer extends Artist {
    public Singer() {}
    public Singer(String name) {
        this.name = name;
    }

    @Override
    public void toRead() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the name of the singer: ");
        this.name = in.nextLine();

        System.out.println();
    }
    @Override
    public String toString() {
        return "\uF09F " + name;
    }
}
