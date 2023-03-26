package Theater.Spectacle;

import Service.PrintService;
import Theater.Artist.Dancer;
import Theater.Director;

import java.util.*;

public class Ballet extends Spectacle {
    private List<Dancer> dancers;

    public Ballet() {
        super();
        this.dancers = new ArrayList<>();
    }
    public Ballet(String name, Director director,
                 List<Dancer> dancers) {
        super(name, director);
        this.dancers = dancers;
    }

    @Override
    public void toRead() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the name of the ballet: ");
        this.name = in.nextLine();

        this.director.toRead();

        while (true)
        {
            System.out.println("The duration of the ballet! It should be between 1 hour and 4 hours with the format: 0h:mm!");
            System.out.print("Enter the duration of the ballet: ");
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
        outputToString = "\uF0B2 Ballet \uF0B2" + '\n' +
                "The name of the ballet: " + '"' + name + '"' + '\n' +
                "The director of the ballet: " + director.getName() + '\n' +
                "The duration of the ballet: " + duration + '\n' +
                "The ballet dancers: ";

        if (dancers.size() == 0)
            outputToString += "there are no dancers as of yet.";
        else {
            PrintService printService = new PrintService();
            outputToString += printService.printDancersList(dancers);
        }
        return outputToString + '\n';
    }

    public List<Dancer> getDancers() {
        return dancers;
    }
    public void setDancers(List<Dancer> dancers) {
        this.dancers = dancers;
    }
}
