package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import Configuration.DatabaseConfiguration;
import Service.TheaterService;
import Theater.Category;
import Theater.Director;

public class CategoryRepository {
    private static CategoryRepository singleInstance = null;

    private CategoryRepository() {}

    public static synchronized CategoryRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new CategoryRepository();
        return singleInstance;
    }

    public HashMap<Integer, Category> getCategories() {
        String preparedSql = "SELECT * FROM Category";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        HashMap<Integer, Category> categories = new HashMap<>();

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            ResultSet categorySet = statement.executeQuery();

            while (categorySet.next())
            {
                String name = categorySet.getString("name");

                Category category = new Category(name);
                categories.put(categories.size() + 1, category);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return categories;
    }

    public Integer getIdByCategory(Category category) {
        String preparedSql = "SELECT id FROM Category WHERE name = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Integer id = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setString(1, category.getName());
            ResultSet categorySet = statement.executeQuery();

            if (categorySet.next())
                id = categorySet.getInt("id");
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return id;
    }

    public Category getCategoryById(Integer id) {
        String preparedSql = "SELECT * FROM Category WHERE id = ?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        Category category = null;

        try
        {
            PreparedStatement statement = databaseConnection.prepareStatement(preparedSql);
            statement.setInt(1, id);
            ResultSet categorySet = statement.executeQuery();

            if (categorySet.next())
            {
                String name = categorySet.getString("name");
                category = new Category(name);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return category;
    }
}
