package Theater;

import Exception.TheaterException;
import java.util.*;
import java.util.regex.Pattern;

public class Profile {
    private static Profile singleInstance = null;
    private int id;
    private String name;
    private String userName;
    private String email;
    private List<Ticket> tickets;

    public Profile() {
        tickets = new ArrayList<>();
    }

    public static synchronized Profile getInstance() {
        if (singleInstance == null)
            singleInstance = new Profile();
        return singleInstance;
    }

    public void toRead() {
        Scanner in = new Scanner(System.in);

        System.out.println("\n\uF0B2 Sign up \uF0B2\n");

        System.out.print("Enter your username: ");
        this.userName = in.nextLine().trim();

        System.out.print("\nEnter the name of your profile: ");
        this.name = in.nextLine().trim();

        while (true)
        {
            try
            {
                System.out.println("\nThe email of the profile! The format: name@email.com!");
                System.out.print("Enter your email: ");
                this.email = in.nextLine().trim();

                if (email.length() < 10 || !email.substring(email.length() - 10, email.length()).equals("@email.com"))
                    throw new TheaterException("\uF0FB The email you introduced is not valid! Please try again! \uF0FB");
                break;
            }
            catch(TheaterException nameException)
            {
                System.out.println(nameException.getMessage());
            }
        }
    }
    @Override
    public String toString() {
        return "\uF0B2 Profile \uF0B2" + '\n' +
                "The username @: " + userName + '\n' +
                "The name of the profile: " + name + '\n' +
                "The email: " + email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
