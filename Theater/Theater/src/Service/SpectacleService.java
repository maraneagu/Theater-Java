package Service;

import Theater.Spectacle.*;
import Theater.Category;
import Theater.Director;

import java.util.Scanner;
import java.util.List;

public class SpectacleService {
    private final TheaterService theaterService;

    public SpectacleService() {
        theaterService = TheaterService.getInstance();
    }

    public void addSpectacle() {
        Scanner in = new Scanner(System.in);

        Spectacle spectacle;
        List<Spectacle> spectacles = theaterService.getSpectacles();
        List<Category> categories = theaterService.getCategories();
        List<Director> directors = theaterService.getDirectors();

        System.out.println("What type of theater spectacle do you want to add?");
        System.out.println("1. Play");
        System.out.println("2. Opera");
        System.out.println("3. Musical");
        System.out.println("4. Ballet");

        int choiceOfSpectacles;
        while (true)
        {
            System.out.print("Type of spectacle: ");

            choiceOfSpectacles = in.nextInt();
            in.nextLine();

            if (choiceOfSpectacles == 1) {
                spectacle = new Play();
                break;
            }
            else if (choiceOfSpectacles == 2) {
                spectacle = new Opera();
                break;
            }
            else if (choiceOfSpectacles == 3) {
                spectacle = new Musical();
                break;
            }
            else if (choiceOfSpectacles == 4) {
                spectacle = new Ballet();
                break;
            }
            System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
        }

        System.out.println();
        spectacle.toRead();

        if (spectacle instanceof Play)
        {
            int i, categoryId;

            System.out.println("\uF0B2 The theater's categories \uF0B2");
            for (i = 0; i < categories.size(); i++)
                System.out.println(i + 1 + ". " + categories.get(i).getName());
            System.out.println();

            while (true)
            {
                System.out.print("Enter the number of the category " +
                        "for your spectacle: ");
                categoryId = Integer.parseInt(in.nextLine());

                if (categoryId >= 1 && categoryId <= categories.size())
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            System.out.println();
            ((Play) spectacle).setCategory(categories.get(categoryId - 1));
        }

        if (add(spectacle, spectacles))
        {
            spectacles.add(spectacle);
            System.out.println("\uF0B2 A new spectacle was added to the theater's spectacles list!");

            if (add(spectacle.getDirector(), directors))
            {
                directors.add(spectacle.getDirector());
                System.out.println("\uF0B2 A new director was added to the theater's categories list!");
            }
            else System.out.println("\uF04A The director you entered already exists in the theater's directors list!");
        }
        else System.out.println("\uF04A The spectacle you entered already exists in the theater's spectacles list!");

        System.out.println();
    }

    public void removeSpectacle() {
        System.out.println("\uF0B2 The theater's spectacles \uF0B2");

        List<Spectacle> spectacles = theaterService.getSpectacles();

        if (spectacles.size() == 0)
            System.out.println("There are no spectacles as of yet." + '\n');
        else
        {
            int i, spectacleId;

            for (i = 0; i < spectacles.size(); i++)
                System.out.println(i + 1 + ". " +
                        '"' + spectacles.get(i).getName() + '"');
            System.out.println();

            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the spectacle you want to remove: ");
                spectacleId = Integer.parseInt(in.nextLine());

                if (spectacleId >= 1 && spectacleId <= spectacles.size())
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            EventService eventService = new EventService();
            eventService.removeEventBySpectacle(spectacles.get(spectacleId - 1));

            for (i = 0; i < spectacles.size(); i++)
                if (i == spectacleId - 1)
                {
                    if (spectacles.get(i) instanceof Play) {
                        System.out.print("\uF0B2 The play ");
                    } else if (spectacles.get(i) instanceof Opera) {
                        System.out.print("\uF0B2 The opera ");
                    } else if (spectacles.get(i) instanceof Musical) {
                        System.out.print("\uF0B2 The musical ");
                    } else if (spectacles.get(i) instanceof Ballet) {
                        System.out.print("\uF0B2 The ballet ");
                    }
                    System.out.println('"' + spectacles.get(i).getName() + '"' + " was removed from the theater's spectacles list!" + '\n');

                    spectacles.remove(i);
                    break;
                }
        }
    }

    public void listSpectacles() {
        Scanner in = new Scanner(System.in);

        int i, choiceOfSpectacles;
        boolean spectacleListed = false;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        System.out.println("Which type of spectacles do you want to list? ");
        System.out.println("1. Plays");
        System.out.println("2. Operas");
        System.out.println("3. Musicals");
        System.out.println("4. Ballets");
        System.out.println("5. All of the above.");

        while (true) {
            System.out.print("Type of spectacles: ");

            choiceOfSpectacles = in.nextInt();
            in.nextLine();

            if (choiceOfSpectacles == 1)
            {
                System.out.println("\n\uF0B2 The theater's plays \uF0B2");

                for (i = 0; i < spectacles.size(); i++)
                    if (spectacles.get(i) instanceof Play)
                    {
                        spectacleListed = true;
                        System.out.println("\uF09F " + '"' + spectacles.get(i).getName() + '"' + " by " + spectacles.get(i).getDirector().getName());
                    }

                if (spectacleListed) System.out.println();
                else System.out.println("There are no plays as of yet. \n");
                break;
            }
            else if (choiceOfSpectacles == 2)
            {
                System.out.println("\n\uF0B2 The theater's operas \uF0B2");

                for (i = 0; i < spectacles.size(); i++)
                    if (spectacles.get(i) instanceof Opera)
                    {
                        spectacleListed = true;
                        System.out.println("\uF09F " + '"' + spectacles.get(i).getName() + '"' + " by " + spectacles.get(i).getDirector().getName());
                    }

                if (spectacleListed) System.out.println();
                else System.out.println("There are no operas as of yet. \n");
                break;
            }
            else if (choiceOfSpectacles == 3)
            {
                System.out.println("\n\uF0B2 The theater's musicals \uF0B2");

                for (i = 0; i < spectacles.size(); i++)
                    if (spectacles.get(i) instanceof Musical)
                    {
                        spectacleListed = true;
                        System.out.println("\uF09F " + '"' + spectacles.get(i).getName() + '"' + " by " + spectacles.get(i).getDirector().getName());
                    }

                if (spectacleListed) System.out.println();
                else System.out.println("There are no musicals as of yet. \n");
                break;
            }
            else if (choiceOfSpectacles == 4)
            {
                System.out.println("\n\uF0B2 The theater's ballets \uF0B2");

                for (i = 0; i < spectacles.size(); i++)
                    if (spectacles.get(i) instanceof Ballet)
                    {
                        spectacleListed = true;
                        System.out.println("\uF09F " + '"' + spectacles.get(i).getName() + '"' + " by " + spectacles.get(i).getDirector().getName());
                    }

                if (spectacleListed) System.out.println();
                else System.out.println("There are no ballets as of yet. \n");
                break;
            }
            else if (choiceOfSpectacles == 5)
            {
                System.out.println("\n\uF0B2 The theater's spectacles \uF0B2");

                if (spectacles.size() == 0)
                    System.out.println("There are no spectacles as of yet. \n");
                else
                {
                    for (Spectacle spectacle : spectacles)
                        System.out.println("\uF09F " + '"' + spectacle.getName() + '"' + " by " + spectacle.getDirector().getName());
                    System.out.println();
                }
                break;
            }
            System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
        }
    }

    public void listSpectacleDetails() {
        System.out.println("\uF0B2 The theater's spectacles \uF0B2");

        List<Spectacle> spectacles = theaterService.getSpectacles();

        if (spectacles.size() == 0)
            System.out.println("There are no spectacles as of yet." + '\n');
        else
        {
            int i, spectacleId;

            for (i = 0; i < spectacles.size(); i++)
                System.out.println(i + 1 + ". " + '"' + spectacles.get(i).getName() + '"');
            System.out.println();

            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the spectacle you want to list the information for: ");
                spectacleId = Integer.parseInt(in.nextLine());

                if (spectacleId >= 1 && spectacleId <= spectacles.size())
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            System.out.println();
            System.out.println(spectacles.get(spectacleId - 1));
        }
    }

    public void listSpectacleByCategory() {
        System.out.println("\uF0B2 The theater's categories \uF0B2");

        List<Category> categories = theaterService.getCategories();

        int i, categoryId;

        for (i = 0; i < categories.size(); i++)
            System.out.println(i + 1 + ". " + '"' + categories.get(i).getName() + '"');
        System.out.println();

        while (true)
        {
            Scanner in = new Scanner(System.in);

            System.out.print("Enter the number of the category by which you want to list the spectacles: ");
            categoryId = Integer.parseInt(in.nextLine());

            if (categoryId >= 1 && categoryId <= categories.size())
                break;
            System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
        }

        System.out.println();
        System.out.println("\uF0B2 The " + '"' + categories.get(categoryId - 1).getName() + '"' + "'s spectacles \uF0B2");

        boolean spectacleExists = false;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        for (i = 0; i < spectacles.size(); i++)
            if (spectacles.get(i) instanceof Play)
                if (((Play)spectacles.get(i)).getCategory().getName().equalsIgnoreCase(categories.get(categoryId - 1).getName()))
                {
                    spectacleExists = true;
                    System.out.println("\uF09F " + '"' + spectacles.get(i).getName() + '"' + " directed by " + spectacles.get(i).getDirector().getName());
                }

        if (!spectacleExists) System.out.println("There are no spectacles in the " + '"' +
                 categories.get(categoryId - 1).getName() + '"' + " category as of yet.");
        System.out.println();
    }

    public boolean add(Spectacle spectacle, List<Spectacle> spectacles) {
        if (spectacle instanceof Play) {
            for (Spectacle s : spectacles)
                if (s instanceof Play)
                    if (s.getName().equalsIgnoreCase(spectacle.getName()))
                        return false;
            return true;
        }
        else if (spectacle instanceof Opera) {
            for (Spectacle s : spectacles)
                if (s instanceof Opera)
                    if (s.getName().equalsIgnoreCase(spectacle.getName()))
                        return false;
            return true;
        }
        else if (spectacle instanceof Musical) {
            for (Spectacle s : spectacles)
                if (s instanceof Musical)
                    if (s.getName().equalsIgnoreCase(spectacle.getName()))
                        return false;
            return true;
        }
        else {
            for (Spectacle s : spectacles)
                if (s instanceof Ballet)
                    if (s.getName().equalsIgnoreCase(spectacle.getName()))
                        return false;
            return true;
        }
    }
    public boolean add(Director director, List<Director> directors) {
        for (Director d : directors)
            if (d.getName().equalsIgnoreCase(director.getName()))
                return false;
        return true;
    }
}
