package Repository;

import Configuration.DatabaseConfiguration;
import Theater.Category;
import Theater.Director;
import Theater.Spectacle.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpectacleRepository {
    private static SpectacleRepository singleInstance = null;

    private SpectacleRepository() {}

    public static synchronized SpectacleRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new SpectacleRepository();
        return singleInstance;
    }

    public List<Spectacle> getSpectacles() {
        List<Spectacle> spectacles = new ArrayList<>();
        PlayRepository playRepository = PlayRepository.getInstance();
        OperaRepository operaRepository = OperaRepository.getInstance();
        MusicalRepository musicalRepository = MusicalRepository.getInstance();
        BalletRepository balletRepository = BalletRepository.getInstance();

        playRepository.getPlays(spectacles);
        operaRepository.getOperas(spectacles);
        musicalRepository.getMusicals(spectacles);
        balletRepository.getBallets(spectacles);

        return spectacles;
    }

    public int getIdBySpectacle(Spectacle spectacle) {
        String preparedSql = "SELECT id FROM Spectacle WHERE name = ? AND type = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Integer id = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setString(1, spectacle.getName());

            if (spectacle instanceof Play)
                statement.setString(2, "play");
            else if (spectacle instanceof Opera)
                statement.setString(2, "opera");
            else if (spectacle instanceof Musical)
                statement.setString(2, "musical");
            else statement.setString(2, "ballet");

            ResultSet spectacleSet = statement.executeQuery();

            if (spectacleSet.next())
                id = spectacleSet.getInt("id");
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return id;
    }

    public Spectacle getSpectacleById(Integer id) {
        String preparedSql = "SELECT * FROM Spectacle WHERE id = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        DirectorRepository directorRepository = DirectorRepository.getInstance();
        Spectacle spectacle = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setInt(1, id);
            ResultSet spectacleSet = statement.executeQuery();

            if (spectacleSet.next())
            {
                String name = spectacleSet.getString("name");
                String type = spectacleSet.getString("type");
                String duration = spectacleSet.getString("duration");
                Director director = directorRepository.getDirectorById(spectacleSet.getInt("directorId"));

                if (type.equals("play"))
                {
                    preparedSql = "SELECT * FROM Play JOIN Spectacle ON (Play.id = Spectacle.id)";
                    CategoryRepository categoryRepository = CategoryRepository.getInstance();

                    try
                    {
                        statement = databaseConnection.prepareStatement(preparedSql);
                        ResultSet categorySet = statement.executeQuery();

                        if (categorySet.next())
                        {
                            Category category = categoryRepository.getCategoryById(categorySet.getInt("categoryId"));
                            spectacle = new Play(name, duration, director, category);
                        }
                    }
                    catch (SQLException exception)
                    {
                        exception.printStackTrace();
                    }
                }
                else if (type.equals("opera"))
                    spectacle = new Opera(name, duration, director);
                else if (type.equals("musical"))
                    spectacle = new Musical(name, duration, director);
                else spectacle = new Ballet(name, duration, director);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return spectacle;
    }

    public void insertSpectacle(Spectacle spectacle) {
        String preparedSql = "INSERT INTO Spectacle VALUES (null, ?, ?, ?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        DirectorRepository directorRepository = DirectorRepository.getInstance();
        PlayRepository playRepository = PlayRepository.getInstance();
        OperaRepository operaRepository = OperaRepository.getInstance();
        MusicalRepository musicalRepository = MusicalRepository.getInstance();
        BalletRepository balletRepository = BalletRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            if (spectacle instanceof Play)
                statement.setString(1, "play");
            else if (spectacle instanceof Opera)
                statement.setString(1, "opera");
            else if (spectacle instanceof Musical)
                statement.setString(1, "musical");
            else statement.setString(1, "ballet");

            statement.setString(2, spectacle.getName());
            statement.setString(3, spectacle.getDuration());
            statement.setInt(4, directorRepository.getIdByDirector(spectacle.getDirector()));
            statement.executeUpdate();

            if (spectacle instanceof Play)
                playRepository.insertPlay((Play) spectacle);
            else if (spectacle instanceof Opera)
                operaRepository.insertOpera((Opera) spectacle);
            else if (spectacle instanceof Musical)
                musicalRepository.insertMusical((Musical) spectacle);
            else
                balletRepository.insertBallet((Ballet) spectacle);

        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void deleteSpectacle(Spectacle spectacle) {
        String preparedSql = "DELETE FROM Spectacle WHERE id = ? AND type = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            if (spectacle instanceof Play)
            {
                statement.setInt(1, getIdBySpectacle(spectacle));
                statement.setString(2, "play");
            }
            else if (spectacle instanceof Opera)
            {
                statement.setInt(1, getIdBySpectacle(spectacle));
                statement.setString(2, "opera");
            }
            else if (spectacle instanceof Musical)
            {
                statement.setInt(1, getIdBySpectacle(spectacle));
                statement.setString(2, "musical");
            }
            else
            {
                statement.setInt(1, getIdBySpectacle(spectacle));
                statement.setString(2, "ballet");
            }

            statement.executeUpdate();

        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }
}
