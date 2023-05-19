package Repository;

import Configuration.DatabaseConfiguration;
import Theater.Category;
import Theater.Director;
import Theater.Spectacle.Play;
import Theater.Spectacle.Spectacle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlayRepository {
    private static PlayRepository singleInstance = null;

    private PlayRepository() {}

    public static synchronized PlayRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new PlayRepository();
        return singleInstance;
    }

    public void getPlays(List<Spectacle> spectacles) {
        String preparedSql = "SELECT name, duration, directorId, categoryId FROM Spectacle JOIN Play ON (Spectacle.id = Play.id)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        DirectorRepository directorRepository = DirectorRepository.getInstance();
        CategoryRepository categoryRepository = CategoryRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet playSet = statement.executeQuery();

            while (playSet.next())
            {
                String name = playSet.getString("name");
                String duration = playSet.getString("duration");
                Director director = directorRepository.getDirectorById(playSet.getInt("directorId"));
                Category category = categoryRepository.getCategoryById(playSet.getInt("categoryId"));
                Play play = new Play(name, duration, director, category);
                spectacles.add(play);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void insertPlay(Play play) {
        String preparedSql = "INSERT INTO Play VALUES (?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();
        CategoryRepository categoryRepository = CategoryRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(play));
            statement.setInt(2, categoryRepository.getIdByCategory(play.getCategory()));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }
}
