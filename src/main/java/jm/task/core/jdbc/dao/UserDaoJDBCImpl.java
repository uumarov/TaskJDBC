package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            String createUsersTableSql = "CREATE TABLE IF NOT EXISTS user"
                    + "(id bigint PRIMARY KEY AUTO_INCREMENT, name varchar(30),"
                    + "lastname varchar(30), age int)";
            statement.execute(createUsersTableSql);
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.err.println("Не удалось создать таблицу user");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    public void dropUsersTable() {
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            String dropUsersTableSql = "DROP TABLE IF EXISTS user";
            statement.execute(dropUsersTableSql);
            statement.close();
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.err.println("Не удалось удалить таблицу user");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement = null;
        String saveUserSql = "INSERT INTO user(name, lastname, age)"
                + " VALUES(?, ?, ?)";
        try {
            connection = Util.getConnection();
            preparedStatement = connection.prepareStatement(saveUserSql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.err.println("Не удалось добавить пользователя " + name);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    public void removeUserById(long id) {
        PreparedStatement preparedStatement = null;
        String removeUserSql = "DELETE FROM user WHERE id=?";
        try {
            connection = Util.getConnection();
            preparedStatement = connection.prepareStatement(removeUserSql);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException throwables) {
            System.err.println("Не удалось удалить пользователя с id:" + id);
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        String selectUsersSql = "SELECT * FROM user";
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectUsersSql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            connection.commit();
            return users;
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.err.println("Не удалось получить список пользователей");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return Collections.emptyList();
    }

    public void cleanUsersTable() {
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            String cleanUsersTableSql = "DELETE FROM user";
            statement.execute(cleanUsersTableSql);
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.err.println("Не удалось очистить таблицу user");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
}
