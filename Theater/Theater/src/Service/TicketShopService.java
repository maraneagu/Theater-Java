package Service;

import Theater.Profile;
import Theater.Spectacle.Spectacle;
import Theater.Ticket;
import Theater.Event;
import Service.PrintService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TicketShopService {
    private static TicketShopService singleInstance = null;
    private final TheaterService theaterService;
    private final Profile profile;
    private List<Ticket> tickets;

    private TicketShopService() {
        theaterService = TheaterService.getInstance();
        profile = Profile.getInstance();
        tickets = new ArrayList<>();
    }

    public static synchronized TicketShopService getInstance() {
        if (singleInstance == null)
            singleInstance = new TicketShopService();
        return singleInstance;
    }

    public void buyTicket() {
        Scanner in = new Scanner(System.in);

        System.out.println("\uF0B2 The theater's events \uF0B2");

        List<Event> events = theaterService.getEvents();

        if (events.isEmpty())
            System.out.println("There are no events as of yet.\n");
        else
        {
            int i, eventId, rowId, seatId;
            String sEventId, sRowId, sSeatId;

            Collections.sort(events);
            System.out.println("Here are the events from the latest to the oldest:\n");

            for (i = 0; i < events.size(); i++)
            {
                System.out.println(i + 1 + ". " + '"' + events.get(i).getSpectacle().getName() +
                        '"' + " performed at the " + events.get(i).getStage().getName());
                System.out.println("    Date: " + events.get(i).getDate() + ", Time: " + events.get(i).getBeginTime());
            }
            System.out.println();

            while (true)
            {
                System.out.print("Enter the number of the event " +
                        "that you want to buy a ticket for: ");
                sEventId = in.nextLine().trim();

                if (sEventId.compareTo("1") >= 0 && sEventId.compareTo(Integer.toString(events.size())) <= 0)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            System.out.println();
            eventId = Integer.parseInt(sEventId);
            PrintService printService = new PrintService();
            System.out.println(printService.printEventSeats(events.get(eventId - 1)));

            while (true)
            {
                System.out.print("Enter the row where " +
                        "you want sit: ");
                sRowId = in.nextLine().trim();

                if (sRowId.compareTo("1") >= 0 && sRowId.compareTo(Integer.toString(events.get(eventId - 1).getSeats().size())) <= 0
                        && availableRow(events.get(eventId - 1), Integer.parseInt(sRowId) - 1))
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            rowId = Integer.parseInt(sRowId);

            while (true)
            {
                System.out.print("Enter the seat where " +
                        "you want sit: ");
                sSeatId = in.nextLine().trim();

                if (sSeatId.compareTo("1") >= 0 && sSeatId.compareTo(Integer.toString(events.get(eventId - 1).getSeats().get(rowId - 1).size())) <= 0 &&
                        events.get(eventId - 1).getSeats().get(rowId - 1).get(Integer.parseInt(sSeatId) - 1))
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            seatId = Integer.parseInt(sSeatId);
            events.get(eventId - 1).getSeats().get(rowId - 1).set(seatId - 1, false);

            Ticket ticket = new Ticket(profile.getUserName(), events.get(eventId - 1), rowId, seatId);
            tickets.add(ticket);
            profile.getTickets().add(ticket);
            System.out.println("\n\uF0B2 A new ticket was added to " + profile.getName() + "'s tickets list!");
            System.out.println("\uF0B2 A new ticket was added to ticket's shop tickets list! \n");
        }
    }

    public void cancelTicket() {
        Scanner in = new Scanner(System.in);

        System.out.println("\uF0B2 " + profile.getName() + "'s tickets \uF0B2");

        List<Ticket> tickets = profile.getTickets();

        if (tickets.isEmpty()) System.out.println("You have bought no tickets as of yet.\n");
        else
        {
            int ticketId = 0;

            Collections.sort(profile.getTickets());

            for (Ticket ticket : tickets)
            {
                ticketId++;
                System.out.println(ticketId + ". " + '"' + ticket.getEvent().getSpectacle().getName() + '"' +
                        " performed at " + ticket.getEvent().getStage().getName());
                System.out.println("    Date: " + ticket.getEvent().getDate() + ", Time: " + ticket.getEvent().getBeginTime());
                System.out.println("    Row: " + ticket.getRow() + ", Seat: " + ticket.getSeat());
            }

            System.out.println();
            String eventId;

            while (true) {
                System.out.print("Enter the number of the ticket " +
                        "that you want to cancel: ");
                eventId = in.nextLine().trim();

                if (eventId.compareTo("1") >= 0 && eventId.compareTo(Integer.toString(tickets.size())) <= 0)
                    break;
                System.out.println("\uF0FB The number you introduced is not valid! Please try again! \uF0FB \n");
            }

            ticketId = 0;
            for (Ticket ticket : tickets) {
                ticketId++;
                if (ticketId == Integer.parseInt(eventId)) {
                    System.out.println("\n\uF04A The ticket for " + '"' + ticket.getEvent().getSpectacle().getName() + '"' +
                            " performed at " + ticket.getEvent().getStage().getName() +
                            " on " + ticket.getEvent().getDate() + " at " + ticket.getEvent().getBeginTime() +
                            " was removed! \n");

                    ticket.getEvent().getSeats().get(ticket.getRow() - 1).set(ticket.getSeat() - 1, true);
                    tickets.remove(ticketId - 1);
                    break;
                }
            }
        }
    }
    public void listTickets() {
        System.out.println("\uF0B2 " + profile.getName() + "'s tickets \uF0B2");

        if (profile.getTickets().isEmpty())
            System.out.println("You have bought no tickets as of yet.\n");
        else
        {
            Collections.sort(profile.getTickets());

            for (Ticket ticket : profile.getTickets())
            {
                System.out.println("\uF09F " + '"' + ticket.getEvent().getSpectacle().getName() + '"' +
                        " performed at " + ticket.getEvent().getStage().getName());
                System.out.println("   Date: " + ticket.getEvent().getDate() + ", Time: " + ticket.getEvent().getBeginTime());
                System.out.println("   Row: " + ticket.getRow() + ", Seat: " + ticket.getSeat());
            }
            System.out.println();
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
