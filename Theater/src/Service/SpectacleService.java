package Service;

import Audit.Audit;
import Repository.*;
import Repository.SpectacleRepository;
import Theater.Spectacle.*;
import Theater.Category;
import Theater.Director;
import Exception.InvalidNumberException;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SpectacleService {
    private final TheaterService theaterService;

    public SpectacleService() {
        theaterService = TheaterService.getInstance();
    }

    public void addSpectacle()
    {
        Scanner in = new Scanner(System.in);

        Spectacle spectacle;
        List<Spectacle> spectacles = theaterService.getSpectacles();
        Map<Integer, Category> categories = theaterService.getCategories();
        Map<Integer, Director> directors = theaterService.getDirectors();

        System.out.println("What type of theater spectacle do you want to add?");
        System.out.println("1. Play");
        System.out.println("2. Opera");
        System.out.println("3. Musical");
        System.out.println("4. Ballet");

        String choiceOfSpectacles;
        while (true)
        {
            try
            {
                System.out.print("Type of spectacle: ");

                choiceOfSpectacles = in.nextLine().trim();

                if (choiceOfSpectacles.equals("1"))
                {
                    spectacle = new Play();
                    break;
                }
                else if (choiceOfSpectacles.equals("2"))
                {
                    spectacle = new Opera();
                    break;
                }
                else if (choiceOfSpectacles.equals("3"))
                {
                    spectacle = new Musical();
                    break;
                }
                else if (choiceOfSpectacles.equals("4"))
                {
                    spectacle = new Ballet();
                    break;
                }
                else throw new InvalidNumberException();
            }
            catch (InvalidNumberException exception)
            {
                System.out.println(exception.getMessage());
            }
        }

        System.out.println();
        spectacle.toRead();

        if (spectacle instanceof Play)
        {
            String sCategoryId;
            int categoryId;

            System.out.println("\uF0B2 The theater's categories \uF0B2");

            for (Map.Entry<Integer, Category> category : categories.entrySet())
                System.out.println(category.getKey() + ". " + category.getValue().getName());
            System.out.println();

            while (true)
            {
                try
                {
                    System.out.print("Enter the number of the category " +
                            "for your spectacle: ");
                    sCategoryId = in.nextLine().trim();

                    if (sCategoryId.compareTo("1") >= 0 && sCategoryId.compareTo(Integer.toString(categories.size())) <= 0)
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            System.out.println();
            categoryId = Integer.parseInt(sCategoryId);
            ((Play) spectacle).setCategory(categories.get(categoryId));
        }

        if (add(spectacle, spectacles))
        {
            if (add(spectacle.getDirector(), directors))
            {
                DirectorRepository directorRepository = DirectorRepository.getInstance();
                directorRepository.insertDirector(spectacle.getDirector());
                directors.put(theaterService.getDirectors().size() + 1, spectacle.getDirector());

                Audit audit = Audit.getInstance();
                audit.writeToFile("A new director was added to the theater's directors list: " + spectacle.getDirector().getName(), "./theaterDirectors.cvs");

                System.out.println("\uF0B2 A new director was added to the theater's directors list!");
            }
            else System.out.println("\uF04A The director you entered already exists in the theater's directors list!");

            SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();
            spectacleRepository.insertSpectacle(spectacle);

            Audit audit = Audit.getInstance();

            if (spectacle instanceof Play)
            {
                audit.writeToFile("A new play was added to the theater's spectacles list: " + spectacle.getName(), "./theaterSpectacles.cvs");
                System.out.println("\uF0B2 A new play was added to the theater's spectacles list!\n");
            }
            else if (spectacle instanceof Opera)
            {
                audit.writeToFile("A new opera was added to the theater's spectacles list: " + spectacle.getName(), "./theaterSpectacles.cvs");
                System.out.println("\uF0B2 A new opera was added to the theater's spectacles list!\n");
            }
            else if (spectacle instanceof Musical)
            {
                audit.writeToFile("A new musical was added to the theater's spectacles list: " + spectacle.getName(), "./theaterSpectacles.cvs");
                System.out.println("\uF0B2 A new musical was added to the theater's spectacles list!\n");
            }
            else
            {
                audit.writeToFile("A new ballet was added to the theater's spectacles list: " + spectacle.getName(), "./theaterSpectacles.cvs");
                System.out.println("\uF0B2 A new ballet was added to the theater's spectacles list!\n");
            }
            spectacles.add(spectacle);
        }
        else System.out.println("\uF04A The spectacle you entered already exists in the theater's spectacles list!\n");
    }

    public void removeSpectacle()
    {
        List<Spectacle> spectacles = theaterService.getSpectacles();

        System.out.println("\uF0B2 The theater's spectacles \uF0B2");

        if (spectacles.isEmpty())
            System.out.println("There are no spectacles as of yet." + '\n');
        else
        {
            String sSpectacleId;
            int spectacleId = 1;
            String spectacleType = "";

            for (Spectacle spectacle : spectacles)
            {
                if (spectacle instanceof Play)
                    spectacleType = "play";
                else if (spectacle instanceof Opera)
                    spectacleType = "opera";
                else if (spectacle instanceof Musical)
                    spectacleType = "musical";
                else spectacleType = "ballet";

                System.out.println(spectacleId + ". " +
                        '"' + spectacle.getName() + '"' + " \uF09E " + spectacleType);
                spectacleId += 1;
            }
            System.out.println();

            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the spectacle you want to remove: ");
                    sSpectacleId = in.nextLine().trim();

                    if (sSpectacleId.compareTo("1") >= 0 && sSpectacleId.compareTo(Integer.toString(spectacles.size())) <= 0)
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            spectacleId = Integer.parseInt(sSpectacleId);
            EventService eventService = new EventService();
            eventService.removeEventBySpectacle(spectacles.get(spectacleId - 1));

            System.out.println("\n\uF04A The " + spectacleType + " "
                    + '"' + spectacles.get(spectacleId - 1).getName() + '"' + " was removed from the theater's spectacles list!" + '\n');

            Audit audit = Audit.getInstance();
            audit.writeToFile("The " + spectacleType + " "
                            + '"' + spectacles.get(spectacleId - 1).getName() + '"' + " was removed from the theater's spectacles list!", "./theaterSpectacles.cvs");

            SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();
            spectacleRepository.deleteSpectacle(spectacles.get(spectacleId - 1));

            spectacles.remove(spectacleId - 1);
        }
    }

    public void listSpectacles()
    {
        Scanner in = new Scanner(System.in);

        String choiceOfSpectacles;
        boolean spectacleListed = false;
        List<Spectacle> spectacles = theaterService.getSpectacles();
        Audit audit = Audit.getInstance();

        System.out.println("Which type of spectacles do you want to list? ");
        System.out.println("1. Plays");
        System.out.println("2. Operas");
        System.out.println("3. Musicals");
        System.out.println("4. Ballets");
        System.out.println("5. All of the above");

        while (true)
        {
            try
            {
                System.out.print("Type of spectacles: ");

                choiceOfSpectacles = in.nextLine().trim();

                if (choiceOfSpectacles.equals("1"))
                {
                    System.out.println("\n\uF0B2 The theater's plays \uF0B2");

                    for (Spectacle spectacle : spectacles)
                        if (spectacle instanceof Play)
                        {
                            spectacleListed = true;
                            System.out.println("\uF09F " + '"' + spectacle.getName() + '"' + " by " + spectacle.getDirector().getName());
                        }

                    if (spectacleListed) System.out.println();
                    else System.out.println("There are no plays as of yet. \n");
                    audit.writeToFile("The theater's plays were listed!", "./theaterSpectacles.cvs");

                    break;
                }
                else if (choiceOfSpectacles.equals("2"))
                {
                    System.out.println("\n\uF0B2 The theater's operas \uF0B2");

                    for (Spectacle spectacle : spectacles)
                        if (spectacle instanceof Opera)
                        {
                            spectacleListed = true;
                            System.out.println("\uF09F " + '"' + spectacle.getName() + '"' + " by " + spectacle.getDirector().getName());
                        }

                    if (spectacleListed) System.out.println();
                    else System.out.println("There are no operas as of yet. \n");
                    audit.writeToFile("The theater's operas were listed!", "./theaterSpectacles.cvs");

                    break;
                }
                else if (choiceOfSpectacles.equals("3"))
                {
                    System.out.println("\n\uF0B2 The theater's musicals \uF0B2");

                    for (Spectacle spectacle : spectacles)
                        if (spectacle instanceof Musical)
                        {
                            spectacleListed = true;
                            System.out.println("\uF09F " + '"' + spectacle.getName() + '"' + " by " + spectacle.getDirector().getName());
                        }

                    if (spectacleListed) System.out.println();
                    else System.out.println("There are no musicals as of yet. \n");
                    audit.writeToFile("The theater's musicals were listed!", "./theaterSpectacles.cvs");

                    break;
                }
                else if (choiceOfSpectacles.equals("4"))
                {
                    System.out.println("\n\uF0B2 The theater's ballets \uF0B2");

                    for (Spectacle spectacle : spectacles)
                        if (spectacle instanceof Ballet)
                        {
                            spectacleListed = true;
                            System.out.println("\uF09F " + '"' + spectacle.getName() + '"' + " by " + spectacle.getDirector().getName());
                        }

                    if (spectacleListed) System.out.println();
                    else System.out.println("There are no ballets as of yet. \n");
                    audit.writeToFile("The theater's ballets were listed!", "./theaterSpectacles.cvs");

                    break;
                }
                else if (choiceOfSpectacles.equals("5"))
                {
                    System.out.println("\n\uF0B2 The theater's spectacles \uF0B2");

                    if (spectacles.isEmpty())
                        System.out.println("There are no spectacles as of yet. \n");
                    else
                    {
                        for (Spectacle spectacle : spectacles)
                            System.out.println("\uF09F " + '"' + spectacle.getName() + '"' + " by " + spectacle.getDirector().getName());
                        System.out.println();
                    }
                    audit.writeToFile("The theater's spectacles were listed!", "./theaterSpectacles.cvs");

                    break;
                }
                else throw new InvalidNumberException();
            }
            catch (InvalidNumberException exception)
            {
                System.out.println(exception.getMessage());
            }
        }
    }

    public void listSpectacleDetails()
    {
        List<Spectacle> spectacles = theaterService.getSpectacles();

        System.out.println("\uF0B2 The theater's spectacles \uF0B2");

        if (spectacles.isEmpty())
            System.out.println("There are no spectacles as of yet." + '\n');
        else
        {
            String sSpectacleId;
            int spectacleId = 1;

            for (Spectacle spectacle : spectacles)
            {
                System.out.println(spectacleId + ". " + '"' + spectacle.getName() + '"');
                spectacleId += 1;
            }
            System.out.println();

            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the spectacle you want to list the information for: ");
                    sSpectacleId = in.nextLine().trim();

                    if (sSpectacleId.compareTo("1") >= 0 && sSpectacleId.compareTo(Integer.toString(spectacles.size())) <= 0)
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            System.out.println("\n" + spectacles.get(Integer.parseInt(sSpectacleId) - 1));

            Audit audit = Audit.getInstance();
            audit.writeToFile("The details about a spectacle were listed!", "./theaterSpectacles.cvs");
        }
    }

    public void listSpectacleByCategory()
    {
        Map<Integer, Category> categories = theaterService.getCategories();

        System.out.println("\uF0B2 The theater's categories \uF0B2");

        String sCategoryId;
        int categoryId;

        for (Map.Entry<Integer, Category> category : categories.entrySet())
            System.out.println(category.getKey() + ". " + category.getValue().getName());
        System.out.println();

        while (true)
        {
            try
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the category by which you want to list the spectacles: ");
                sCategoryId = in.nextLine().trim();

                if (sCategoryId.compareTo("1") >= 0 && sCategoryId.compareTo(Integer.toString(categories.size())) <= 0)
                    break;
                else throw new InvalidNumberException();
            }
            catch (InvalidNumberException exception)
            {
                System.out.println(exception.getMessage());
            }
        }

        categoryId = Integer.parseInt(sCategoryId);
        System.out.println("\n\uF0B2 The " + categories.get(categoryId).getName() + " spectacles \uF0B2");

        boolean spectacleExists = false;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        for (Spectacle spectacle : spectacles)
            if (spectacle instanceof Play)
                if (((Play) spectacle).getCategory().getName().equalsIgnoreCase(categories.get(categoryId).getName()))
                {
                    spectacleExists = true;
                    System.out.println("\uF09F " + '"' + spectacle.getName() + '"' + " by " + spectacle.getDirector().getName());
                }

        if (!spectacleExists) System.out.println("There are no spectacles in the " + '"' +
                 categories.get(categoryId).getName() + '"' + " category as of yet.");
        System.out.println();

        Audit audit = Audit.getInstance();
        audit.writeToFile("The " + categories.get(categoryId).getName() + " spectacles were listed", "./theaterCategories.cvs");
    }

    public boolean add(Spectacle spectacle, List<Spectacle> spectacles)
    {
        if (spectacle instanceof Play)
        {
            for (Spectacle s : spectacles)
                if (s instanceof Play) {
                    if (s.getName().equalsIgnoreCase(spectacle.getName()))
                        return false;
                }
            return true;
        }
        else if (spectacle instanceof Opera)
        {
            for (Spectacle s : spectacles)
                if (s instanceof Opera)
                    if (s.getName().equalsIgnoreCase(spectacle.getName()))
                        return false;
            return true;
        }
        else if (spectacle instanceof Musical)
        {
            for (Spectacle s : spectacles)
                if (s instanceof Musical)
                    if (s.getName().equalsIgnoreCase(spectacle.getName()))
                        return false;
            return true;
        }
        else
        {
            for (Spectacle s : spectacles)
                if (s instanceof Ballet)
                    if (s.getName().equalsIgnoreCase(spectacle.getName()))
                        return false;
            return true;
        }
    }

    public boolean add(Director director, Map<Integer, Director> directors)
    {
        for (Map.Entry<Integer, Director> d : directors.entrySet())
            if (d.getValue().getName().equalsIgnoreCase(director.getName()))
                return false;
        return true;
    }
}
