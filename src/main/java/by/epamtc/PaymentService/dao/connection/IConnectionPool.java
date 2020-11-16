package by.epamtc.PaymentService.dao.connection;

import by.epamtc.PaymentService.dao.exception.DAOException;

import java.sql.Connection;

public interface IConnectionPool {

    public Connection getConnection() throws DAOException;
}
