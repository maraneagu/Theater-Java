package Service;

import Theater.Spectacle.Spectacle;
import Theater.Ticket;
import Theater.Event;
import Service.PrintService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketShopService {
    private static TicketShopService singleInstance = null;
    private final TheaterService theaterService;
    private List<Ticket> tickets;

    private TicketShopService() {
        theaterService = TheaterService.getInstance();
        tickets = new ArrayList<>();
    }

    public static synchronized TicketShopService getInstance() {
        if (singleInstance == null)
            singleInstance = new TicketShopService();
        return singleInstance;
    }

    public void buyTicket() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = in.nextLine();

        System.out.println("\n\uF0B2 The theater's events \uF0B2");

        List<Event> events = theaterService.getEvents();

        if (events.size() == 0)
            System.out.println("There are no events as of yet.\n");
        else
        {
            int i, eventId, rowId, seatId;

            for (i = 0; i < events.size(); i++)
                System.out.println(i + 1 + ". " + '"' + events.get(i).getSpectacle().getName() +
                        '"' + " performed at the " + events.get(i).getStage().getName() +
                        " on " + events.get(i).getDate());
            System.out.println();

            while (true)
            {
                System.out.print("Enter the number of the event " +
                        "that you want to buy a ticket for: ");
                eventId = Integer.parseInt(in.nextLine().trim());

                if (eventId >= 1 && eventId <= events.size())
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            System.out.println();
            PrintService printService = new PrintService();
            System.out.println(printService.printEventSeats(events.get(eventId - 1)));

            while (true)
            {
                System.out.print("Enter the row where " +
                        "you want sit: ");
                rowId = Integer.parseInt(in.nextLine().trim());

                if (rowId >= 1 && rowId <= events.get(eventId - 1).getSeats().size() && availableRow(events.get(eventId - 1), rowId - 1))
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            while (true)
            {
                System.out.print("Enter the seat where " +
                        "you want sit: ");
                seatId = Integer.parseInt(in.nextLine().trim());

                if (seatId >= 1 && seatId <= events.get(eventId - 1).getSeats().get(rowId - 1).size() && events.get(eventId - 1).getSeats().get(rowId - 1).get(seatId - 1))
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            events.get(eventId - 1).getSeats().get(rowId - 1).set(seatId - 1, false);

            Ticket ticket = new Ticket(name, events.get(eventId - 1), rowId, seatId);
            tickets.add(ticket);
            System.out.println("\n\uF0B2 A new ticket was added to ticket's shop tickets list! \n");
        }
    }

    public void cancelTicket() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = in.nextLine();

        System.out.println("\n\uF0B2 " + name + "'s tickets \uF0B2");

        int numberOfTicketsBought = 0, ticketId = 0;
        for (int i = 0; i < tickets.size(); i++)
            if (tickets.get(i).getName().equalsIgnoreCase(name))
            {
                numberOfTicketsBought++;
                ticketId++;

                System.out.println(ticketId + ". " + '"' + tickets.get(i).getEvent().getSpectacle().getName() + '"' +
                        " performed at " + tickets.get(i).getEvent().getStage().getName() +
                        " on " + tickets.get(i).getEvent().getDate());
            }
        if (numberOfTicketsBought == 0) System.out.println("You have bought no tickets as of yet.\n");
        else
        {
            System.out.println();
            int eventId;

            while (true)
            {
                System.out.print("Enter the number of the event " +
                        "that you want to cancel your ticket for: ");
                eventId = Integer.parseInt(in.nextLine().trim());

                if (eventId >= 1 && eventId <= numberOfTicketsBought)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            ticketId = 0;

            for (int i = 0; i < tickets.size(); i++)
                if (tickets.get(i).getName().equalsIgnoreCase(name))
                {
                    ticketId++;

                    if (ticketId == eventId) {
                        System.out.println("\uF0B2 The ticket for " + '"' + tickets.get(i).getEvent().getSpectacle().getName() + '"' +
                                " performed at " + tickets.get(i).getEvent().getStage().getName() +
                                " on " + tickets.get(i).getEvent().getDate() + " was removed! \n");

                        tickets.get(i).getEvent().getSeats().get(tickets.get(i).getRow() - 1).set(tickets.get(i).getSeat() - 1, true);
                        tickets.remove(i);
                    }
                    break;
                }
        }
    }
    public void listTickets() {
        System.out.println("\uF0B2 The theater's events \uF0B2");

        List<Event> events = theaterService.getEvents();

        if (events.size() == 0)
            System.out.println("There are no events as of yet.\n");
        else
        {
            Scanner in = new Scanner(System.in);
            int i, eventId;

            for (i = 0; i < events.size(); i++)
                System.out.println(i + 1 + ". " + '"' + events.get(i).getSpectacle().getName() +
                        '"' + " performed at the " + events.get(i).getStage().getName() +
                        " on " + events.get(i).getDate());
            System.out.println();

            while (true)
            {
                System.out.print("Enter the number of the event " +
                        "that you want to list the tickets for: ");
                eventId = Integer.parseInt(in.nextLine().trim());

                if (eventId >= 1 && eventId <= events.size())
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            System.out.println("\n\uF0B2 The events's tickets \uF0B2");

            boolean ticketExists = false;
            for (Ticket ticket : tickets)
                if (ticket.getEvent() == events.get(eventId - 1))
                {
                    ticketExists = true;
                    System.out.println("\uF09F " + ticket.getName() + ", row: " +
                            ticket.getRow() + ", seat: " + ticket.getSeat());
                }

            if(!ticketExists) System.out.println("There are no tickets as of yet.\n");
            else System.out.println();
        }
    }

    public void removeTicketByEvent(Event event) {
        tickets.removeIf(ticket -> ticket.getEvent() == event);
    }

    public boolean availableRow(Event event, int row) {
        List<List<Boolean>> seats = event.getSeats();

        boolean seatsAvailable = false;
        for (int i = 0; i < seats.get(row).size(); i++)
            if (seats.get(row).get(i))
                seatsAvailable = true;

        if (!seatsAvailable) return false;
        return true;
    }
}
