package Service;

import Theater.Spectacle.*;
import Theater.Category;
import Theater.Director;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SpectacleService {
    private final TheaterService theaterService;

    public SpectacleService() {
        theaterService = TheaterService.getInstance();
    }

    public void addSpectacle() {
        Scanner in = new Scanner(System.in);

        Spectacle spectacle;
        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();
        Map<Integer, Category> categories = theaterService.getCategories();
        Map<Integer, Director> directors = theaterService.getDirectors();

        System.out.println("What type of theater spectacle do you want to add?");
        System.out.println("1. Play");
        System.out.println("2. Opera");
        System.out.println("3. Musical");
        System.out.println("4. Ballet");

        int choiceOfSpectacles;
        while (true)
        {
            System.out.print("Type of spectacle: ");

            choiceOfSpectacles = Integer.parseInt(in.nextLine().trim());

            if (choiceOfSpectacles == 1)
            {
                spectacle = new Play();
                break;
            }
            else if (choiceOfSpectacles == 2)
            {
                spectacle = new Opera();
                break;
            }
            else if (choiceOfSpectacles == 3)
            {
                spectacle = new Musical();
                break;
            }
            else if (choiceOfSpectacles == 4)
            {
                spectacle = new Ballet();
                break;
            }
            System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
        }

        System.out.println();
        spectacle.toRead();

        if (spectacle instanceof Play)
        {
            int categoryId;

            System.out.println("\uF0B2 The theater's categories \uF0B2");

            for (Map.Entry<Integer, Category> category : categories.entrySet())
                System.out.println(category.getKey() + ". " + category.getValue().getName());
            System.out.println();

            while (true)
            {
                System.out.print("Enter the number of the category " +
                        "for your spectacle: ");
                categoryId = Integer.parseInt(in.nextLine().trim());

                if (categoryId >= 1 && categoryId <= categories.size())
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            System.out.println();
            ((Play) spectacle).setCategory(categories.get(categoryId));
        }

        if (add(spectacle, spectacles))
        {
            theaterService.setSpectacleId(theaterService.getSpectacleId() + 1);
            spectacles.put(theaterService.getSpectacleId(), spectacle);
            System.out.println("\uF0B2 A new spectacle was added to the theater's spectacles list!");

            if (add(spectacle.getDirector(), directors))
            {
                theaterService.setDirectorId(theaterService.getDirectorId() + 1);
                directors.put(theaterService.getSpectacleId(), spectacle.getDirector());
                System.out.println("\uF0B2 A new director was added to the theater's directors list!");
            }
            else System.out.println("\uF04A The director you entered already exists in the theater's directors list!");
        }
        else System.out.println("\uF04A The spectacle you entered already exists in the theater's spectacles list!");

        System.out.println();
    }

    public void removeSpectacle() {
        System.out.println("\uF0B2 The theater's spectacles \uF0B2");

        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();

        if (spectacles.isEmpty())
            System.out.println("There are no spectacles as of yet." + '\n');
        else
        {
            int spectacleId;

            for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                System.out.println(spectacle.getKey() + ". " +
                        '"' + spectacle.getValue().getName() + '"');
            System.out.println();

            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the spectacle you want to remove: ");
                spectacleId = Integer.parseInt(in.nextLine().trim());

                if (spectacleId >= 1 && spectacleId <= spectacles.size())
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            EventService eventService = new EventService();
            eventService.removeEventBySpectacle(spectacles.get(spectacleId));

            System.out.println("\n\uF04A The spectacle " + '"' + spectacles.get(spectacleId).getName() + '"' + " was removed from the theater's spectacles list!" + '\n');
            spectacles.remove(spectacleId);

            Map<Integer, Spectacle> newSpectacles = new HashMap<>();
            for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
            {
                if (spectacle.getKey() < spectacleId)
                    newSpectacles.put(spectacle.getKey(), spectacle.getValue());
                else if (spectacle.getKey() > spectacleId)
                    newSpectacles.put(spectacle.getKey() - 1, spectacle.getValue());
            }
            theaterService.setSpectacles(newSpectacles);
        }
    }

    public void listSpectacles() {
        Scanner in = new Scanner(System.in);

        int choiceOfSpectacles;
        boolean spectacleListed = false;
        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();

        System.out.println("Which type of spectacles do you want to list? ");
        System.out.println("1. Plays");
        System.out.println("2. Operas");
        System.out.println("3. Musicals");
        System.out.println("4. Ballets");
        System.out.println("5. All of the above");

        while (true)
        {
            System.out.print("Type of spectacles: ");

            choiceOfSpectacles = Integer.parseInt(in.nextLine().trim());

            if (choiceOfSpectacles == 1)
            {
                System.out.println("\n\uF0B2 The theater's plays \uF0B2");

                for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                    if (spectacle.getValue() instanceof Play)
                    {
                        spectacleListed = true;
                        System.out.println("\uF09F " + '"' + spectacle.getValue().getName() + '"' + " by " + spectacle.getValue().getDirector().getName());
                    }

                if (spectacleListed) System.out.println();
                else System.out.println("There are no plays as of yet. \n");
                break;
            }
            else if (choiceOfSpectacles == 2)
            {
                System.out.println("\n\uF0B2 The theater's operas \uF0B2");

                for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                    if (spectacle.getValue() instanceof Opera)
                    {
                        spectacleListed = true;
                        System.out.println("\uF09F " + '"' + spectacle.getValue().getName() + '"' + " by " + spectacle.getValue().getDirector().getName());
                    }

                if (spectacleListed) System.out.println();
                else System.out.println("There are no operas as of yet. \n");
                break;
            }
            else if (choiceOfSpectacles == 3)
            {
                System.out.println("\n\uF0B2 The theater's musicals \uF0B2");

                for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                    if (spectacle.getValue() instanceof Musical)
                    {
                        spectacleListed = true;
                        System.out.println("\uF09F " + '"' + spectacle.getValue().getName() + '"' + " by " + spectacle.getValue().getDirector().getName());
                    }

                if (spectacleListed) System.out.println();
                else System.out.println("There are no musicals as of yet. \n");
                break;
            }
            else if (choiceOfSpectacles == 4)
            {
                System.out.println("\n\uF0B2 The theater's ballets \uF0B2");

                for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                    if (spectacle.getValue() instanceof Ballet)
                    {
                        spectacleListed = true;
                        System.out.println("\uF09F " + '"' + spectacle.getValue().getName() + '"' + " by " + spectacle.getValue().getDirector().getName());
                    }

                if (spectacleListed) System.out.println();
                else System.out.println("There are no ballets as of yet. \n");
                break;
            }
            else if (choiceOfSpectacles == 5)
            {
                System.out.println("\n\uF0B2 The theater's spectacles \uF0B2");

                if (spectacles.isEmpty())
                    System.out.println("There are no spectacles as of yet. \n");
                else
                {
                    for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                        System.out.println("\uF09F " + '"' + spectacle.getValue().getName() + '"' + " by " + spectacle.getValue().getDirector().getName());
                    System.out.println();
                }
                break;
            }
            System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
        }
    }

    public void listSpectacleDetails() {
        System.out.println("\uF0B2 The theater's spectacles \uF0B2");

        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();

        if (spectacles.isEmpty())
            System.out.println("There are no spectacles as of yet." + '\n');
        else
        {
            int spectacleId;

            for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                System.out.println(spectacle.getKey() + ". " + '"' + spectacle.getValue().getName() + '"');
            System.out.println();

            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the spectacle you want to list the information for: ");
                spectacleId = Integer.parseInt(in.nextLine().trim());

                if (spectacleId >= 1 && spectacleId <= spectacles.size())
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            System.out.println();
            System.out.println(spectacles.get(spectacleId));
        }
    }

    public void listSpectacleByCategory() {
        System.out.println("\uF0B2 The theater's categories \uF0B2");

        Map<Integer, Category> categories = theaterService.getCategories();

        int categoryId;

        for (Map.Entry<Integer, Category> category : categories.entrySet())
            System.out.println(category.getKey() + ". " + category.getValue().getName());
        System.out.println();

        while (true)
        {
            Scanner in = new Scanner(System.in);

            System.out.print("Enter the number of the category by which you want to list the spectacles: ");
            categoryId = Integer.parseInt(in.nextLine().trim());

            if (categoryId >= 1 && categoryId <= categories.size())
                break;
            System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
        }

        System.out.println();
        System.out.println("\uF0B2 The " + categories.get(categoryId).getName() + " spectacles \uF0B2");

        boolean spectacleExists = false;
        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();

        for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
            if (spectacle.getValue() instanceof Play)
                if (((Play) spectacle.getValue()).getCategory().getName().equalsIgnoreCase(categories.get(categoryId).getName()))
                {
                    spectacleExists = true;
                    System.out.println("\uF09F " + '"' + spectacle.getValue().getName() + '"' + " by " + spectacle.getValue().getDirector().getName());
                }

        if (!spectacleExists) System.out.println("There are no spectacles in the " + '"' +
                 categories.get(categoryId).getName() + '"' + " category as of yet.");
        System.out.println();
    }

    public boolean add(Spectacle spectacle, Map<Integer, Spectacle> spectacles) {
        if (spectacle instanceof Play)
        {
            for (Map.Entry<Integer, Spectacle> s : spectacles.entrySet())
                if (s.getValue() instanceof Play) {
                    if (s.getValue().getName().equalsIgnoreCase(spectacle.getName()))
                        return false;
                }
            return true;
        }
        else if (spectacle instanceof Opera)
        {
            for (Map.Entry<Integer, Spectacle> s : spectacles.entrySet())
                if (s.getValue() instanceof Opera)
                    if (s.getValue().getName().equalsIgnoreCase(spectacle.getName()))
                        return false;
            return true;
        }
        else if (spectacle instanceof Musical)
        {
            for (Map.Entry<Integer, Spectacle> s : spectacles.entrySet())
                if (s.getValue() instanceof Musical)
                    if (s.getValue().getName().equalsIgnoreCase(spectacle.getName()))
                        return false;
            return true;
        }
        else
        {
            for (Map.Entry<Integer, Spectacle> s : spectacles.entrySet())
                if (s.getValue() instanceof Ballet)
                    if (s.getValue().getName().equalsIgnoreCase(spectacle.getName()))
                        return false;
            return true;
        }
    }
    public boolean add(Director director, Map<Integer, Director> directors) {
        for (Map.Entry<Integer, Director> d : directors.entrySet())
            if (d.getValue().getName().equalsIgnoreCase(director.getName()))
                return false;
        return true;
    }
}
