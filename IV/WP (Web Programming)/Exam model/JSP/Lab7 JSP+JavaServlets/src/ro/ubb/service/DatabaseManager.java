package ro.ubb.service;

import ro.ubb.model.Asset;
import ro.ubb.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    public static final String URL = "jdbc:mysql://localhost/exam_model";
    public static final String USER = "root";
    public static final String PASSWORD = "";

    private Connection connection;

    public DatabaseManager() {
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch(Exception exception) {
            System.out.println("Connection Error: "+ exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if(!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User authenticateUser(String username, String password) {
        ResultSet resultSet;
        User user = null;
        try {
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(
                    String.format("SELECT * FROM users WHERE username='%s' AND password='%s'", username, password)
            );
            if (resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"));
            }
            resultSet.close();

            if (!statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<Asset> getUserAssets(int userId) {
        ResultSet resultSet;
        List<Asset> assets = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(
                    String.format("SELECT * FROM assets WHERE userid=%d", userId)
            );
            while (resultSet.next()) {
                Asset asset = new Asset(
                        resultSet.getInt("id"),
                        resultSet.getInt("userid"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("value")
                );
                assets.add(asset);
            }
            resultSet.close();

            if (!statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assets;
    }

    public boolean addUserAssets(List<Asset> assets) {
        try {
            String sql = "INSERT INTO assets(userid, name, description, value) VALUES";
            for (Asset asset: assets) {
                sql += String.format("(%d, '%s', '%s', %d),", asset.getUserId(), asset.getName(), asset.getDescription(), asset.getValue());
            }
            sql = sql.substring(0, sql.length() - 1);

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            if (!statement.isClosed()) {
                statement.close();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
