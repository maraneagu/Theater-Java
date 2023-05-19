package Repository;

import Configuration.DatabaseConfiguration;
import Theater.Artist.Actor;
import Theater.Spectacle.Play;
import Theater.Spectacle.Spectacle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActorRepository {
    private static ActorRepository singleInstance = null;
    private ActorRepository() {}

    public static synchronized ActorRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new ActorRepository();
        return singleInstance;
    }

    public HashMap<Integer, Actor> getActors() {
        String preparedSql = "SELECT * FROM Actor";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        HashMap<Integer, Actor> actors = new HashMap<>();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet actorSet = statement.executeQuery();

            while (actorSet.next())
            {
                String name = actorSet.getString("name");

                Actor actor = new Actor(name);
                actors.put(actors.size() + 1, actor);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return actors;
    }

    public void getPlayActors(List<Spectacle> spectacles, Map<Integer, Actor> actors) {
        String preparedSql = "SELECT * FROM PlayActor";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet playActorSet = statement.executeQuery();

            while (playActorSet.next())
            {
                int playId = playActorSet.getInt("playId");
                int actorId = playActorSet.getInt("actorId");

                Play play = (Play) spectacleRepository.getSpectacleById(playId);
                Actor actor = getActorById(actorId);

                for (Spectacle spectacle : spectacles)
                    if (spectacle instanceof Play && spectacle.getName().equals(play.getName()))
                    {
                        ((Play) spectacle).getActors().add(actor);
                        break;
                    }

                for (Map.Entry<Integer, Actor> a : actors.entrySet())
                    if (a.getValue().getName().equalsIgnoreCase(actor.getName()))
                    {
                        a.getValue().getSpectacles().add(play);
                        break;
                    }
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public Integer getIdByActor(Actor actor) {
        String preparedSql = "SELECT id FROM Actor WHERE name = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Integer id = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setString(1, actor.getName());
            ResultSet actorSet = statement.executeQuery();

            if (actorSet.next())
                id = actorSet.getInt("id");
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return id;
    }

    public Actor getActorById(Integer id) {
        String preparedSql = "SELECT * FROM Actor WHERE id = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Actor actor = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setInt(1, id);
            ResultSet actorSet = statement.executeQuery();

            if (actorSet.next())
            {
                String name = actorSet.getString("name");
                actor = new Actor(name);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return actor;
    }

    public void insertActor(Actor actor) {
        String preparedSql = "INSERT INTO Actor VALUES (null, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setString(1, actor.getName());
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void insertPlayActor(Play play, Actor actor) {
        String preparedSql = "INSERT INTO PlayActor VALUES (?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(play));
            statement.setInt(2, getIdByActor(actor));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void deletePlayActor(Play play, Actor actor) {
        String preparedSql = "DELETE FROM PlayActor WHERE playId = ? AND actorId = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        SpectacleRepository spectacleRepository = SpectacleRepository.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setInt(1, spectacleRepository.getIdBySpectacle(play));
            statement.setInt(2, getIdByActor(actor));
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }
}
