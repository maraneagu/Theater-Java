package Service.ArtistService;

import Service.TheaterService;
import Theater.Artist.Actor;
import Theater.Artist.Singer;
import Theater.Spectacle.Musical;
import Theater.Spectacle.Opera;
import Theater.Spectacle.Spectacle;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SingerService {
    private final TheaterService theaterService;

    public SingerService() {
        theaterService = TheaterService.getInstance();
    }

    public void addSinger() {
        System.out.println("\uF0B2 The theater's operas & musicals \uF0B2");

        String spectacleId;
        int playId = 0;
        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();

        for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
        {
            if (spectacle.getValue() instanceof Opera || spectacle.getValue() instanceof Musical)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getValue().getName() + '"');
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
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the opera or the musical where you want to add a new singer: ");
                spectacleId = in.nextLine().trim();

                if (spectacleId.compareTo("1") >= 0 && spectacleId.compareTo(Integer.toString(playId)) <= 0)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            Singer singer = new Singer();
            singer.toRead();

            playId = 0;
            for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                if (spectacle.getValue() instanceof Opera || spectacle.getValue() instanceof Musical)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        boolean noDuplicate;
                        List<Singer> sSingers;

                        if (spectacle.getValue() instanceof Opera)
                        {
                            noDuplicate = add(singer, ((Opera) spectacle.getValue()).getSingers());
                            sSingers = ((Opera) spectacle.getValue()).getSingers();
                        }
                        else
                        {
                            noDuplicate = add(singer, ((Musical) spectacle.getValue()).getSingers());
                            sSingers = ((Musical) spectacle.getValue()).getSingers();
                        }

                        if (noDuplicate)
                        {
                            sSingers.add(singer);
                            singer.getSpectacles().add(spectacle.getValue());
                            System.out.println("\uF0B2 A new singer was added to " + '"' +
                                    spectacle.getValue().getName() + '"' + "'s singers list!");
                        }
                        else System.out.println("\uF04A The singer you entered already exists in " + '"' +
                                spectacle.getValue().getName() + '"' + "'s singers list!");

                        if (add(singer, singers))
                        {
                            theaterService.setSingerId(theaterService.getSingerId() + 1);
                            singers.put(theaterService.getSingerId(), singer);
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

        String spectacleId;
        int playId = 0;
        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();

        for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
            if (spectacle.getValue() instanceof Opera || spectacle.getValue() instanceof Musical)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getValue().getName() + '"');
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
                spectacleId = in.nextLine().trim();

                if (spectacleId.compareTo("1") >= 0 && spectacleId.compareTo(Integer.toString(playId)) <= 0)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            playId = 0;
            for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                if (spectacle.getValue() instanceof Opera || spectacle.getValue() instanceof Musical)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        List<Singer> sSingers;
                        if (spectacle.getValue() instanceof Opera)
                            sSingers = ((Opera) spectacle.getValue()).getSingers();
                        else sSingers = ((Musical) spectacle.getValue()).getSingers();

                        System.out.println("\n\uF0B2 The " + '"' + spectacle.getValue().getName() + '"' +
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
                                Scanner in = new Scanner(System.in);

                                System.out.print("Enter the number of the singer that you want to remove: ");
                                sSingerId = in.nextLine().trim();

                                if (sSingerId.compareTo("1") >= 0 && sSingerId.compareTo(Integer.toString(sSingers.size())) <= 0)
                                    break;
                                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
                            }

                            singerId = Integer.parseInt(sSingerId);
                            System.out.println("\n\uF04A The singer " + sSingers.get(singerId - 1).getName() +
                                    " was removed from " + '"' + spectacle.getValue().getName() + '"'
                                    + "'s singers list! \n");

                            sSingers.get(singerId - 1).getSpectacles().remove(spectacle.getValue());
                            sSingers.remove(singerId - 1);
                            break;
                        }
                    }
                }
        }
    }

    public void listSingers() {
        System.out.println("\uF0B2 The theater's operas & musicals \uF0B2");

        String spectacleId;
        int playId = 0;
        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();

        for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
            if (spectacle.getValue() instanceof Opera || spectacle.getValue() instanceof Musical)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getValue().getName() + '"');
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
                spectacleId = in.nextLine().trim();

                if (spectacleId.compareTo("1") >= 0 && spectacleId.compareTo(Integer.toString(playId)) <= 0)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            playId = 0;
            for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                if (spectacle.getValue() instanceof Opera || spectacle.getValue() instanceof Musical)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        List<Singer> sSingers;
                        if (spectacle.getValue() instanceof Opera)
                            sSingers = ((Opera) spectacle.getValue()).getSingers();
                        else sSingers = ((Musical) spectacle.getValue()).getSingers();

                        System.out.println("\n\uF0B2 The " + '"' + spectacle.getValue().getName() + '"' +
                                "'s singers list \uF0B2");

                        if (sSingers.isEmpty()) System.out.println("There are no singers as of yet. \n");
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

    public void searchSinger() {
        System.out.println("\uF0B2 The theater's singers \uF0B2");

        Map<Integer, Singer> singers = theaterService.getSingers();

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
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the singer that you want to search the spectacles for: ");
                sSingerId = in.nextLine().trim();

                if (sSingerId.compareTo("1") >= 0 && sSingerId.compareTo(Integer.toString(singers.size())) <= 0)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
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
        }
    }

    public boolean add(Singer singer, List<Singer> singers) {
        for (Singer s : singers)
            if (s.getName().equalsIgnoreCase(singer.getName()))
                return false;
        return true;
    }
    public boolean add(Singer singer, Map<Integer, Singer> singers) {
        for (Map.Entry<Integer, Singer> s : singers.entrySet())
            if (s.getValue().getName().equalsIgnoreCase(singer.getName()))
                return false;
        return true;
    }
}
