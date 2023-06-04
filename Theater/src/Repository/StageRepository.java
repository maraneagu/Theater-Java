package Repository;

import Configuration.DatabaseConfiguration;
import Theater.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class StageRepository {
    private static StageRepository singleInstance = null;

    private StageRepository() {}

    public static synchronized StageRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new StageRepository();
        return singleInstance;
    }

    public HashMap<Integer, Stage> getStages() {
        String preparedSql = "SELECT * FROM Stage";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        HashMap<Integer, Stage> stages = new HashMap<>();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet stageSet = statement.executeQuery();

            while (stageSet.next())
            {
                String name = stageSet.getString("name");
                Integer numberOfRows = stageSet.getInt("numberOfRows");
                Integer numberOfSeatsPerRow = stageSet.getInt("numberOfSeatsPerRow");

                Stage stage = new Stage(name, numberOfRows, numberOfSeatsPerRow);
                stages.put(stages.size() + 1, stage);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return stages;
    }

    public Integer getIdByStage(Stage stage) {
        String preparedSql = "SELECT id FROM Stage WHERE name = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Integer id = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setString(1, stage.getName());
            ResultSet stageSet = statement.executeQuery();

            if (stageSet.next())
                id = stageSet.getInt("id");
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return id;
    }

    public Stage getStageById(Integer id) {
        String preparedSql = "SELECT * FROM Stage WHERE id = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Stage stage = null;

        try {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setInt(1, id);
            ResultSet stageSet = statement.executeQuery();

            if (stageSet.next()) {
                String name = stageSet.getString("name");
                int numberOfRows = stageSet.getInt("numberOfRows");
                int numberOfSeatsPerRow = stageSet.getInt("numberOfSeatsPerRow");
                stage = new Stage(name, numberOfRows, numberOfSeatsPerRow);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return stage;
    }
}
