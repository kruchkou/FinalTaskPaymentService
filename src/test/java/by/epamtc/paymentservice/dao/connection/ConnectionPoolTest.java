package by.epamtc.paymentservice.dao.connection;

import by.epamtc.paymentservice.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;

public interface ConnectionPoolTest {

    public Connection getConnection() throws DAOException;

    public void closeConnection(Connection connection, PreparedStatement ps) throws DAOException;
}
