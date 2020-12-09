package by.epamtc.paymentservice.dao.connection;

import by.epamtc.paymentservice.dao.exception.DAOException;

import java.sql.Connection;

public interface IConnectionPool {

    public Connection getConnection() throws DAOException;
}
