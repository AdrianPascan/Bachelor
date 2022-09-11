package ro.ubb.service;

import ro.ubb.model.User;

import java.sql.*;

public class DatabaseManager {
    public static final String URL = "jdbc:mysql://localhost/wp2";
    public static final String USER = "root";
    public static final String PASSWORD = "";

    private Statement statement;


    public DatabaseManager() {
        connect();
    }

    public void connect() {
        try {
//            Class.forName("org.gjt.mm.mysql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
        } catch(Exception exception) {
            System.out.println("Connection Error: "+ exception.getMessage());
            exception.printStackTrace();
        }
    }

    public User authenticateUser(String username, String password) {
        ResultSet resultSet;
        User user = null;
        try {
            resultSet = statement.executeQuery(String.format("select * from users where username='%s' and password='%s'", username, password));
            if (resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void saveTime(int userId, long time) {
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(String.format("select * from sessions where userid='%d'", userId));
            if (resultSet.next()) {
                statement.executeUpdate(String.format("update sessions set time='%d' where userid='%d'", time, userId));
            } else {
                statement.executeUpdate(String.format("insert into sessions(userid, time) values('%d', '%d')", userId, time));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMove(int userId, int position, int score) {
        try {
            statement.executeUpdate(String.format("insert into moves(userid, position, score) values('%d', '%d', '%d')", userId, position, score));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMoves(int userId) {
        try {
            statement.executeUpdate(String.format("delete from moves where userid='%d'", userId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
