package Service.ArtistService;

import Service.TheaterService;
import Theater.Artist.Actor;
import Theater.Spectacle.Play;
import Theater.Spectacle.Spectacle;

import java.util.List;
import java.util.Scanner;

public class ActorService {
    private final TheaterService theaterService;
    public ActorService() {
        theaterService = TheaterService.getInstance();
    }

    public void addActor() {
        System.out.println("\uF0B2 The theater's plays \uF0B2");

        int i, spectacleId, playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        for (i = 0; i < spectacles.size(); i++)
            if (spectacles.get(i) instanceof Play)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacles.get(i).getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no plays as of yet." + '\n');
        else
        {
            List<Actor> actors = theaterService.getActors();

            System.out.println();
            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the play where you want to add a new actor: ");
                spectacleId = Integer.parseInt(in.nextLine().trim());

                if (spectacleId >= 1 && spectacleId <= playId)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            Actor actor = new Actor();
            actor.toRead();

            playId = 0;
            for (i = 0; i < spectacles.size(); i++)
                if (spectacles.get(i) instanceof Play)
                {
                    playId++;
                    if (playId == spectacleId)
                    {
                        if (add(actor, ((Play) spectacles.get(i)).getActors()))
                        {
                            List<Actor> sActors = ((Play) spectacles.get(i)).getActors();
                            sActors.add(actor);

                            System.out.println("\uF0B2 A new actor was added to " + '"' + spectacles.get(i).getName() + '"' + "'s actors list!");
                        }
                        else System.out.println("\uF04A The actor you entered already exists in " + '"' +
                                spectacles.get(i).getName() + '"' + "'s actors list!");

                        if (add(actor, actors))
                        {
                            actors.add(actor);
                            System.out.println("\uF0B2 A new actor was added to theater's actors list!");
                        }
                        else System.out.println("\uF04A The actor you entered already exists in theater's actors list!");

                        System.out.println();
                        break;
                    }
                }
        }
    }

    public void removeActor() {
        System.out.println("\uF0B2 The theater's plays \uF0B2");

        int i, spectacleId, playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        for (i = 0; i < spectacles.size(); i++)
            if (spectacles.get(i) instanceof Play)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacles.get(i).getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no plays as of yet." + '\n');
        else
        {
            System.out.println();
            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the play where you want to remove an actor: ");
                spectacleId = Integer.parseInt(in.nextLine().trim());

                if (spectacleId >= 1 && spectacleId <= playId)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            playId = 0;
            for (i = 0; i < spectacles.size(); i++)
                if (spectacles.get(i) instanceof Play)
                {
                    playId++;
                    if (playId == spectacleId)
                    {
                        int j, actorId;
                        List<Actor> sActors = ((Play) spectacles.get(i)).getActors();

                        System.out.println("\n\uF0B2 The " + '"' + spectacles.get(i).getName() + '"' +
                                "'s actors list \uF0B2");

                        if (sActors.size() == 0) System.out.println("There are no actors as of yet.\n");
                        else
                        {
                            for (j = 0; j < sActors.size(); j++)
                                System.out.println(j + 1 + ". " + sActors.get(j).getName());

                            System.out.println();
                            while (true)
                            {
                                Scanner in = new Scanner(System.in);

                                System.out.print("Enter the number of the actor you want to remove: ");
                                actorId = Integer.parseInt(in.nextLine().trim());

                                if (actorId >= 1 && actorId <= sActors.size())
                                    break;
                                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
                            }

                            System.out.println("\uF0B2 The actor " + '"' + sActors.get(actorId - 1).getName() + '"' +
                                    " was removed from " + '"' + spectacles.get(i).getName() + '"'
                                    + "'s actors list! \n");
                            sActors.remove(actorId - 1);
                            break;
                        }
                    }
                }
        }
    }

    public void listActors() {
        System.out.println("\uF0B2 The theater's plays \uF0B2");

        int i, spectacleId, playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        for (i = 0; i < spectacles.size(); i++)
            if (spectacles.get(i) instanceof Play)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacles.get(i).getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no plays as of yet.\n");
        else
        {
            System.out.println();
            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the play where you want to list the actors: ");
                spectacleId = Integer.parseInt(in.nextLine().trim());

                if (spectacleId >= 1 && spectacleId <= playId)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            playId = 0;
            for (i = 0; i < spectacles.size(); i++)
                if (spectacles.get(i) instanceof Play)
                {
                    playId++;
                    if (playId == spectacleId)
                    {
                        List<Actor> sActors = ((Play) spectacles.get(i)).getActors();

                        System.out.println("\n\uF0B2 The " + '"' + spectacles.get(i).getName() + '"' +
                                "'s actors list \uF0B2");

                        if (sActors.size() == 0) System.out.println("There are no actors as of yet.\n");
                        else
                        {
                            for (Actor actor : sActors)
                                System.out.println(actor);
                            System.out.println();
                        }
                    }
                }
        }
    }

    public boolean add(Actor actor, List<Actor> actors) {
        for (Actor a : actors)
            if (a.getName().equalsIgnoreCase(actor.getName()))
                return false;
        return true;
    }
}
