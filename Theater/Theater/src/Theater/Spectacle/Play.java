package Theater.Spectacle;

import Service.PrintService;
import Theater.Artist.Actor;
import Theater.Category;
import Theater.Director;
import Exception.TheaterException;

import java.text.*;
import java.util.*;

public class Play extends Spectacle {
    private Category category;
    private List<Actor> actors;

    public Play() {
        super();
        this.category = new Category();
        this.actors = new ArrayList<>();
    }
    public Play(String name, Director director,
                Category category, List<Actor> actors) {
        super(name, director);
        this.category = category;
        this.actors = actors;
    }

    @Override
    public void toRead() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the name of the play: ");
        this.name = in.nextLine().trim();

        this.director.toRead();

        DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        while (true)
        {
            System.out.println("\nThe duration of the play! It should be between 1 hour and 4 hours with the format: hh:mm!");
            System.out.print("Enter the duration of the play: ");
            this.duration = in.nextLine().trim();

            try
            {
                timeFormat.parse(duration);

                String[] splitTime = duration.split(":");
                int hour = Integer.parseInt(splitTime[0]);
                int minutes = Integer.parseInt(splitTime[1]);

                if (hour > 4 || minutes > 59)
                    throw new TheaterException("\uF0FB The duration you introduced is not valid! Please try again! \uF0FB");
                break;
            }
            catch(ParseException parseException)
            {
                System.out.println("\uF0FB The duration format you introduced is not valid! Please try again! \uF0FB");
            }
            catch(TheaterException durationException)
            {
                System.out.println(durationException.getMessage());
            }
        }

        System.out.println();
    }
    @Override
    public String toString(){
        String outputToString;
        outputToString = "\uF0B2 Play \uF0B2" + '\n' +
                "The name of the play: " + '"' + name + '"' + '\n' +
                "The category of the play: " + category.getName() + '\n' +
                "The director of the play: " + director.getName() + '\n' +
                "The duration of the play: " + duration + " hours\n" +
                "The play actors: ";

        if (actors.size() == 0)
            outputToString += "there are no actors as of yet.";
        else {
            PrintService printService = new PrintService();
            outputToString += printService.printActorsList(actors);
        }
        return outputToString + '\n';
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public List<Actor> getActors() {
        return actors;
    }
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
