package Repository;

import Configuration.DatabaseConfiguration;
import Theater.Event;
import Theater.Spectacle.Spectacle;
import Theater.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    private static EventRepository singleInstance = null;

    private EventRepository() {}

    public static synchronized EventRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new EventRepository();
        return singleInstance;
    }

    public List<Event> getEvents() {
        String preparedSql = "SELECT * FROM Event";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();
        StageRepository stageRepository = StageRepository.getInstance();
        List<Event> events = new ArrayList<>();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet eventSet = statement.executeQuery();

            while (eventSet.next())
            {
                Spectacle spectacle = spectacleRepository.getSpectacleById(eventSet.getInt("spectacleId"));
                Stage stage = stageRepository.getStageById(eventSet.getInt("stageId"));
                String date = eventSet.getString("date");
                String beginTime = eventSet.getString("beginTime");
                String endTime = eventSet.getString("endTime");
                Double price = eventSet.getDouble("price");
                Event event = new Event(spectacle, stage, date, beginTime, endTime, price);
                events.add(event);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return events;
    }

    public int getIdByEvent(Event event) {
        String preparedSql = "SELECT id FROM Event WHERE spectacleId = ? AND stageId = ? AND date = ? AND beginTime = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();
        StageRepository stageRepository = StageRepository.getInstance();
        Integer id = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(event.getSpectacle()));
            statement.setInt(2, stageRepository.getIdByStage(event.getStage()));
            statement.setString(3, event.getDate());
            statement.setString(4, event.getBeginTime());

            ResultSet eventSet = statement.executeQuery();

            if (eventSet.next())
                id = eventSet.getInt("id");
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return id;
    }

    public Event getEventById(Integer id) {
        String preparedSql = "SELECT * FROM Event WHERE id = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();
        StageRepository stageRepository = StageRepository.getInstance();
        Event event = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setInt(1, id);
            ResultSet eventSet = statement.executeQuery();

            if (eventSet.next())
            {
                Spectacle spectacle = spectacleRepository.getSpectacleById(eventSet.getInt("spectacleId"));
                Stage stage = stageRepository.getStageById(eventSet.getInt("stageId"));
                String date = eventSet.getString("date");
                String beginTime = eventSet.getString("beginTime");
                String endTime = eventSet.getString("endTime");
                Double price = eventSet.getDouble("price");
                event = new Event(spectacle, stage, date, beginTime, endTime, price);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return event;
    }

    public void insertEvent(Event event) {
        String preparedSql = "INSERT INTO Event VALUES (null, ?, ?, ?, ?, ?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();
        StageRepository stageRepository = StageRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(event.getSpectacle()));
            statement.setInt(2, stageRepository.getIdByStage(event.getStage()));
            statement.setString(3, event.getDate());
            statement.setString(4, event.getBeginTime());
            statement.setString(5, event.getEndTime());
            statement.setDouble(6, event.getPrice());

            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void deleteEvent(Event event) {
        String preparedSql = "DELETE FROM Event WHERE id = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setInt(1, getIdByEvent(event));
            statement.executeUpdate();

        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }
}
