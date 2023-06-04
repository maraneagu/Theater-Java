package Repository;

import Configuration.DatabaseConfiguration;
import Theater.Event;
import Theater.Ticket;
import Theater.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {
    private static TicketRepository singleInstance = null;

    private TicketRepository() {}

    public static synchronized TicketRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new TicketRepository();
        return singleInstance;
    }

    public List<Ticket> getTickets(List<Event> events) {
        String preparedSql = "SELECT * FROM Ticket";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        EventRepository eventRepository = EventRepository.getInstance();
        List<Ticket> tickets = new ArrayList<>();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet ticketSet = statement.executeQuery();

            while (ticketSet.next())
            {
                String username = ticketSet.getString("username");
                Event event = eventRepository.getEventById(ticketSet.getInt("eventId"));
                int row = ticketSet.getInt("row");
                int seat = ticketSet.getInt("seat");
                Ticket ticket = new Ticket(username, event, row, seat);
                tickets.add(ticket);

                for (Event e : events)
                {
                    if (e.getSpectacle().getName().equals(event.getSpectacle().getName()) && e.getStage().getName().equals(event.getStage().getName())
                            && e.getDate().equals(event.getDate()) && e.getBeginTime().equals(event.getBeginTime())) {
                        e.getSeats().get(row - 1).set(seat - 1, false);
                        break;
                    }
                }
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return tickets;
    }

    public List<Ticket> getUserTickets(User user) {
        String preparedSql = "SELECT * FROM Ticket WHERE username = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        EventRepository eventRepository = EventRepository.getInstance();
        List<Ticket> tickets = new ArrayList<>();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setString(1, user.getUsername());
            ResultSet ticketSet = statement.executeQuery();

            while (ticketSet.next())
            {
                String username = ticketSet.getString("username");
                Event event = eventRepository.getEventById(ticketSet.getInt("eventId"));
                int row = ticketSet.getInt("row");
                int seat = ticketSet.getInt("seat");
                Ticket ticket = new Ticket(username, event, row, seat);
                tickets.add(ticket);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return tickets;
    }

    public int getIdByTicket(Ticket ticket) {
        String preparedSql = "SELECT id FROM Ticket WHERE username = ? AND eventId = ? AND row = ? AND seat = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        EventRepository eventRepository = EventRepository.getInstance();
        Integer id = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setString(1, ticket.getUsername());
            statement.setInt(2, eventRepository.getIdByEvent(ticket.getEvent()));
            statement.setInt(3, ticket.getRow());
            statement.setInt(4, ticket.getSeat());

            ResultSet ticketSet = statement.executeQuery();

            if (ticketSet.next())
                id = ticketSet.getInt("id");
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return id;
    }

    public void insertTicket(Ticket ticket) {
        String preparedSql = "INSERT INTO Ticket VALUES (null, ?, ?, ?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        EventRepository eventRepository = EventRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setString(1, ticket.getUsername());
            statement.setInt(2, eventRepository.getIdByEvent(ticket.getEvent()));
            statement.setInt(3, ticket.getRow());
            statement.setInt(4, ticket.getSeat());

            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void deleteTicket(Ticket ticket) {
        String preparedSql = "DELETE FROM Ticket WHERE id = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setInt(1, getIdByTicket(ticket));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }
}
