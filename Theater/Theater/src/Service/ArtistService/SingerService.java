package Service.ArtistService;

import Audit.Audit;
import Repository.ActorRepository;
import Repository.SingerRepository;
import Service.TheaterService;
import Theater.Artist.Singer;
import Theater.Spectacle.Musical;
import Theater.Spectacle.Opera;
import Theater.Spectacle.Spectacle;
import Exception.InvalidNumberException;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SingerService {
    private final TheaterService theaterService;

    public SingerService() {
        theaterService = TheaterService.getInstance();
    }

    public void addSinger()
    {
        String spectacleId;
        int playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        System.out.println("\uF0B2 The theater's operas & musicals \uF0B2");

        for (Spectacle spectacle : spectacles)
        {
            if (spectacle instanceof Opera || spectacle instanceof Musical)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getName() + '"');
            }
        }

        if (playId == 0)
            System.out.println("There are no operas or musicals as of yet." + '\n');
        else
        {
            Map<Integer, Singer> singers = theaterService.getSingers();

            System.out.println();
            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the opera or the musical where you want to add a new singer: ");
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

            Singer singer = new Singer();
            singer.toRead();

            playId = 0;
            for (Spectacle spectacle : spectacles)
                if (spectacle instanceof Opera || spectacle instanceof Musical)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        boolean noDuplicate;
                        List<Singer> sSingers;
                        SingerRepository singerRepository = SingerRepository.getInstance();
                        Audit audit = Audit.getInstance();

                        if (spectacle instanceof Opera)
                        {
                            noDuplicate = add(singer, ((Opera) spectacle).getSingers());
                            sSingers = ((Opera) spectacle).getSingers();
                        }
                        else
                        {
                            noDuplicate = add(singer, ((Musical) spectacle).getSingers());
                            sSingers = ((Musical) spectacle).getSingers();
                        }

                        if (add(singer, singers))
                        {
                            singerRepository.insertSinger(singer);
                            singers.put(theaterService.getSingers().size() + 1, singer);

                            System.out.println("\uF0B2 A new singer was added to theater's singers list!");
                            audit.writeToFile("A new singer was added to theater's singers list: " + singer.getName());
                        }
                        else System.out.println("\uF04A The singer you entered already exists in theater's singers list!");

                        if (noDuplicate)
                        {
                            if (spectacle instanceof Opera)
                                singerRepository.insertOperaSinger((Opera) spectacle, singer);
                            else singerRepository.insertMusicalSinger((Musical) spectacle, singer);

                            sSingers.add(singer);
                            singer.getSpectacles().add(spectacle);

                            System.out.println("\uF0B2 A new singer was added to " + '"' +
                                    spectacle.getName() + '"' + "'s singers list!");
                            audit.writeToFile("A new singer was added to " + '"' + spectacle.getName() + '"' + "'s singers list: " + singer.getName());
                        }
                        else System.out.println("\uF04A The singer you entered already exists in " + '"' +
                                spectacle.getName() + '"' + "'s singers list!");

                        System.out.println();
                        break;
                    }
                }
        }
    }

    public void removeSinger()
    {
        String spectacleId;
        int playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        System.out.println("\uF0B2 The theater's operas & musicals \uF0B2");

        for (Spectacle spectacle : spectacles)
            if (spectacle instanceof Opera || spectacle instanceof Musical)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no operas or musicals as of yet." + '\n');
        else
        {
            System.out.println();
            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the opera or musical where you want to remove an actor: ");
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
                if (spectacle instanceof Opera || spectacle instanceof Musical)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        List<Singer> sSingers;
                        if (spectacle instanceof Opera)
                            sSingers = ((Opera) spectacle).getSingers();
                        else sSingers = ((Musical) spectacle).getSingers();

                        System.out.println("\n\uF0B2 The " + '"' + spectacle.getName() + '"' +
                                "'s singers list \uF0B2");

                        if (sSingers.isEmpty()) System.out.println("There are no singers as of yet.\n");
                        else
                        {
                            int j, singerId;
                            String sSingerId;

                            for (j = 0; j < sSingers.size(); j++)
                                System.out.println(j + 1 + ". " + sSingers.get(j).getName());

                            System.out.println();
                            while (true)
                            {
                                try
                                {
                                    Scanner in = new Scanner(System.in);

                                    System.out.print("Enter the number of the singer that you want to remove: ");
                                    sSingerId = in.nextLine().trim();

                                    if (sSingerId.compareTo("1") >= 0 && sSingerId.compareTo(Integer.toString(sSingers.size())) <= 0)
                                        break;
                                    else throw new InvalidNumberException();
                                }
                                catch (InvalidNumberException exception)
                                {
                                    System.out.println(exception.getMessage());
                                }
                            }

                            singerId = Integer.parseInt(sSingerId);
                            System.out.println("\n\uF04A The singer " + sSingers.get(singerId - 1).getName() +
                                    " was removed from " + '"' + spectacle.getName() + '"'
                                    + "'s singers list! \n");

                            Audit audit = Audit.getInstance();
                            audit.writeToFile("The singer " + sSingers.get(singerId - 1).getName() +
                                    " was removed from " + '"' + spectacle.getName() + '"'
                                            + "'s singers list!");

                            SingerRepository singerRepository = SingerRepository.getInstance();
                            if (spectacle instanceof Opera)
                                singerRepository.deleteOperaSinger((Opera) spectacle, sSingers.get(singerId - 1));
                            else singerRepository.deleteMusicalSinger((Musical) spectacle, sSingers.get(singerId - 1));

                            sSingers.get(singerId - 1).getSpectacles().remove(spectacle);
                            sSingers.remove(singerId - 1);
                            break;
                        }
                        break;
                    }
                }
        }
    }

    public void listSingers()
    {
        String spectacleId;
        int playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        System.out.println("\uF0B2 The theater's operas & musicals \uF0B2");

        for (Spectacle spectacle : spectacles)
            if (spectacle instanceof Opera || spectacle instanceof Musical)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no operas or musicals as of yet." + '\n');
        else
        {
            System.out.println();
            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the opera or musical where you want to list the singers: ");
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
                if (spectacle instanceof Opera || spectacle instanceof Musical)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        List<Singer> sSingers;
                        if (spectacle instanceof Opera)
                            sSingers = ((Opera) spectacle).getSingers();
                        else sSingers = ((Musical) spectacle).getSingers();

                        System.out.println("\n\uF0B2 The " + '"' + spectacle.getName() + '"' +
                                "'s singers list \uF0B2");

                        if (sSingers.isEmpty()) System.out.println("There are no singers as of yet. \n");
                        else
                        {
                            for (Singer singer : sSingers)
                                System.out.println(singer);
                            System.out.println();
                        }

                        Audit audit = Audit.getInstance();
                        audit.writeToFile("The " + '"' + spectacle.getName() + '"' +
                                "'s singers list was listed!");

                        break;
                    }
                }
        }
    }

    public void searchSinger()
    {
        Map<Integer, Singer> singers = theaterService.getSingers();

        System.out.println("\uF0B2 The theater's singers \uF0B2");

        if (singers.isEmpty())
            System.out.println("There are no singers as of yet.\n");
        else
        {
            for (Map.Entry<Integer, Singer> singer : singers.entrySet())
                System.out.println(singer.getKey() + ". " + singer.getValue().getName());
            System.out.println();

            String sSingerId;
            int singerId;
            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the singer that you want to search the spectacles for: ");
                    sSingerId = in.nextLine().trim();

                    if (sSingerId.compareTo("1") >= 0 && sSingerId.compareTo(Integer.toString(singers.size())) <= 0)
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            singerId = Integer.parseInt(sSingerId);
            System.out.println("\n\uF0B2 " + singers.get(singerId).getName() + "'s spectacles \uF0B2");

            if (singers.get(singerId).getSpectacles().isEmpty())
                System.out.println("The singer has not starred in any spectacles from the theater's spectacles as of yet.\n");
            else
            {
                for (Spectacle spectacle : singers.get(singerId).getSpectacles())
                    System.out.println("\uF09F " + spectacle.getName() + " by " + spectacle.getDirector().getName());
                System.out.println();
            }

            Audit audit = Audit.getInstance();
            audit.writeToFile("The spectacles that have the singer " + singers.get(singerId).getName() + " in their distribution were listed!");
        }
    }

    public boolean add(Singer singer, List<Singer> singers)
    {
        for (Singer s : singers)
            if (s.getName().equalsIgnoreCase(singer.getName()))
                return false;
        return true;
    }
    public boolean add(Singer singer, Map<Integer, Singer> singers)
    {
        for (Map.Entry<Integer, Singer> s : singers.entrySet())
            if (s.getValue().getName().equalsIgnoreCase(singer.getName()))
                return false;
        return true;
    }
}
