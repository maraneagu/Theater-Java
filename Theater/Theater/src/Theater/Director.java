package Theater;

import java.util.Scanner;
import java.util.regex.*;

import Exception.TheaterException;

public class Director {
    private int id;
    private String name;

    public Director() {}
    public Director(String name) {
        this.name = name;
    }

    public void toRead() {
        Scanner in = new Scanner(System.in);

        while (true)
        {
            try
            {
                System.out.print("\nEnter the name of the director: ");
                this.name = in.nextLine().trim();

                if (!Pattern.matches("^[a-zA-z]*$", name))
                    throw new TheaterException("\uF0FB The name you introduced is not valid! Please try again! \uF0FB");
                break;
            }
            catch(TheaterException nameException)
            {
                System.out.println(nameException.getMessage());
            }
        }
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
