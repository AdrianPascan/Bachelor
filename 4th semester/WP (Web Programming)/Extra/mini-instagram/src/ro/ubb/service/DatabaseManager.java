package ro.ubb.service;

import ro.ubb.model.Photo;
import ro.ubb.model.User;
import ro.ubb.model.Vote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    public static final String URL = "jdbc:mysql://localhost/mini-instagram";
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

    public List<Photo> getPhotos(int top) {
        ResultSet resultSet;
        List<Photo> photos = new ArrayList<>();

        try {
            String sql = null;

            Statement statement = connection.createStatement();

            if (top == -1) {
                sql = "SELECT p.id, p.source, p.votes, u.id AS userid, u.username " +
                      "FROM photos p INNER JOIN users u ON p.userid = u.id";
            }
            else {
                sql = String.format("SELECT p.id, p.source, p.votes, u.id AS userid, u.username " +
                                    "FROM photos p INNER JOIN users u ON p.userid = u.id " +
                                    "ORDER BY votes DESC " +
                                    "LIMIT %d", top);
            }

            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("userid"),
                        resultSet.getString("username"),
                        null
                );

                Photo photo = new Photo(
                        resultSet.getInt("id"),
                        user,
                        resultSet.getString("source"),
                        resultSet.getInt("votes")
                );

                photos.add(photo);
            }
            resultSet.close();

            if (!statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return photos;
    }

    public boolean addPhoto(Photo photo) {
        try {
            String sql =
                    String.format("INSERT INTO photos(userid, source) VALUES (%d, '%s')", photo.getUser().getId(), photo.getSource());

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

    public boolean votePhoto(Vote vote) {
        try {
            String sql;

            Statement statement = connection.createStatement();

            sql = String.format("SELECT * FROM votes WHERE userid=%d AND photoid=%d", vote.getUser().getId(), vote.getPhoto().getId());
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return false;
            }

            sql = String.format("INSERT INTO votes(userid, photoid, rank) VALUES(%d, %d, %d)",
                    vote.getUser().getId(), vote.getPhoto().getId(), vote.getRank());
            statement.executeUpdate(sql);

            sql = String.format("UPDATE photos SET votes=votes+%d WHERE id=%d", vote.getRank(), vote.getPhoto().getId());
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
