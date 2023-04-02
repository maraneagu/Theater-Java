package Service.ArtistService;

import Service.TheaterService;
import Theater.Artist.Dancer;
import Theater.Artist.Singer;
import Theater.Spectacle.Ballet;
import Theater.Spectacle.Musical;
import Theater.Spectacle.Spectacle;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DancerService {
    private final TheaterService theaterService;

    public DancerService() {
        theaterService = TheaterService.getInstance();
    }

    public void addDancer() {
        System.out.println("\uF0B2 The theater's musicals & ballets \uF0B2");

        int i, spectacleId, playId = 0;
        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();

        for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
            if (spectacle.getValue() instanceof Musical || spectacle.getValue() instanceof Ballet)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getValue().getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no musicals or ballets as of yet." + '\n');
        else
        {
            Map<Integer, Dancer> dancers = theaterService.getDancers();

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
            for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                if (spectacle.getValue() instanceof Musical || spectacle.getValue() instanceof Ballet)
                {
                    playId++;
                    if (playId == spectacleId)
                    {
                        boolean noDuplicate;
                        List<Dancer> sDancers;

                        if (spectacle.getValue() instanceof Musical) {
                            noDuplicate = add(dancer, ((Musical) spectacle.getValue()).getDancers());
                            sDancers = ((Musical) spectacle.getValue()).getDancers();
                        }
                        else
                        {
                            noDuplicate = add(dancer, ((Ballet) spectacle.getValue()).getDancers());
                            sDancers = ((Ballet) spectacle.getValue()).getDancers();
                        }

                        if (noDuplicate)
                        {
                            sDancers.add(dancer);
                            dancer.getSpectacles().add(spectacle.getValue());
                            System.out.println("\uF0B2 A new dancer was added to " + '"' +
                                    spectacle.getValue().getName() + '"' + "'s dancers list!");
                        }
                        else System.out.println("\uF04A The dancer you entered already exists in " + '"' +
                                spectacle.getValue().getName() + '"' + "'s dancers list!");

                        if (add(dancer, dancers))
                        {
                            theaterService.setDancerId(theaterService.getDancerId() + 1);
                            dancers.put(theaterService.getDancerId(), dancer);
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

        int spectacleId, playId = 0;
        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();

        for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
            if (spectacle.getValue() instanceof Musical || spectacle.getValue() instanceof Ballet)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getValue().getName() + '"');
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
            for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                if (spectacle.getValue() instanceof Musical || spectacle.getValue() instanceof Ballet)
                {
                    playId++;
                    if (playId == spectacleId)
                    {
                        int j, dancerId;

                        List<Dancer> sDancers;
                        if (spectacle.getValue() instanceof Musical)
                            sDancers = ((Musical) spectacle.getValue()).getDancers();
                        else sDancers = ((Ballet) spectacle.getValue()).getDancers();

                        System.out.println("\n\uF0B2 The " + '"' + spectacle.getValue().getName() + '"' +
                                "'s dancers list \uF0B2");

                        if (sDancers.isEmpty()) System.out.println("There are no dancers as of yet.\n");
                        else
                        {
                            for (j = 0; j < sDancers.size(); j++)
                                System.out.println(j + 1 + ". " + sDancers.get(j).getName());

                            System.out.println();
                            while (true)
                            {
                                Scanner in = new Scanner(System.in);

                                System.out.print("Enter the number of the dancer that you want to remove: ");
                                dancerId = Integer.parseInt(in.nextLine());

                                if (dancerId >= 1 && dancerId <= sDancers.size())
                                    break;
                                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
                            }

                            System.out.println("\n\uF04A The dancer " + sDancers.get(dancerId - 1).getName() +
                                    " was removed from " + '"' + spectacle.getValue().getName() + '"'
                                    + "'s dancers list! \n");

                            sDancers.get(dancerId - 1).getSpectacles().remove(spectacle.getValue());
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
        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();

        for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
            if (spectacle.getValue() instanceof Musical || spectacle.getValue() instanceof Ballet)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getValue().getName() + '"');
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
                spectacleId = Integer.parseInt(in.nextLine().trim());

                if (spectacleId >= 1 && spectacleId <= playId)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            playId = 0;
            for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                if (spectacle.getValue() instanceof Musical || spectacle.getValue() instanceof Ballet)
                {
                    playId++;
                    if (playId == spectacleId)
                    {
                        List<Dancer> sDancers;
                        if (spectacle.getValue() instanceof Musical)
                            sDancers = ((Musical) spectacle.getValue()).getDancers();
                        else sDancers = ((Ballet) spectacle.getValue()).getDancers();

                        System.out.println("\n\uF0B2 The " + '"' + spectacle.getValue().getName() + '"' +
                                "'s dancers list \uF0B2");

                        if (sDancers.isEmpty()) System.out.println("There are no dancers as of yet.\n");
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

    public void searchDancer() {
        System.out.println("\uF0B2 The theater's dancers \uF0B2");

        Map<Integer, Dancer> dancers = theaterService.getDancers();

        if (dancers.isEmpty())
            System.out.println("There are no singers as of yet.\n");
        else
        {
            for (Map.Entry<Integer, Dancer> dancer : dancers.entrySet())
                System.out.println(dancer.getKey() + ". " + dancer.getValue().getName());
            System.out.println();

            int dancerId;
            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the dancer that you want to search the spectacles for: ");
                dancerId = Integer.parseInt(in.nextLine().trim());

                if (dancerId >= 1 && dancerId <= dancers.size())
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            System.out.println("\n\uF0B2 " + dancers.get(dancerId).getName() + "'s spectacles \uF0B2");

            if (dancers.get(dancerId).getSpectacles().isEmpty())
                System.out.println("The dancer has not starred in any spectacles from the theater's spectacles as of yet.\n");
            else
            {
                for (Spectacle spectacle : dancers.get(dancerId).getSpectacles())
                    System.out.println("\uF09F " + spectacle.getName() + " by " + spectacle.getDirector().getName());
                System.out.println();
            }
        }
    }

    public boolean add(Dancer dancer, List<Dancer> dancers) {
        for (Dancer d : dancers)
            if (d.getName().equalsIgnoreCase(dancer.getName()))
                return false;
        return true;
    }
    public boolean add(Dancer dancer, Map<Integer, Dancer> dancers) {
        for (Map.Entry<Integer, Dancer> d : dancers.entrySet())
            if (d.getValue().getName().equalsIgnoreCase(dancer.getName()))
                return false;
        return true;
    }
}
