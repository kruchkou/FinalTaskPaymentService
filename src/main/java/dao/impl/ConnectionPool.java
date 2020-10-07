package dao.impl;

import dao.DAOException;
import dao.IConnectionPool;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionPool implements IConnectionPool {

    private static ConnectionPool instance = null;

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    public Connection getConnection() throws DAOException {
        Context context;
        Connection connection = null;
        try {
            context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/dbprops"); //заменить!
            connection = ds.getConnection();
        } catch (NamingException e) {
            throw new DAOException("Can't get configuration file", e);
        } catch (SQLException e) {
            throw new DAOException("Can't connect to database", e);
        }
        return connection;
    }

    public void closeConnection(Connection connection, PreparedStatement ps) throws DAOException {
        try {
            if (connection != null) {
                ps.close();
            }
            if (ps != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DAOException("Can't close connection", e);
        }
    }
}
