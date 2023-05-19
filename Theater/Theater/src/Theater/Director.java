package Theater;

import java.util.Scanner;
import java.util.regex.*;

import Exception.InvalidNameException;

public class Director {
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

                if (!Pattern.matches("^[a-zA-z ]*$", name))
                    throw new InvalidNameException();
                break;
            }
            catch(InvalidNameException nameException)
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
