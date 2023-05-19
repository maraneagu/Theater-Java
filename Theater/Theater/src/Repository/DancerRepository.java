package Repository;

import Configuration.DatabaseConfiguration;
import Theater.Artist.Dancer;
import Theater.Spectacle.Ballet;
import Theater.Spectacle.Musical;
import Theater.Spectacle.Spectacle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DancerRepository {
    private static DancerRepository singleInstance = null;

    private DancerRepository() {}

    public static synchronized DancerRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new DancerRepository();
        return singleInstance;
    }


    public HashMap<Integer, Dancer> getDancers() {
        String preparedSql = "SELECT * FROM Dancer";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        HashMap<Integer, Dancer> dancers = new HashMap<>();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet dancerSet = statement.executeQuery();

            while (dancerSet.next())
            {
                String name = dancerSet.getString("name");

                Dancer dancer = new Dancer(name);
                dancers.put(dancers.size() + 1, dancer);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return dancers;
    }

    public void getMusicalDancers(List<Spectacle> spectacles, Map<Integer, Dancer> dancers) {
        String preparedSql = "SELECT * FROM MusicalDancer";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet musicalDancerSet = statement.executeQuery();

            while (musicalDancerSet.next())
            {
                int musicalId = musicalDancerSet.getInt("musicalId");
                int dancerId = musicalDancerSet.getInt("dancerId");

                Musical musical = (Musical) spectacleRepository.getSpectacleById(musicalId);
                Dancer dancer = getDancerById(dancerId);

                for (Spectacle spectacle : spectacles)
                    if (spectacle instanceof Musical && spectacle.getName().equals(musical.getName()))
                    {
                        ((Musical) spectacle).getDancers().add(dancer);
                        break;
                    }

                for (Map.Entry<Integer, Dancer> d : dancers.entrySet())
                    if (d.getValue().getName().equalsIgnoreCase(dancer.getName()))
                    {
                        d.getValue().getSpectacles().add(musical);
                        break;
                    }
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void getBalletDancers(List<Spectacle> spectacles, Map<Integer, Dancer> dancers) {
        String preparedSql = "SELECT * FROM BalletDancer";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet balletDancerSet = statement.executeQuery();

            while (balletDancerSet.next())
            {
                int balletId = balletDancerSet.getInt("balletId");
                int dancerId = balletDancerSet.getInt("dancerId");

                Ballet ballet = (Ballet) spectacleRepository.getSpectacleById(balletId);
                Dancer dancer = getDancerById(dancerId);

                for (Spectacle spectacle : spectacles)
                    if (spectacle instanceof Ballet && spectacle.getName().equals(ballet.getName()))
                    {
                        ((Ballet) spectacle).getDancers().add(dancer);
                        break;
                    }

                for (Map.Entry<Integer, Dancer> d : dancers.entrySet())
                    if (d.getValue().getName().equalsIgnoreCase(dancer.getName()))
                    {
                        d.getValue().getSpectacles().add(ballet);
                        break;
                    }
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public Integer getIdByDancer(Dancer dancer) {
        String preparedSql = "SELECT id FROM Dancer WHERE name = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Integer id = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setString(1, dancer.getName());
            ResultSet dancerSet = statement.executeQuery();

            if (dancerSet.next())
                id = dancerSet.getInt("id");
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return id;
    }

    public Dancer getDancerById(Integer id) {
        String preparedSql = "SELECT * FROM Dancer WHERE id = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Dancer dancer = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setInt(1, id);
            ResultSet dancerSet = statement.executeQuery();

            if (dancerSet.next())
            {
                String name = dancerSet.getString("name");
                dancer = new Dancer(name);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return dancer;
    }

    public void insertDancer(Dancer dancer) {
        String preparedSql = "INSERT INTO Dancer VALUES (null, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setString(1, dancer.getName());
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void insertMusicalDancer(Musical musical, Dancer dancer) {
        String preparedSql = "INSERT INTO MusicalDancer VALUES (?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(musical));
            statement.setInt(2, getIdByDancer(dancer));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void insertBalletDancer(Ballet ballet, Dancer dancer) {
        String preparedSql = "INSERT INTO BalletDancer VALUES (?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(ballet));
            statement.setInt(2, getIdByDancer(dancer));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void deleteMusicalDancer(Musical musical, Dancer dancer) {
        String preparedSql = "DELETE FROM MusicalDancer WHERE musicalId = ? AND dancerId = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(musical));
            statement.setInt(2, getIdByDancer(dancer));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void deleteBalletDancer(Ballet ballet, Dancer dancer) {
        String preparedSql = "DELETE FROM BalletDancer WHERE balletId = ? AND dancerId = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(ballet));
            statement.setInt(2, getIdByDancer(dancer));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }
}
