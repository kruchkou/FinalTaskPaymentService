package by.epamtc.paymentservice.dao.connection.impl;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import by.epamtc.paymentservice.dao.exception.ConnectionPoolInitError;
import by.epamtc.paymentservice.dao.exception.DAOException;

public class ConnectionPoolImpl implements by.epamtc.paymentservice.dao.connection.ConnectionPool {

    private static final ConnectionPoolImpl instance = new ConnectionPoolImpl();

    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final int CONNECTION_COUNT = 5;

    private final String DB_URL = "db.url";
    private final String DB_USER = "db.user";
    private final String DB_PASSWORD = "db.password";

    private final String MESSAGE_DRIVER_NOT_FOUND = "Driver not found";
    private final String MESSAGE_SQL_PROBLEM = "Sql connection problem";
    private final String MESSAGE_NO_FREE_CONNECTION = "No free connection";
    private final String MESSAGE_CONNECTION_CLOSING_PROBLEM = "Closing connection problem.";

    private BlockingQueue<Connection> freeConnections;
    private BlockingQueue<Connection> activeConnections;

    private ConnectionPoolImpl() {
        initPool();
    }

    public static ConnectionPoolImpl getInstance() {
        return instance;
    }

    public void initPool() throws ConnectionPoolInitError {

        ResourceBundle resourceBundle = ResourceBundle.getBundle("db", Locale.getDefault());

        String url = resourceBundle.getString(DB_URL);
        String user = resourceBundle.getString(DB_USER);
        String password = resourceBundle.getString(DB_PASSWORD);


        freeConnections = new ArrayBlockingQueue<Connection>(CONNECTION_COUNT);
        activeConnections = new ArrayBlockingQueue<Connection>(CONNECTION_COUNT);

        try {
            Class.forName(DRIVER);

            for (int count = 0; count < CONNECTION_COUNT; count++) {

                Connection connection;
                connection = DriverManager.getConnection(url, user, password);

                freeConnections.add(connection);
            }

        } catch (ClassNotFoundException e) {

            throw new ConnectionPoolInitError(MESSAGE_DRIVER_NOT_FOUND, e);

        } catch (SQLException e) {

            throw new ConnectionPoolInitError(MESSAGE_SQL_PROBLEM, e);
        }
    }

    public Connection getConnection() throws DAOException {

        Connection connection;
        connection = freeConnections.poll();

        if (connection == null) {

            throw new DAOException(MESSAGE_NO_FREE_CONNECTION);
        }

        activeConnections.add(connection);

        return connection;
    }

    public void releaseConnection(Connection connection) {

        activeConnections.remove(connection);
        freeConnections.add(connection);

    }

    public void clearConnectionPool() throws DAOException {

        closeConnectionQueue(freeConnections);
        closeConnectionQueue(activeConnections);

    }

    private void closeConnectionQueue(BlockingQueue<Connection> connectionQueue) throws DAOException {

        Connection connection;

        while ((connection = connectionQueue.poll()) != null) {
            try {
                connection.close();

            } catch (SQLException e) {
                throw new DAOException(MESSAGE_CONNECTION_CLOSING_PROBLEM, e);
            }
        }

    }

    public void closeConnection(Connection connection, PreparedStatement ps) throws DAOException {
        try {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                releaseConnection(connection);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_CONNECTION_CLOSING_PROBLEM, e);
        }
    }


}
