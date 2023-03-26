package Theater.Spectacle;

import Service.PrintService;
import Theater.Director;
import Theater.Artist.Singer;
import Theater.Artist.Dancer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Musical extends Spectacle {
    private List<Singer> singers;
    private List<Dancer> dancers;

    public Musical() {
        super();
        this.singers = new ArrayList<>();
        this.dancers = new ArrayList<>();
    }
    public Musical(String name, Director director,
                 List<Singer> singers, List<Dancer> dancers) {
        super(name, director);
        this.singers = singers;
        this.dancers = dancers;
    }

    @Override
    public void toRead() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the name of the musical: ");
        this.name = in.nextLine();

        this.director.toRead();

        while (true)
        {
            System.out.println("The duration of the musical! It should be between 1 hour and 4 hours with the format: 0h:mm!");
            System.out.print("Enter the duration of the musical: ");
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
        outputToString = "\uF0B2 Musical \uF0B2" + '\n' +
                "The name of the musical: " + '"' + name + '"' + '\n' +
                "The director of the musical: " + director.getName() + '\n' +
                "The duration of the musical: " + duration + '\n' +
                "The musical singers: ";

        if (singers.size() == 0)
            outputToString += "there are no singers as of yet.";
        else {
            PrintService printService = new PrintService();
            outputToString += printService.printSingersList(singers);
        }

        outputToString += "\nThe musical dancers: ";
        if (dancers.size() == 0)
            outputToString += "there are no dancers as of yet.";
        else {
            PrintService printService = new PrintService();
            outputToString += printService.printDancersList(dancers);
        }
        return outputToString + '\n';
    }

    public List<Singer> getSingers() {
        return singers;
    }
    public void setSingers(List<Singer> singers) {
        this.singers = singers;
    }
    public List<Dancer> getDancers() {
        return dancers;
    }
    public void setDancers(List<Dancer> dancers) {
        this.dancers = dancers;
    }
}
