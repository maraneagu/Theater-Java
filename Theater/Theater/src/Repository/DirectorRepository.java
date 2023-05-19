package Repository;

import Configuration.DatabaseConfiguration;
import Theater.Director;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DirectorRepository {
    private static DirectorRepository singleInstance = null;

    private DirectorRepository() {}

    public static synchronized DirectorRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new DirectorRepository();
        return singleInstance;
    }

    public HashMap<Integer, Director> getDirectors() {
        String preparedSql = "SELECT * FROM Director";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        HashMap<Integer, Director> directors = new HashMap<>();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet directorSet = statement.executeQuery();

            while (directorSet.next())
            {
                String name = directorSet.getString("name");

                Director director = new Director(name);
                directors.put(directors.size() + 1, director);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return directors;
    }

    public Integer getIdByDirector(Director director) {
        String preparedSql = "SELECT id FROM Director WHERE name = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Integer id = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setString(1, director.getName());
            ResultSet directorSet = statement.executeQuery();

            if (directorSet.next())
                id = directorSet.getInt("id");
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return id;
    }

    public Director getDirectorById(Integer id) {
        String preparedSql = "SELECT * FROM Director WHERE id = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Director director = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setInt(1, id);
            ResultSet directorSet = statement.executeQuery();

            if (directorSet.next())
            {
                String name = directorSet.getString("name");
                director = new Director(name);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return director;
    }

    public void insertDirector(Director director) {
        String preparedSql = "INSERT INTO Director VALUES (null, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setString(1, director.getName());
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }
}
