package Theater.Artist;

import java.util.Scanner;
import java.util.regex.Pattern;
import Exception.InvalidNameException;

public class Singer extends Artist {
    public Singer() {}
    public Singer(String name) {
        this.name = name;
    }

    @Override
    public void toRead() {
        Scanner in = new Scanner(System.in);

        while (true)
        {
            try
            {
                System.out.print("\nEnter the name of the singer: ");
                this.name = in.nextLine().trim();

                if (!Pattern.matches("^[a-zA-z ]*$", name))
                    throw new InvalidNameException();
                break;
            }
            catch(InvalidNameException nameException)
            {
                System.out.println(nameException.getMessage());
            }
        }

        System.out.println();
    }
    @Override
    public String toString() {
        return "\uF09F " + name;
    }
}
