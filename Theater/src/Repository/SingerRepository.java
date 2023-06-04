package Repository;

import Configuration.DatabaseConfiguration;
import Theater.Artist.Singer;
import Theater.Spectacle.Musical;
import Theater.Spectacle.Opera;
import Theater.Spectacle.Spectacle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingerRepository {
    private static SingerRepository singleInstance = null;

    private SingerRepository() {}

    public static synchronized SingerRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new SingerRepository();
        return singleInstance;
    }

    public HashMap<Integer, Singer> getSingers() {
        String preparedSql = "SELECT * FROM Singer";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        HashMap<Integer, Singer> singers = new HashMap<>();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet singerSet = statement.executeQuery();

            while (singerSet.next())
            {
                String name = singerSet.getString("name");

                Singer singer = new Singer(name);
                singers.put(singers.size() + 1, singer);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return singers;
    }

    public void getOperaSingers(List<Spectacle> spectacles, Map<Integer, Singer> singers) {
        String preparedSql = "SELECT * FROM OperaSinger";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet operaSingerSet = statement.executeQuery();

            while (operaSingerSet.next())
            {
                int operaId = operaSingerSet.getInt("operaId");
                int singerId = operaSingerSet.getInt("singerId");

                Opera opera = (Opera) spectacleRepository.getSpectacleById(operaId);
                Singer singer = getSingerById(singerId);

                for (Spectacle spectacle : spectacles)
                    if (spectacle instanceof Opera && spectacle.getName().equals(opera.getName()))
                    {
                        ((Opera) spectacle).getSingers().add(singer);
                        break;
                    }

                for (Map.Entry<Integer, Singer> s : singers.entrySet())
                    if (s.getValue().getName().equalsIgnoreCase(singer.getName()))
                    {
                        s.getValue().getSpectacles().add(opera);
                        break;
                    }
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void getMusicalSingers(List<Spectacle> spectacles, Map<Integer, Singer> singers) {
        String preparedSql = "SELECT * FROM MusicalSinger";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet musicalSingerSet = statement.executeQuery();

            while (musicalSingerSet.next())
            {
                int musicalId = musicalSingerSet.getInt("musicalId");
                int singerId = musicalSingerSet.getInt("singerId");

                Musical musical = (Musical) spectacleRepository.getSpectacleById(musicalId);
                Singer singer = getSingerById(singerId);

                for (Spectacle spectacle : spectacles)
                    if (spectacle instanceof Musical && spectacle.getName().equals(musical.getName()))
                    {
                        ((Musical) spectacle).getSingers().add(singer);
                        break;
                    }

                for (Map.Entry<Integer, Singer> s : singers.entrySet())
                    if (s.getValue().getName().equalsIgnoreCase(singer.getName()))
                    {
                        s.getValue().getSpectacles().add(musical);
                        break;
                    }
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public Integer getIdBySinger(Singer singer) {
        String preparedSql = "SELECT id FROM Singer WHERE name = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Integer id = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setString(1, singer.getName());
            ResultSet singerSet = statement.executeQuery();

            if (singerSet.next())
                id = singerSet.getInt("id");
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return id;
    }

    public Singer getSingerById(Integer id) {
        String preparedSql = "SELECT * FROM Singer WHERE id = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Singer singer = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setInt(1, id);
            ResultSet singerSet = statement.executeQuery();

            if (singerSet.next())
            {
                String name = singerSet.getString("name");
                singer = new Singer(name);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return singer;
    }

    public void insertSinger(Singer singer) {
        String preparedSql = "INSERT INTO Singer VALUES (null, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setString(1, singer.getName());
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void insertOperaSinger(Opera opera, Singer singer) {
        String preparedSql = "INSERT INTO OperaSinger VALUES (?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(opera));
            statement.setInt(2, getIdBySinger(singer));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void insertMusicalSinger(Musical musical, Singer singer) {
        String preparedSql = "INSERT INTO MusicalSinger VALUES (?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(musical));
            statement.setInt(2, getIdBySinger(singer));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void deleteOperaSinger(Opera opera, Singer singer) {
        String preparedSql = "DELETE FROM OperaSinger WHERE operaId = ? AND singerId = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(opera));
            statement.setInt(2, getIdBySinger(singer));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void deleteMusicalSinger(Musical musical, Singer singer) {
        String preparedSql = "DELETE FROM MusicalSinger WHERE musicalId = ? AND singerId = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(musical));
            statement.setInt(2, getIdBySinger(singer));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }
}
