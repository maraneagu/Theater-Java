package Service;

import Audit.Audit;
import Repository.EventRepository;
import Repository.TicketRepository;
import Theater.Theater;
import Theater.User;
import Theater.Ticket;
import Theater.Event;
import Exception.InvalidNumberException;
import java.util.*;

public class TicketService {
    private static TicketService singleInstance = null;
    private final TheaterService theaterService;
    private final User user;
    private List<Ticket> tickets;

    private TicketService()
    {
        Theater theater = Theater.getInstance();
        user = theater.getUser();

        theaterService = TheaterService.getInstance();

        TicketRepository ticketRepository = TicketRepository.getInstance();
        tickets = ticketRepository.getTickets(theaterService.getEvents());
        user.setTickets(ticketRepository.getUserTickets(user));
    }

    public static synchronized TicketService getInstance()
    {
        if (singleInstance == null)
            singleInstance = new TicketService();
        return singleInstance;
    }

    public void buyTicket()
    {
        Scanner in = new Scanner(System.in);

        availableEvents();
        List<Event> events = theaterService.getEvents();

        System.out.println("\uF0B2 The theater's events \uF0B2");

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
                try
                {
                    System.out.print("Enter the number of the event " +
                            "that you want to buy a ticket for: ");
                    sEventId = in.nextLine().trim();

                    if (sEventId.compareTo("1") >= 0 && sEventId.compareTo(Integer.toString(events.size())) <= 0)
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            eventId = Integer.parseInt(sEventId);
            PrintService printService = new PrintService();
            System.out.println("\n" + printService.printEventSeats(events.get(eventId - 1)));

            while (true)
            {
                try
                {
                    System.out.print("Enter the row where " +
                            "you want sit: ");
                    rowId = Integer.parseInt(in.nextLine().trim());

                    if (rowId >= 1 && rowId <= events.get(eventId - 1).getStage().getNumberOfRows()
                            && availableRow(events.get(eventId - 1), rowId - 1))
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            while (true)
            {
                try
                {
                    System.out.print("Enter the seat where " +
                            "you want sit: ");
                    seatId = Integer.parseInt(in.nextLine().trim());

                    if (seatId >= 1 && seatId <= events.get(eventId - 1).getStage().getNumberOfSeatsPerRow() &&
                            events.get(eventId - 1).getSeats().get(rowId - 1).get(seatId - 1))
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            events.get(eventId - 1).getSeats().get(rowId - 1).set(seatId - 1, false);
            Ticket ticket = new Ticket(user.getUsername(), events.get(eventId - 1), rowId, seatId);

            TicketRepository ticketRepository = TicketRepository.getInstance();
            ticketRepository.insertTicket(ticket);

            tickets.add(ticket);
            user.getTickets().add(ticket);

            System.out.println("\n\uF0B2 A new ticket was added to " + user.getName() + "'s tickets list!");
            System.out.println("\uF0B2 A new ticket was added to ticket's shop tickets list! \n");

            Audit audit = Audit.getInstance();
            audit.writeToFile("A new ticket was added to " + user.getName() + "'s tickets list", "./theaterTickets.cvs");
            audit.writeToFile("A new ticket was added to ticket's shop tickets list", "./theaterTickets.cvs");
        }
    }

    public void cancelTicket()
    {
        Scanner in = new Scanner(System.in);

        List<Ticket> tickets = user.getTickets();

        System.out.println("\uF0B2 " + user.getName() + "'s tickets \uF0B2");

        if (tickets.isEmpty()) System.out.println("You have bought no tickets as of yet.\n");
        else
        {
            int ticketId = 0;

            Collections.sort(user.getTickets());

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

            while (true)
            {
                try
                {
                    System.out.print("Enter the number of the ticket " +
                            "that you want to cancel: ");
                    eventId = in.nextLine().trim();

                    if (eventId.compareTo("1") >= 0 && eventId.compareTo(Integer.toString(tickets.size())) <= 0)
                        break;
                    else throw new InvalidNumberException();
                }
                catch (InvalidNumberException exception)
                {
                    System.out.println(exception.getMessage());
                }
            }

            ticketId = 0;
            for (Ticket ticket : tickets)
            {
                ticketId++;
                if (ticketId == Integer.parseInt(eventId))
                {
                    System.out.println("\n\uF04A The ticket for " + '"' + ticket.getEvent().getSpectacle().getName() + '"' +
                            " performed at " + ticket.getEvent().getStage().getName() +
                            " on " + ticket.getEvent().getDate() + " at " + ticket.getEvent().getBeginTime() +
                            " was removed! \n");

                    Audit audit = Audit.getInstance();
                    audit.writeToFile("The ticket for " + '"' + ticket.getEvent().getSpectacle().getName() + '"' +
                    " performed at " + ticket.getEvent().getStage().getName() +
                            " on " + ticket.getEvent().getDate() + " at " + ticket.getEvent().getBeginTime() +
                            " was removed!", "./theaterTickets.cvs");

                    TicketRepository ticketRepository = TicketRepository.getInstance();
                    ticketRepository.deleteTicket(ticket);

                    ticket.getEvent().getSeats().get(ticket.getRow() - 1).set(ticket.getSeat() - 1, true);
                    tickets.remove(ticketId - 1);
                    break;
                }
            }
        }
    }
    public void listTickets()
    {
        System.out.println("\uF0B2 " + user.getName() + "'s tickets \uF0B2");

        if (user.getTickets().isEmpty())
            System.out.println("You have bought no tickets as of yet.\n");
        else
        {
            Collections.sort(user.getTickets());

            for (Ticket ticket : user.getTickets())
            {
                System.out.println("\uF09F " + '"' + ticket.getEvent().getSpectacle().getName() + '"' +
                        " performed at " + ticket.getEvent().getStage().getName());
                System.out.println("   Date: " + ticket.getEvent().getDate() + ", Time: " + ticket.getEvent().getBeginTime());
                System.out.println("   Row: " + ticket.getRow() + ", Seat: " + ticket.getSeat());
            }
            System.out.println();
        }

        Audit audit = Audit.getInstance();
        audit.writeToFile("The " + user.getName() + "'s tickets were listed!", "./theaterTickets.cvs");
    }

    public void removeTicketByEvent(Event event) {
        tickets.removeIf(ticket -> ticket.getEvent() == event);
    }

    public boolean availableRow(Event event, int row)
    {
        List<List<Boolean>> seats = event.getSeats();

        boolean seatsAvailable = false;
        for (int i = 0; i < seats.get(row).size(); i++)
            if (seats.get(row).get(i))
                seatsAvailable = true;

        if (!seatsAvailable) return false;
        return true;
    }

    public void availableEvents()
    {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        List<Event> events = theaterService.getEvents();
        List<Integer> eventsToEliminate = new ArrayList<>();

        for (int eventPositon = 0; eventPositon < events.size(); eventPositon++)
        {
            String[] splitDate = events.get(eventPositon).getDate().split("\\.");
            String[] splitTime = events.get(eventPositon).getBeginTime().split(":");
            int day = Integer.parseInt(splitDate[0]);
            int month = Integer.parseInt(splitDate[1]);
            int year = Integer.parseInt(splitDate[2]);
            int hour = Integer.parseInt(splitTime[0]);

            if (year < currentYear)
                eventsToEliminate.add(eventPositon);
            else if (year == currentYear)
            {
                if (month < currentMonth)
                    eventsToEliminate.add(eventPositon);
                else if (month == currentMonth)
                {
                    if (day < currentDay)
                        eventsToEliminate.add(eventPositon);
                    else if (day == currentDay)
                        if (hour <= currentHour)
                            eventsToEliminate.add(eventPositon);
                }
            }
        }

        EventRepository eventRepository = EventRepository.getInstance();
        for (int eventPosition : eventsToEliminate)
        {
            eventRepository.deleteEvent(theaterService.getEvents().get(eventPosition));
            theaterService.getEvents().remove(eventPosition);
            removeTicketByEvent(theaterService.getEvents().get(eventPosition));
        }
    }
}
