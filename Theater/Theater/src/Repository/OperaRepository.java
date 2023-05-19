package Repository;

import Configuration.DatabaseConfiguration;
import Theater.Director;
import Theater.Spectacle.Opera;
import Theater.Spectacle.Spectacle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OperaRepository {
    private static OperaRepository singleInstance = null;

    private OperaRepository() {}

    public static synchronized OperaRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new OperaRepository();
        return singleInstance;
    }

    public void getOperas(List<Spectacle> spectacles) {
        String preparedSql = "SELECT name, duration, directorId FROM Spectacle JOIN Opera ON (Spectacle.id = Opera.id)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        DirectorRepository directorRepository = DirectorRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet operaSet = statement.executeQuery();

            while (operaSet.next())
            {
                String name = operaSet.getString("name");
                String duration = operaSet.getString("duration");
                Director director = directorRepository.getDirectorById(operaSet.getInt("directorId"));
                Opera opera = new Opera(name, duration, director);
                spectacles.add(opera);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void insertOpera(Opera opera) {
        String preparedSql = "INSERT INTO Opera VALUES (?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(opera));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }
}
