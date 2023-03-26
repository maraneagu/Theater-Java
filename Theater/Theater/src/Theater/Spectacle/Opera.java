package Theater.Spectacle;

import Service.PrintService;
import Theater.Artist.Singer;
import Theater.Director;

import java.util.*;

public class Opera extends Spectacle {
    private List<Singer> singers;

    public Opera() {
        super();
        this.singers = new ArrayList<>();
    }
    public Opera(String name, Director director,
                List<Singer> singers) {
        super(name, director);
        this.singers = singers;
    }

    @Override
    public void toRead() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the name of the opera: ");
        this.name = in.nextLine();

        this.director.toRead();

        while (true)
        {
            System.out.println("The duration of the opera! The format: 0h:mm!");
            System.out.print("Enter the duration of the opera: ");
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
        outputToString = "\uF0B2 Opera \uF0B2" + '\n' +
                "The name of the opera: " + '"' + name + '"' + '\n' +
                "The director of the opera: " + director.getName() + '\n' +
                "The duration of the opera: " + duration + '\n' +
                "The opera singers: ";

        if (singers.size() == 0)
            outputToString += "there are no singers as of yet.";
        else {
            PrintService printService = new PrintService();
            outputToString += printService.printSingersList(singers);
        }
        return outputToString + '\n';
    }

    public List<Singer> getSingers() {
        return singers;
    }
    public void setSingers(List<Singer> singers) {
        this.singers = singers;
    }
}
