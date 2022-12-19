package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/*import static jm.task.core.jdbc.util.Util.URL;
import static jm.task.core.jdbc.util.Util.USERNAME;
import static jm.task.core.jdbc.util.Util.PASSWORD;*/

public class UserDaoJDBCImpl extends Util implements UserDao {
    Connection conn = getConnection();

    public UserDaoJDBCImpl() {
    }
    public void createUsersTable() throws SQLException {
        try(Statement statement = conn.createStatement()) {
            statement.executeUpdate("create table if not exists Users (id int primary key not null auto_increment, name varchar(20) not null, lastName varchar(20) not null, age int not null)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void dropUsersTable() {
        try(Statement statement = conn.createStatement()) {
            statement.executeUpdate("drop table Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into Users (name, lastName, age) values (?, ?, ?)";
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeUserById(long id) {
        String sql = "delete from Users where id=?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try(Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from Users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(userList);
        return userList;
    }
    public void cleanUsersTable() {
        try(Statement statement = conn.createStatement()) {
            statement.executeUpdate("truncate table Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
