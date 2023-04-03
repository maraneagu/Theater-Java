package Service.ArtistService;

import Service.TheaterService;
import Theater.Artist.Actor;
import Theater.Spectacle.Play;
import Theater.Spectacle.Spectacle;

import java.util.Map;
import java.util.List;
import java.util.Scanner;

public class ActorService {
    private final TheaterService theaterService;
    public ActorService() {
        theaterService = TheaterService.getInstance();
    }

    public void addActor() {
        System.out.println("\uF0B2 The theater's plays \uF0B2");

        String spectacleId;
        int playId = 0;
        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();

        for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
            if (spectacle.getValue() instanceof Play)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getValue().getName() + '"');
            }

        if (playId == 0)
            System.out.println("There are no plays as of yet." + '\n');
        else
        {
            Map<Integer, Actor> actors = theaterService.getActors();

            System.out.println();
            while (true)
            {
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the play where you want to add a new actor: ");
                spectacleId = in.nextLine().trim();

                if (spectacleId.compareTo("1") >= 0 && spectacleId.compareTo(Integer.toString(playId)) <= 0)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            Actor actor = new Actor();
            actor.toRead();

            playId = 0;
            for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                if (spectacle.getValue() instanceof Play)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        if (add(actor, ((Play) spectacle.getValue()).getActors()))
                        {
                            List<Actor> sActors = ((Play) spectacle.getValue()).getActors();
                            sActors.add(actor);
                            actor.getSpectacles().add(spectacle.getValue());

                            System.out.println("\uF0B2 A new actor was added to " + '"' + spectacle.getValue().getName() + '"' + "'s actors list!");
                        }
                        else System.out.println("\uF04A The actor you entered already exists in " + '"' +
                                spectacle.getValue().getName() + '"' + "'s actors list!");

                        if (add(actor, actors))
                        {
                            theaterService.setActorId(theaterService.getActorId() + 1);
                            actors.put(theaterService.getActorId(), actor);
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

        String spectacleId;
        int playId = 0;
        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();

        for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
            if (spectacle.getValue() instanceof Play)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getValue().getName() + '"');
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
                spectacleId = in.nextLine().trim();

                if (spectacleId.compareTo("1") >= 0 && spectacleId.compareTo(Integer.toString(playId)) <= 0)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            playId = 0;
            for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                if (spectacle.getValue() instanceof Play)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        List<Actor> sActors = ((Play) spectacle.getValue()).getActors();

                        System.out.println("\n\uF0B2 The " + '"' + spectacle.getValue().getName() + '"' +
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
                                Scanner in = new Scanner(System.in);

                                System.out.print("Enter the number of the actor that you want to remove: ");
                                sActorId = in.nextLine().trim();

                                if (sActorId.compareTo("1") >= 0 && sActorId.compareTo(Integer.toString(sActors.size())) <= 0)
                                    break;
                                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
                            }

                            actorId = Integer.parseInt(sActorId);
                            System.out.println("\n\uF04A The actor " + sActors.get(actorId - 1).getName() +
                                    " was removed from " + '"' + spectacle.getValue().getName() + '"'
                                    + "'s actors list! \n");

                            sActors.get(actorId - 1).getSpectacles().remove(spectacle.getValue());
                            sActors.remove(actorId - 1);
                            break;
                        }
                    }
                }
        }
    }

    public void listActors() {
        System.out.println("\uF0B2 The theater's plays \uF0B2");

        String spectacleId;
        int playId = 0;
        Map<Integer, Spectacle> spectacles = theaterService.getSpectacles();

        for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
            if (spectacle.getValue() instanceof Play)
            {
                playId++;
                System.out.println(playId + ". " + '"' + spectacle.getValue().getName() + '"');
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
                spectacleId = in.nextLine().trim();

                if (spectacleId.compareTo("1") >= 0 && spectacleId.compareTo(Integer.toString(playId)) <= 0)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            playId = 0;
            for (Map.Entry<Integer, Spectacle> spectacle : spectacles.entrySet())
                if (spectacle.getValue() instanceof Play)
                {
                    playId++;
                    if (playId == Integer.parseInt(spectacleId))
                    {
                        List<Actor> sActors = ((Play) spectacle.getValue()).getActors();

                        System.out.println("\n\uF0B2 The " + '"' + spectacle.getValue().getName() + '"' +
                                "'s actors list \uF0B2");

                        if (sActors.isEmpty()) System.out.println("There are no actors as of yet.\n");
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

    public void searchActor() {
        System.out.println("\uF0B2 The theater's actors \uF0B2");

        Map<Integer, Actor> actors = theaterService.getActors();

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
                Scanner in = new Scanner(System.in);

                System.out.print("Enter the number of the actor that you want to search the spectacles for: ");
                sActorId = in.nextLine().trim();

                if (sActorId.compareTo("1") >= 0 && sActorId.compareTo(Integer.toString(actors.size())) <= 0)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
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
        }
    }

    public boolean add(Actor actor, List<Actor> actors) {
        for (Actor a : actors)
            if (a.getName().equalsIgnoreCase(actor.getName()))
                return false;
        return true;
    }
    public boolean add(Actor actor, Map<Integer, Actor> actors) {
        for (Map.Entry<Integer, Actor> a : actors.entrySet())
            if (a.getValue().getName().equalsIgnoreCase(actor.getName()))
                return false;
        return true;
    }
}
