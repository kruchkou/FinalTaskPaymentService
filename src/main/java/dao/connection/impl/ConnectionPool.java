package dao.connection.impl;

import dao.exception.DAOException;
import dao.connection.IConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConnectionPool implements IConnectionPool {

    private static ConnectionPool instance = new ConnectionPool();

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public Connection getConnection() throws DAOException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ResourceBundle resourceBundle = ResourceBundle.getBundle("db", Locale.getDefault());
            String url = resourceBundle.getString("db.url");
            String user = resourceBundle.getString("db.user");
            String password = resourceBundle.getString("db.password");

            connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            throw new DAOException("Can't connect to database", e);
        } catch (ClassNotFoundException e) {
            throw new DAOException("Cant' load jdbc driver", e);
        }
        return connection;
    }

    public void closeConnection(Connection connection, PreparedStatement ps) throws DAOException {
        try {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DAOException("Can't close connection", e);
        }
    }
}