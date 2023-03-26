package Theater.Spectacle;

import Service.PrintService;
import Theater.Artist.Actor;
import Theater.Category;
import Theater.Director;

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
        this.name = in.nextLine();

        this.director.toRead();

        while (true)
        {
            System.out.println("The duration of the play! The format: 0h:mm!");
            System.out.print("Enter the duration of the play: ");
            String duration = in.nextLine();

            String[] splitTime = duration.split(":");
            int hour = Integer.parseInt(splitTime[0]);
            int minutes = Integer.parseInt(splitTime[1]);

            if (hour > 4 || minutes > 59)
                System.out.println("\uF0FB The duration you introduced is not valid! Please try again! \uF0FB\n");
            else {
                this.duration = duration;
                break;
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
                "The duration of the play: " + duration + '\n' +
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
