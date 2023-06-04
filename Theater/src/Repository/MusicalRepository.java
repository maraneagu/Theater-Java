package Repository;

import Configuration.DatabaseConfiguration;
import Theater.Director;
import Theater.Spectacle.Musical;
import Theater.Spectacle.Spectacle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MusicalRepository {
    private static MusicalRepository singleInstance = null;

    private MusicalRepository() {}

    public static synchronized MusicalRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new MusicalRepository();
        return singleInstance;
    }

    public void getMusicals(List<Spectacle> spectacles) {
        String preparedSql = "SELECT name, duration, directorId FROM Spectacle JOIN Musical ON (Spectacle.id = Musical.id)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        DirectorRepository directorRepository = DirectorRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet musicalSet = statement.executeQuery();

            while (musicalSet.next())
            {
                String name = musicalSet.getString("name");
                String duration = musicalSet.getString("duration");
                Director director = directorRepository.getDirectorById(musicalSet.getInt("directorId"));
                Musical musical = new Musical(name, duration, director);
                spectacles.add(musical);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void insertMusical(Musical musical) {
        String preparedSql = "INSERT INTO Musical VALUES (?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(musical));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }
}
