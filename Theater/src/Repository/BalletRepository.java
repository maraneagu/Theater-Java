package Repository;

import Configuration.DatabaseConfiguration;
import Theater.Director;
import Theater.Spectacle.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BalletRepository {
    private static BalletRepository singleInstance = null;

    private BalletRepository() {}

    public static synchronized BalletRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new BalletRepository();
        return singleInstance;
    }

    public void getBallets(List<Spectacle> spectacles) {
        String preparedSql = "SELECT name, duration, directorId FROM Spectacle JOIN Ballet ON (Spectacle.id = Ballet.id)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        DirectorRepository directorRepository = DirectorRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet balletSet = statement.executeQuery();

            while (balletSet.next())
            {
                String name = balletSet.getString("name");
                String duration = balletSet.getString("duration");
                Director director = directorRepository.getDirectorById(balletSet.getInt("directorId"));
                Ballet ballet = new Ballet(name, duration, director);
                spectacles.add(ballet);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void insertBallet(Ballet ballet) {
        String preparedSql = "INSERT INTO Ballet VALUES (?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(ballet));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }
}
