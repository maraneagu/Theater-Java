package Service;

import Theater.Artist.Actor;
import Theater.Artist.Singer;
import Theater.Artist.Dancer;
import Theater.Stage;
import Theater.Event;
import java.util.*;

public class PrintService {
    public PrintService() {}
    public void printStages(List<Stage> stages) {
        for (Stage stage : stages)
            System.out.println("\uF09F " + stage.getName() + " stage" +
                    " with the capacity of " + stage.getNumberOfRows() * stage.getNumberOfSeatsPerRow() +
                    " seats");
        System.out.println();
    }

    public StringBuilder printActorsList(Collection<Actor> actors) {
        StringBuilder printActors = new StringBuilder();

        for (Actor actor : actors)
        {
            printActors.append(actor.getName());
            printActors.append(", ");
        }
        return printActors.delete(printActors.length() - 2, printActors.length());
    }

    public StringBuilder printSingersList(Collection<Singer> singers) {
        StringBuilder printSingers = new StringBuilder();

        for (Singer singer : singers)
        {
            printSingers.append(singer.getName());
            printSingers.append(", ");
        }
        return printSingers.delete(printSingers.length() - 2, printSingers.length());
    }

    public StringBuilder printDancersList(Collection<Dancer> dancers) {
        StringBuilder printDancers = new StringBuilder();

        for (Dancer dancer: dancers)
        {
            printDancers.append(dancer.getName());
            printDancers.append(", ");
        }
        return printDancers.delete(printDancers.length() - 2, printDancers.length());
    }

    public StringBuilder printEventSeats(Event event) {
        List<List<Boolean>> seats = event.getSeats();
        StringBuilder printSeats = new StringBuilder();

        for (int i = 0; i < seats.size(); i++)
        {
            printSeats.append("Row " + (i + 1) + ": ");

            boolean seatsAvailable = false;
            for (int j = 0; j < seats.get(i).size(); j++)
                if (seats.get(i).get(j))
                {
                    seatsAvailable = true;
                    printSeats.append((j + 1) + ", ");
                }

            if (seatsAvailable == false) printSeats.append("There are no seats available on this row.\n");
            else
            {
                printSeats.delete(printSeats.length() - 2, printSeats.length());
                printSeats.append('\n');
            }
        }
        return printSeats;
    }
}
