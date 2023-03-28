package Service;

import Theater.Event;
import Theater.Spectacle.Spectacle;
import Theater.Stage;
import Service.TicketShopService;

import java.util.*;

public class EventService {
    private final TheaterService theaterService;

    public EventService() {
        theaterService = TheaterService.getInstance();
    }

    public void addEvent() {
        System.out.println("\uF0B2 The theater's spectacles \uF0B2");

        List<Spectacle> spectacles = theaterService.getSpectacles();

        if (spectacles.size() == 0)
            System.out.println("There are no spectacles as of yet.");
        else
        {
            List<Stage> stages = theaterService.getStages();
            List<Event> events = theaterService.getEvents();

            int i, spectacleId, stageId;
            for (i = 0; i < spectacles.size(); i++)
                System.out.println(i + 1 + ". " + '"' + spectacles.get(i).getName() + '"');
            System.out.println();

            while (true)
            {
                Scanner in = new Scanner(System.in);
                System.out.print("Enter the number of the spectacle " +
                        "that you want to add to the event: ");
                spectacleId = Integer.parseInt(in.nextLine().trim());

                if (spectacleId >= 1 && spectacleId <= spectacles.size())
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            System.out.println("\n\uF0B2 The theater's stages \uF0B2");
            for (i = 0; i < stages.size(); i++)
                System.out.println(i + 1 + ". " + stages.get(i).getName());
            System.out.println();

            while (true)
            {
                Scanner in = new Scanner(System.in);
                System.out.print("Enter the number of the stage " +
                        "where the event will take place: ");
                stageId = Integer.parseInt(in.nextLine().trim());

                if (stageId >= 1 && stageId <= stages.size())
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            Event event = new Event(spectacles.get(spectacleId - 1), stages.get(stageId - 1));
            event.toRead();
            events.add(event);

            System.out.println("\uF0B2 A new event was added to theater's events list! \n");
        }
    }

    public void removeEvent() {
        System.out.println("\uF0B2 The theater's events \uF0B2");

        List<Event> events = theaterService.getEvents();

        if (events.size() == 0)
            System.out.println("There are no events as of yet.");
        else
        {
            int i, eventId;
            for (i = 0; i < events.size(); i++)
                System.out.println(i + 1 + ". " + '"' + events.get(i).getSpectacle().getName() +
                        '"' + " performed at the " + events.get(i).getStage().getName());
            System.out.println();

            while (true)
            {
                Scanner in = new Scanner(System.in);
                System.out.print("Enter the number of the event " +
                        "that you want to remove: ");
                eventId = Integer.parseInt(in.nextLine().trim());

                if (eventId >= 1 && eventId <= events.size())
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            TicketShopService ticketShopService = TicketShopService.getInstance();
            ticketShopService.removeTicketByEvent(events.get(eventId - 1));

            System.out.println("\uF0B2 The event " + '"' + events.get(eventId - 1).getSpectacle().getName() +
                    '"' + " that starts at " + events.get(eventId - 1).getTime() +
                    " performed at the " + events.get(eventId - 1).getStage().getName() + "" +
                    " was removed from the theater's events list! \n");
            events.remove(eventId - 1);
        }
    }

    public void removeEventBySpectacle(Spectacle spectacle) {
        List<Event> events = theaterService.getEvents();
        TicketShopService ticketShopService = TicketShopService.getInstance();

        for (Event event : events)
            if (event.getSpectacle() == spectacle)
                ticketShopService.removeTicketByEvent(event);

        events.removeIf(event -> event.getSpectacle() == spectacle);
    }

    public void listEvents() {
        System.out.println("\uF0B2 The theater's events \uF0B2");

        List<Event> events = theaterService.getEvents();

        if (events.size() == 0)
            System.out.println("\nThere are no events as of yet.\n");
        else
        {
            Collections.sort(events);
            for (Event event : events)
                System.out.println("\uF09F " + '"' + event.getSpectacle().getName() +
                            '"' + " that starts at " + event.getTime() +
                            " performed at the " + event.getStage().getName() + " stage on " + event.getDate());
            System.out.println();
        }
    }

    public void listEventDetails() {
        System.out.println("\uF0B2 The theater's events \uF0B2");

        List<Event> events = theaterService.getEvents();

        if (events.size() == 0)
            System.out.println("There are no events as of yet.");
        else
        {
            int i, eventId;
            for (i = 0; i < events.size(); i++)
                System.out.println(i + 1 + ". " + '"' + events.get(i).getSpectacle().getName() + '"');
            System.out.println();

            while (true)
            {
                Scanner in = new Scanner(System.in);
                System.out.print("Enter the number of the event " +
                        "for which you want to list the information: ");
                eventId = Integer.parseInt(in.nextLine().trim());

                if (eventId >= 1 && eventId <= events.size())
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            System.out.println();
            System.out.println(events.get(eventId - 1));
            System.out.println();
        }
    }

    public void changeEventDetails() {
        System.out.println("\uF0B2 The theater's events \uF0B2");

        List<Event> events = theaterService.getEvents();

        if (events.size() == 0)
            System.out.println("There are no events as of yet.");
        else
        {
            Scanner in = new Scanner(System.in);

            int i, eventId;
            for (i = 0; i < events.size(); i++)
                System.out.println(i + 1 + ". " + '"' + events.get(i).getSpectacle().getName() + '"');
            System.out.println();

            while (true)
            {
                System.out.print("Enter the number of the event " +
                        "where you want to change the information: ");
                eventId = Integer.parseInt(in.nextLine().trim());

                if (eventId >= 1 && eventId <= events.size())
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            System.out.println("\nWhich information do you want to change?");
            System.out.println("1. The stage the spectacle is performed at.");
            System.out.println("2. The date of the event.");
            System.out.println("3. The time the spectacle will begin.");
            System.out.println("4. The price of the event.");

            String choiceOfInformation;

            while (true)
            {
                System.out.print("The information: ");

                choiceOfInformation = in.nextLine();
                if (choiceOfInformation.equals("1"))
                {
                    List<Stage> stages = theaterService.getStages();
                    int stageId;

                    System.out.println("\n\uF0B2 The theater's stages \uF0B2");
                    for (i = 0; i < stages.size(); i++)
                        if (stages.get(i) != events.get(eventId - 1).getStage())
                            System.out.println(i + 1 + ". " + stages.get(i).getName());
                    System.out.println();

                    while (true)
                    {
                        System.out.print("Enter the number of the new stage " +
                                "where the event will take place: ");
                        stageId = Integer.parseInt(in.nextLine().trim());

                        if (stageId >= 1 && stageId <= stages.size())
                            break;
                        System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
                    }

                    events.get(eventId - 1).setStage(stages.get(stageId - 1));
                    System.out.println("\n\uF0B2 A new stage was set for the event!\n");
                    break;
                }
                else if (choiceOfInformation.equals("2"))
                {
                    System.out.println("\nThe date of the event! The format: dd/mm/yyyyy!");
                    System.out.print("Enter the new date of the event: ");
                    String date = in.nextLine().trim();

                    events.get(eventId - 1).setDate(date);
                    System.out.println("\n\uF0B2 A new date was set for the event!\n");
                    break;
                }
                else if (choiceOfInformation.equals("3"))
                {
                    while (true)
                    {
                        System.out.println("\nThe time of the event! It should be between 11 AM and 11 PM, with the format: hh:mm!");
                        System.out.print("Enter the time of the event: ");
                        String time = in.nextLine().trim();

                        String[] splitTime = time.split(":");
                        int hour = Integer.parseInt(splitTime[0]);
                        int minutes = Integer.parseInt(splitTime[1]);

                        if (hour < 11 || hour > 22 || minutes > 59)
                            System.out.println("\uF0FB The time you introduced is not valid! Please try again! \uF0FB\n");
                        else
                        {
                            events.get(eventId - 1).setTime(time);
                            System.out.println("\n\uF0B2 A new time was set for the event!\n");
                            break;
                        }
                    }
                    break;
                }
                else if (choiceOfInformation.equals("4")) {
                    System.out.println("\nThe price of the event! It should have two digits, with the format: number:dd!");
                    System.out.print("Enter the price of the event: ");
                    double price = Double.parseDouble(in.nextLine().trim());

                    events.get(eventId - 1).setPrice(price);
                    System.out.println("\n\uF0B2 A new price was set for the event!\n");
                    break;
                }

                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }
        }
    }
}
