package Repository;

import Configuration.DatabaseConfiguration;
import Theater.Theater;
import Theater.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository singleInstance = null;

    private UserRepository() {}

    public static synchronized UserRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new UserRepository();
        return singleInstance;
    }

    public List<User> getUsers() {
        String preparedSql = "SELECT * FROM User";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        List<User> users = new ArrayList<>();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet userSet = statement.executeQuery();

            while (userSet.next())
            {
                String username = userSet.getString("username");
                String email = userSet.getString("email");
                String password = userSet.getString("password");
                String name = userSet.getString("name");

                User user = new User(username, email, password, name);
                users.add(user);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return users;
    }

    public void insertUser(User user) {
        String preparedSql = "INSERT INTO User VALUES (?, ?, ?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getName());

            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public void updateUser(String username, String email, String password, String name)
    {
        String preparedSql = "UPDATE User SET username = ?, email = ?, password = ?, name = ? WHERE username = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Theater theater = Theater.getInstance();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, name);
            statement.setString(5, theater.getUser().getUsername());
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }
}
