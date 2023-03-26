package Service.ArtistService;

import Service.TheaterService;
import Theater.Artist.Dancer;
import Theater.Spectacle.Ballet;
import Theater.Spectacle.Musical;
import Theater.Spectacle.Spectacle;

import java.util.List;
import java.util.Scanner;

public class DancerService {
    private final TheaterService theaterService;

    public DancerService() {
        theaterService = TheaterService.getInstance();
    }

    public void addDancer() {
        System.out.println("\uF0B2 The theater's musicals & ballets \uF0B2");

        int i, spectacleId, playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        for (i = 0; i < spectacles.size(); i++) {
            if (spectacles.get(i) instanceof Musical || spectacles.get(i) instanceof Ballet)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacles.get(i).getName() + '"');
            }
        }

        if (playId == 0)
            System.out.println("There are no musicals or ballets as of yet." + '\n');
        else
        {
            List<Dancer> dancers = theaterService.getDancers();

            System.out.println();
            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the musical or the ballet where you want to add a new dancer: ");
                spectacleId = Integer.parseInt(in.nextLine());

                if (spectacleId >= 1 && spectacleId <= playId)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            Dancer dancer = new Dancer();
            dancer.toRead();

            playId = 0;
            for (i = 0; i < spectacles.size(); i++)
                if (spectacles.get(i) instanceof Musical || spectacles.get(i) instanceof Ballet)
                {
                    playId++;
                    if (playId == spectacleId)
                    {
                        boolean noDuplicate;
                        List<Dancer> sDancers;

                        if (spectacles.get(i) instanceof Musical) {
                            noDuplicate = add(dancer, ((Musical) spectacles.get(i)).getDancers());
                            sDancers = ((Musical) spectacles.get(i)).getDancers();
                        }
                        else
                        {
                            noDuplicate = add(dancer, ((Ballet) spectacles.get(i)).getDancers());
                            sDancers = ((Ballet) spectacles.get(i)).getDancers();
                        }

                        if (noDuplicate)
                        {
                            sDancers.add(dancer);
                            System.out.println("\uF0B2 A new dancer was added to " + '"' +
                                    spectacles.get(i).getName() + '"' + "'s dancers list!");
                        }
                        else System.out.println("\uF04A The dancer you entered already exists in " + '"' +
                                spectacles.get(i).getName() + '"' + "'s dancers list!");

                        if (add(dancer, dancers))
                        {
                            dancers.add(dancer);
                            System.out.println("\uF0B2 A new dancer was added to theater's dancers list!");
                        }
                        else System.out.println("\uF04A The dancer you entered already exists in theater's dancers list!");

                        System.out.println();
                        break;
                    }
                }
        }
    }
    public void removeDancer() {
        System.out.println("\uF0B2 The theater's musicals & ballets \uF0B2");

        int i, spectacleId, playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        for (i = 0; i < spectacles.size(); i++)
            if (spectacles.get(i) instanceof Musical || spectacles.get(i) instanceof Ballet)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacles.get(i).getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no musicals or ballets as of yet." + '\n');
        else
        {
            System.out.println();
            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the musical or ballet where you want to remove a dancer: ");
                spectacleId = Integer.parseInt(in.nextLine());

                if (spectacleId >= 1 && spectacleId <= playId)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            playId = 0;
            for (i = 0; i < spectacles.size(); i++)
                if (spectacles.get(i) instanceof Musical || spectacles.get(i) instanceof Ballet)
                {
                    playId++;
                    if (playId == spectacleId)
                    {
                        int j, dancerId;

                        List<Dancer> sDancers;
                        if (spectacles.get(i) instanceof Musical)
                            sDancers = ((Musical) spectacles.get(i)).getDancers();
                        else sDancers = ((Ballet) spectacles.get(i)).getDancers();

                        System.out.println("\n\uF0B2 The " + '"' + spectacles.get(i).getName() + '"' +
                                "'s dancers list \uF0B2");

                        if (sDancers.size() == 0) System.out.println("There are no dancers as of yet.\n");
                        else
                        {
                            for (j = 0; j < sDancers.size(); j++)
                                System.out.println(j + 1 + ". " + sDancers.get(j).getName());

                            System.out.println();
                            while (true)
                            {
                                Scanner in = new Scanner(System.in);

                                System.out.print("Enter the number of the dancer you want to remove: ");
                                dancerId = Integer.parseInt(in.nextLine());

                                if (dancerId >= 1 && dancerId <= sDancers.size())
                                    break;
                                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
                            }

                            System.out.println("\uF0B2 The dancer " + '"' + sDancers.get(dancerId - 1).getName() + '"' +
                                    " was removed from " + '"' + spectacles.get(i).getName() + '"'
                                    + "'s dancers list! \n");

                            sDancers.remove(dancerId - 1);
                            break;
                        }
                    }
                }
        }
    }
    public void listDancers() {
        System.out.println("\uF0B2 The theater's musicals & ballets \uF0B2");

        int i, spectacleId, playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        for (i = 0; i < spectacles.size(); i++)
            if (spectacles.get(i) instanceof Musical || spectacles.get(i) instanceof Ballet)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacles.get(i).getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no musicals or ballets as of yet." + '\n');
        else
        {
            System.out.println();
            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the musical or ballets where you want to list the dancers: ");
                spectacleId = Integer.parseInt(in.nextLine());

                if (spectacleId >= 1 && spectacleId <= playId)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            playId = 0;
            for (i = 0; i < spectacles.size(); i++)
                if (spectacles.get(i) instanceof Musical || spectacles.get(i) instanceof Ballet)
                {
                    playId++;
                    if (playId == spectacleId)
                    {
                        List<Dancer> sDancers;
                        if (spectacles.get(i) instanceof Musical)
                            sDancers = ((Musical) spectacles.get(i)).getDancers();
                        else sDancers = ((Ballet) spectacles.get(i)).getDancers();

                        System.out.println("\n\uF0B2 The " + '"' + spectacles.get(i).getName() + '"' +
                                "'s dancers list \uF0B2");

                        if (sDancers.size() == 0) System.out.println("There are no dancers as of yet.\n");
                        else
                        {
                            for (Dancer dancer : sDancers)
                                System.out.println(dancer);
                            System.out.println();
                        }
                    }
                }
        }
    }
    public boolean add(Dancer dancer, List<Dancer> dancers) {
        for (Dancer d : dancers)
            if (d.getName().equalsIgnoreCase(dancer.getName()))
                return false;
        return true;
    }
}
