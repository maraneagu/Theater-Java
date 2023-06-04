package Service.ArtistService;

import Audit.Audit;
import Repository.DancerRepository;
import Service.TheaterService;
import Theater.Artist.Dancer;
import Theater.Spectacle.Ballet;
import Theater.Spectacle.Musical;
import Theater.Spectacle.Spectacle;
import Exception.InvalidNumberException;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DancerService
{
    private final TheaterService theaterService;

    public DancerService() {
        theaterService = TheaterService.getInstance();
    }

    public void addDancer()
    {
        String spectacleId;
        int playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        System.out.println("\uF0B2 The theater's musicals & ballets \uF0B2");

        for (Spectacle spectacle : spectacles)
            if (spectacle instanceof Musical || spectacle instanceof Ballet)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no musicals or ballets as of yet." + '\n');
        else
        {
            Map<Integer, Dancer> dancers = theaterService.getDancers();

            System.out.println();
            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the musical or the ballet where you want to add a new dancer: ");
                    spectacleId = in.nextLine().trim();

                    if (spectacleId.compareTo("1") >= 0 && spectacleId.compareTo(Integer.toString(playId)) <= 0)
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            Dancer dancer = new Dancer();
            dancer.toRead();

            playId = 0;
            for (Spectacle spectacle : spectacles)
                if (spectacle instanceof Musical || spectacle instanceof Ballet)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        boolean noDuplicate;
                        List<Dancer> sDancers;
                        DancerRepository dancerRepository = DancerRepository.getInstance();
                        Audit audit = Audit.getInstance();

                        if (spectacle instanceof Musical) {
                            noDuplicate = add(dancer, ((Musical) spectacle).getDancers());
                            sDancers = ((Musical) spectacle).getDancers();
                        }
                        else
                        {
                            noDuplicate = add(dancer, ((Ballet) spectacle).getDancers());
                            sDancers = ((Ballet) spectacle).getDancers();
                        }

                        if (add(dancer, dancers))
                        {
                            dancerRepository.insertDancer(dancer);
                            dancers.put(theaterService.getDancers().size() + 1, dancer);

                            System.out.println("\uF0B2 A new dancer was added to theater's dancers list!");
                            audit.writeToFile("A new dancer was added to theater's dancers list: " + dancer.getName(), "./theaterArtists.cvs");
                        }
                        else System.out.println("\uF04A The dancer you entered already exists in theater's dancers list!");

                        if (noDuplicate)
                        {
                            if (spectacle instanceof Musical)
                                dancerRepository.insertMusicalDancer((Musical) spectacle, dancer);
                            else dancerRepository.insertBalletDancer((Ballet) spectacle, dancer);

                            sDancers.add(dancer);
                            dancer.getSpectacles().add(spectacle);

                            System.out.println("\uF0B2 A new dancer was added to " + '"' +
                                    spectacle.getName() + '"' + "'s dancers list!");
                            audit.writeToFile("A new dancer was added to " + '"' + spectacle.getName() + '"' + "'s dancers list: " + dancer.getName(), "./theaterArtists.cvs");
                        }
                        else System.out.println("\uF04A The dancer you entered already exists in " + '"' +
                                spectacle.getName() + '"' + "'s dancers list!");

                        System.out.println();
                        break;
                    }
                }
        }
    }
    public void removeDancer()
    {
        String spectacleId;
        int playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        System.out.println("\uF0B2 The theater's musicals & ballets \uF0B2");

        for (Spectacle spectacle : spectacles)
            if (spectacle instanceof Musical || spectacle instanceof Ballet)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no musicals or ballets as of yet." + '\n');
        else
        {
            System.out.println();
            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the musical or ballet where you want to remove a dancer: ");
                    spectacleId = in.nextLine();

                    if (spectacleId.compareTo("1") >= 0 && spectacleId.compareTo(Integer.toString(playId)) <= 0)
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            playId = 0;
            for (Spectacle spectacle : spectacles)
                if (spectacle instanceof Musical || spectacle instanceof Ballet)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        List<Dancer> sDancers;
                        if (spectacle instanceof Musical)
                            sDancers = ((Musical) spectacle).getDancers();
                        else sDancers = ((Ballet) spectacle).getDancers();

                        System.out.println("\n\uF0B2 The " + '"' + spectacle.getName() + '"' +
                                "'s dancers list \uF0B2");

                        if (sDancers.isEmpty()) System.out.println("There are no dancers as of yet.\n");
                        else
                        {
                            int j, dancerId;
                            String sDancerId;

                            for (j = 0; j < sDancers.size(); j++)
                                System.out.println(j + 1 + ". " + sDancers.get(j).getName());
                            System.out.println();

                            while (true)
                            {
                                try
                                {
                                    Scanner in = new Scanner(System.in);

                                    System.out.print("Enter the number of the dancer that you want to remove: ");
                                    sDancerId = in.nextLine().trim();

                                    if (sDancerId.compareTo("1") >= 0 && sDancerId.compareTo(Integer.toString(sDancers.size())) <= 0)
                                        break;
                                    else throw new InvalidNumberException();
                                }
                                catch (InvalidNumberException exception)
                                {
                                    System.out.println(exception.getMessage());
                                }
                            }

                            dancerId = Integer.parseInt(sDancerId);
                            System.out.println("\n\uF04A The dancer " + sDancers.get(dancerId - 1).getName() +
                                    " was removed from " + '"' + spectacle.getName() + '"'
                                    + "'s dancers list! \n");

                            Audit audit = Audit.getInstance();
                            audit.writeToFile("The dancer " + sDancers.get(dancerId - 1).getName() + " was removed from " + '"' + spectacle.getName() + '"' + "'s dancers list!", "theaterArtists.cvs");

                            DancerRepository dancerRepository = DancerRepository.getInstance();
                            if (spectacle instanceof Musical)
                                dancerRepository.deleteMusicalDancer((Musical) spectacle, sDancers.get(dancerId - 1));
                            else dancerRepository.deleteBalletDancer((Ballet) spectacle, sDancers.get(dancerId - 1));

                            sDancers.get(dancerId - 1).getSpectacles().remove(spectacle);
                            sDancers.remove(dancerId - 1);
                            break;
                        }
                        break;
                    }
                }
        }
    }
    public void listDancers()
    {
        String spectacleId;
        int playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        System.out.println("\uF0B2 The theater's musicals & ballets \uF0B2");

        for (Spectacle spectacle : spectacles)
            if (spectacle instanceof Musical || spectacle instanceof Ballet)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no musicals or ballets as of yet." + '\n');
        else
        {
            System.out.println();
            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the musical or ballets where you want to list the dancers: ");
                    spectacleId = in.nextLine().trim();

                    if (spectacleId.compareTo("1") >= 0 && spectacleId.compareTo(Integer.toString(playId)) <= 0)
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            playId = 0;
            for (Spectacle spectacle : spectacles)
                if (spectacle instanceof Musical || spectacle instanceof Ballet)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        List<Dancer> sDancers;
                        if (spectacle instanceof Musical)
                            sDancers = ((Musical) spectacle).getDancers();
                        else sDancers = ((Ballet) spectacle).getDancers();

                        System.out.println("\n\uF0B2 The " + '"' + spectacle.getName() + '"' +
                                "'s dancers list \uF0B2");

                        if (sDancers.isEmpty()) System.out.println("There are no dancers as of yet.\n");
                        else
                        {
                            for (Dancer dancer : sDancers)
                                System.out.println(dancer);
                            System.out.println();
                        }

                        Audit audit = Audit.getInstance();
                        audit.writeToFile("The " + '"' + spectacle.getName() + '"' + "'s dancers list was listed!", "./theaterArtists.cvs");

                        break;
                    }
                }
        }
    }

    public void searchDancer()
    {
        Map<Integer, Dancer> dancers = theaterService.getDancers();

        System.out.println("\uF0B2 The theater's dancers \uF0B2");

        if (dancers.isEmpty())
            System.out.println("There are no singers as of yet.\n");
        else
        {
            String sDancerId;
            int dancerId;

            for (Map.Entry<Integer, Dancer> dancer : dancers.entrySet())
                System.out.println(dancer.getKey() + ". " + dancer.getValue().getName());
            System.out.println();

            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the dancer that you want to search the spectacles for: ");
                    sDancerId = in.nextLine().trim();

                    if (sDancerId.compareTo("1") >= 0 && sDancerId.compareTo(Integer.toString(dancers.size())) <= 0)
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            dancerId = Integer.parseInt(sDancerId);
            System.out.println("\n\uF0B2 " + dancers.get(dancerId).getName() + "'s spectacles \uF0B2");

            if (dancers.get(dancerId).getSpectacles().isEmpty())
                System.out.println("The dancer has not starred in any spectacles from the theater's spectacles as of yet.\n");
            else
            {
                for (Spectacle spectacle : dancers.get(dancerId).getSpectacles())
                    System.out.println("\uF09F " + spectacle.getName() + " by " + spectacle.getDirector().getName());
                System.out.println();
            }

            Audit audit = Audit.getInstance();
            audit.writeToFile("The spectacles that have the dancer " + dancers.get(dancerId).getName() + " in their distribution were listed!", "./theaterArtists.cvs");
        }
    }

    public boolean add(Dancer dancer, List<Dancer> dancers)
    {
        for (Dancer d : dancers)
            if (d.getName().equalsIgnoreCase(dancer.getName()))
                return false;
        return true;
    }
    public boolean add(Dancer dancer, Map<Integer, Dancer> dancers)
    {
        for (Map.Entry<Integer, Dancer> d : dancers.entrySet())
            if (d.getValue().getName().equalsIgnoreCase(dancer.getName()))
                return false;
        return true;
    }
}
