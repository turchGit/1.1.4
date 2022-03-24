package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();

    }

    public void createUsersTable() {
        try {
            Statement statement =
                    connection.createStatement();
            statement.executeUpdate("CREATE TABLE Users (" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(20)," +
                    " lastName VARCHAR(20)," +
                    " age INT," +
                    "PRIMARY KEY (id))");
            connection.commit();
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Таблица уже существует");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE Users");
            connection.commit();

        } catch (SQLException e) {
            System.out.println("Таблицы не существует");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO Users VALUES (default ,?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
            System.out.println(String.format("Пользователь с именем %s добавлен в базу данных", name));
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM Users WHERE id=?");
            statement.setLong(1,id);
            statement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement =
                    connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setAge(resultSet.getByte("age"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                users.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM Users");
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Таблицы не существует");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }


    }
}
