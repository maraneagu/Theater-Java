package Service.ArtistService;

import Service.TheaterService;
import Repository.ActorRepository;
import Theater.Artist.Actor;
import Theater.Spectacle.Play;
import Theater.Spectacle.Spectacle;
import Exception.InvalidNumberException;
import Audit.Audit;

import java.util.Map;
import java.util.List;
import java.util.Scanner;

public class ActorService
{
    private final TheaterService theaterService;
    public ActorService() {
        theaterService = TheaterService.getInstance();
    }

    public void addActor()
    {
        String spectacleId;
        int playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        System.out.println("\uF0B2 The theater's plays \uF0B2");

        for (Spectacle spectacle : spectacles)
            if (spectacle instanceof Play)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no plays as of yet." + '\n');
        else
        {
            Map<Integer, Actor> actors = theaterService.getActors();

            System.out.println();
            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the play where you want to add a new actor: ");
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

            Actor actor = new Actor();
            actor.toRead();

            playId = 0;
            for (Spectacle spectacle : spectacles)
                if (spectacle instanceof Play)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        ActorRepository actorRepository = ActorRepository.getInstance();
                        Audit audit = Audit.getInstance();

                        if (add(actor, actors))
                        {
                            actorRepository.insertActor(actor);
                            actors.put(theaterService.getActors().size() + 1, actor);

                            audit.writeToFile("A new actor was added to theater's actors list: " + actor.getName(), "./theaterArtists.cvs");
                            System.out.println("\uF0B2 A new actor was added to theater's actors list!");
                        }
                        else System.out.println("\uF04A The actor you entered already exists in theater's actors list!");

                        if (add(actor, ((Play) spectacle).getActors()))
                        {
                            actorRepository.insertPlayActor((Play) spectacle, actor);
                            List<Actor> sActors = ((Play) spectacle).getActors();
                            sActors.add(actor);
                            actor.getSpectacles().add(spectacle);

                            audit.writeToFile("A new actor was added to " + '"' + spectacle.getName() + '"' + "'s actors list: " + actor.getName(), "./theaterArtists.cvs");
                            System.out.println("\uF0B2 A new actor was added to " + '"' + spectacle.getName() + '"' + "'s actors list!");
                        }
                        else System.out.println("\uF04A The actor you entered already exists in " + '"' +
                                spectacle.getName() + '"' + "'s actors list!");

                        System.out.println();
                        break;
                    }
                }
        }
    }

    public void removeActor()
    {
        String spectacleId;
        int playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        System.out.println("\uF0B2 The theater's plays \uF0B2");

        for (Spectacle spectacle : spectacles)
            if (spectacle instanceof Play)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no plays as of yet." + '\n');
        else
        {
            System.out.println();
            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the play where you want to remove an actor: ");
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
                if (spectacle instanceof Play)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        List<Actor> sActors = ((Play) spectacle).getActors();

                        System.out.println("\n\uF0B2 The " + '"' + spectacle.getName() + '"' +
                                "'s actors list \uF0B2");

                        if (sActors.isEmpty()) System.out.println("There are no actors as of yet.\n");
                        else
                        {
                            int j, actorId;
                            String sActorId;

                            for (j = 0; j < sActors.size(); j++)
                                System.out.println(j + 1 + ". " + sActors.get(j).getName());

                            System.out.println();
                            while (true)
                            {
                                try
                                {
                                    Scanner in = new Scanner(System.in);

                                    System.out.print("Enter the number of the actor that you want to remove: ");
                                    sActorId = in.nextLine().trim();

                                    if (sActorId.compareTo("1") >= 0 && sActorId.compareTo(Integer.toString(sActors.size())) <= 0)
                                        break;
                                    else throw new InvalidNumberException();
                                }
                                catch (InvalidNumberException exception)
                                {
                                    System.out.println(exception.getMessage());
                                }
                            }

                            Audit audit = Audit.getInstance();
                            ActorRepository actorRepository = ActorRepository.getInstance();
                            actorId = Integer.parseInt(sActorId);

                            System.out.println("\n\uF04A The actor " + sActors.get(actorId - 1).getName() +
                                    " was removed from " + '"' + spectacle.getName() + '"'
                                    + "'s actors list! \n");

                            audit.writeToFile("The actor "+ sActors.get(actorId - 1).getName() + " was removed from " + '"' + spectacle.getName() + '"'
                                    + "'s actors list!", "./theaterArtists.cvs");

                            actorRepository.deletePlayActor((Play) spectacle, sActors.get(actorId - 1));
                            sActors.get(actorId - 1).getSpectacles().remove(spectacle);
                            sActors.remove(actorId - 1);
                            break;
                        }
                    }
                }
        }
    }

    public void listActors()
    {
        String spectacleId;
        int playId = 0;
        List<Spectacle> spectacles = theaterService.getSpectacles();

        System.out.println("\uF0B2 The theater's plays \uF0B2");

        for (Spectacle spectacle : spectacles)
            if (spectacle instanceof Play)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no plays as of yet.\n");
        else
        {
            System.out.println();
            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the play where you want to list the actors: ");
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
                if (spectacle instanceof Play)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        List<Actor> sActors = ((Play) spectacle).getActors();

                        System.out.println("\n\uF0B2 The " + '"' + spectacle.getName() + '"' +
                                "'s actors list \uF0B2");

                        if (sActors.isEmpty()) System.out.println("There are no actors as of yet.\n");
                        else
                        {
                            for (Actor actor : sActors)
                                System.out.println(actor);
                            System.out.println();
                        }

                        Audit audit = Audit.getInstance();
                        audit.writeToFile("The " + '"' + spectacle.getName() + '"' + "'s actors list was listed!", "./theaterArtists.cvs");
                        break;
                    }
                }
        }
    }

    public void searchActor()
    {
        Map<Integer, Actor> actors = theaterService.getActors();

        System.out.println("\uF0B2 The theater's actors \uF0B2");

        if (actors.isEmpty())
            System.out.println("There are no actors as of yet.\n");
        else
        {
            String sActorId;
            int actorId;

            for (Map.Entry<Integer, Actor> actor : actors.entrySet())
                System.out.println(actor.getKey() + ". " + actor.getValue().getName());
            System.out.println();

            while (true)
            {
                try
                {
                    Scanner in = new Scanner(System.in);

                    System.out.print("Enter the number of the actor that you want to search the spectacles for: ");
                    sActorId = in.nextLine().trim();

                    if (sActorId.compareTo("1") >= 0 && sActorId.compareTo(Integer.toString(actors.size())) <= 0)
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            actorId = Integer.parseInt(sActorId);
            System.out.println("\n\uF0B2 " + actors.get(actorId).getName() + "'s spectacles \uF0B2");

            if (actors.get(actorId).getSpectacles().isEmpty())
                System.out.println("The actor has not starred in any spectacles from the theater's spectacles as of yet.\n");
            else
            {
                for (Spectacle spectacle : actors.get(actorId).getSpectacles())
                    System.out.println("\uF09F " + '"' + spectacle.getName() + '"' + " by " + spectacle.getDirector().getName());
                System.out.println();
            }

            Audit audit = Audit.getInstance();
            audit.writeToFile("The spectacles that have the actor " + actors.get(actorId).getName() + " in their distribution were listed!", "./theaterArtists.cvs");
        }
    }

    public boolean add(Actor actor, List<Actor> actors)
    {
        for (Actor a : actors)
            if (a.getName().equalsIgnoreCase(actor.getName()))
                return false;
        return true;
    }
    public boolean add(Actor actor, Map<Integer, Actor> actors)
    {
        for (Map.Entry<Integer, Actor> a : actors.entrySet())
            if (a.getValue().getName().equalsIgnoreCase(actor.getName()))
                return false;
        return true;
    }
}
