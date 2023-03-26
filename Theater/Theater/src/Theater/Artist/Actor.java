package Theater.Artist;

import java.util.Scanner;

public class Actor extends Artist {
    public Actor() {}
    public Actor(String name) {
        this.name = name;
    }

    @Override
    public void toRead() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the name of the actor: ");
        this.name = in.nextLine();

        System.out.println();
    }
    @Override
    public String toString(){
        return "\uF09F " + name;
    }
}
