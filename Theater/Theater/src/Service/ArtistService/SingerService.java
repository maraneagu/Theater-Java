package Service.ArtistService;

import Service.TheaterService;
import Theater.Artist.Singer;
import Theater.Spectacle.Musical;
import Theater.Spectacle.Opera;
import Theater.Spectacle.Spectacle;

import java.util.List;
import java.util.Scanner;

public class SingerService {
    private final TheaterService theaterService;

    public SingerService() {
        theaterService = TheaterService.getInstance();
    }

    public void addSinger() {
        System.out.println("\uF0B2 The theater's operas & musicals \uF0B2");

        int i, spectacleId, playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        for (i = 0; i < spectacles.size(); i++)
        {
            if (spectacles.get(i) instanceof Opera || spectacles.get(i) instanceof Musical)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacles.get(i).getName() + '"');
            }
        }

        if (playId == 0)
            System.out.println("There are no operas or musicals as of yet." + '\n');
        else
        {
            List<Singer> singers = theaterService.getSingers();

            System.out.println();
            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the opera or the musical where you want to add a new singer: ");
                spectacleId = Integer.parseInt(in.nextLine());

                if (spectacleId >= 1 && spectacleId <= playId)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            Singer singer = new Singer();
            singer.toRead();

            playId = 0;
            for (i = 0; i < spectacles.size(); i++)
                if (spectacles.get(i) instanceof Opera || spectacles.get(i) instanceof Musical)
                {
                    playId++;
                    if (playId == spectacleId)
                    {
                        boolean noDuplicate;
                        List<Singer> sSingers;

                        if (spectacles.get(i) instanceof Opera)
                        {
                            noDuplicate = add(singer, ((Opera) spectacles.get(i)).getSingers());
                            sSingers = ((Opera) spectacles.get(i)).getSingers();
                        }
                        else
                        {
                            noDuplicate = add(singer, ((Musical) spectacles.get(i)).getSingers());
                            sSingers = ((Musical) spectacles.get(i)).getSingers();
                        }

                        if (noDuplicate)
                        {
                            sSingers.add(singer);
                            System.out.println("\uF0B2 A new singer was added to " + '"' +
                                    spectacles.get(i).getName() + '"' + "'s singers list!");
                        }
                        else System.out.println("\uF04A The singer you entered already exists in " + '"' +
                                spectacles.get(i).getName() + '"' + "'s singers list!");

                        if (add(singer, singers))
                        {
                            singers.add(singer);
                            System.out.println("\uF0B2 A new singer was added to theater's singers list!");
                        }
                        else System.out.println("\uF04A The singer you entered already exists in theater's singers list!");

                        System.out.println();
                        break;
                    }
                }
        }
    }

    public void removeSinger() {
        System.out.println("\uF0B2 The theater's operas & musicals \uF0B2");

        int i, spectacleId, playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        for (i = 0; i < spectacles.size(); i++)
            if (spectacles.get(i) instanceof Opera || spectacles.get(i) instanceof Musical)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacles.get(i).getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no operas or musicals as of yet." + '\n');
        else
        {
            System.out.println();
            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the opera or musical where you want to remove an actor: ");
                spectacleId = Integer.parseInt(in.nextLine());

                if (spectacleId >= 1 && spectacleId <= playId)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            playId = 0;
            for (i = 0; i < spectacles.size(); i++)
                if (spectacles.get(i) instanceof Opera || spectacles.get(i) instanceof Musical)
                {
                    playId++;
                    if (playId == spectacleId)
                    {
                        int j, singerId;

                        List<Singer> sSingers;
                        if (spectacles.get(i) instanceof Opera)
                            sSingers = ((Opera) spectacles.get(i)).getSingers();
                        else sSingers = ((Musical) spectacles.get(i)).getSingers();

                        System.out.println("\n\uF0B2 The " + '"' + spectacles.get(i).getName() + '"' +
                                "'s singers list \uF0B2");

                        if (sSingers.size() == 0) System.out.println("There are no singers as of yet.\n");
                        else
                        {
                            for (j = 0; j < sSingers.size(); j++)
                                System.out.println(j + 1 + ". " + sSingers.get(j).getName());

                            System.out.println();
                            while (true)
                            {
                                Scanner in = new Scanner(System.in);

                                System.out.print("Enter the number of the singer you want to remove: ");
                                singerId = Integer.parseInt(in.nextLine());

                                if (singerId >= 1 && singerId <= sSingers.size())
                                    break;
                                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
                            }

                            System.out.println("\uF0B2 The singer " + '"' + sSingers.get(singerId - 1).getName() + '"' +
                                    " was removed from " + '"' + spectacles.get(i).getName() + '"'
                                    + "'s singers list! \n");

                            sSingers.remove(singerId - 1);
                            break;
                        }
                    }
                }
        }
    }

    public void listSingers() {
        System.out.println("\uF0B2 The theater's operas & musicals \uF0B2");

        int i, spectacleId, playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        for (i = 0; i < spectacles.size(); i++)
            if (spectacles.get(i) instanceof Opera || spectacles.get(i) instanceof Musical)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacles.get(i).getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no operas or musicals as of yet." + '\n');
        else
        {
            System.out.println();
            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the opera or musical where you want to list the singers: ");
                spectacleId = Integer.parseInt(in.nextLine());

                if (spectacleId >= 1 && spectacleId <= playId)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            playId = 0;
            for (i = 0; i < spectacles.size(); i++)
                if (spectacles.get(i) instanceof Opera || spectacles.get(i) instanceof Musical) {
                    playId++;
                    if (playId == spectacleId)
                    {
                        List<Singer> sSingers;
                        if (spectacles.get(i) instanceof Opera)
                            sSingers = ((Opera) spectacles.get(i)).getSingers();
                        else sSingers = ((Musical) spectacles.get(i)).getSingers();

                        System.out.println("\n\uF0B2 The " + '"' + spectacles.get(i).getName() + '"' +
                                "'s singers list \uF0B2");

                        if (sSingers.size() == 0) System.out.println("There are no singers as of yet. \n");
                        else
                        {
                            for (Singer singer : sSingers)
                                System.out.println(singer);
                            System.out.println();
                        }
                    }
                }
        }
    }
    public boolean add(Singer singer, List<Singer> singers) {
        for (Singer s : singers)
            if (s.getName().equalsIgnoreCase(singer.getName()))
                return false;
        return true;
    }
}
