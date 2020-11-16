package by.epamtc.PaymentService.dao.impl;

import by.epamtc.PaymentService.dao.connection.impl.ConnectionPool;
import by.epamtc.PaymentService.bean.Payment;
import by.epamtc.PaymentService.dao.exception.DAOException;
import by.epamtc.PaymentService.dao.PaymentDAO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SET_ACC_BALANCE_BY_ID_SQL = "UPDATE Accounts SET balance = ? WHERE (id = ? AND status = ?)";
    private static final String GET_ACC_BALANCE_SQL = "SELECT balance FROM Accounts where (id = ? AND status = ?)"; //FOR UPDATE

    private static final String INSERT_PAYMENT_SQL = "INSERT INTO Payments(account_from,account_to,amount,datetime,comment) VALUES (?,?,?,?,?)";
    private static final String GET_PAYMENT_BY_ID_SQL = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM Payments payments WHERE payments.id = ?";
    private static final String GET_OUT_PAYMENT_LIST_BY_ACCOUNT_ID = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM Payments payments WHERE account_from = ?";
    private static final String GET_IN_PAYMENT_LIST_BY_ACCOUNT_ID = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM Payments payments WHERE account_to = ?";
    private static final String GET_OUT_PAYMENT_LIST_BY_USER_ID = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM Payments payments " +
            "JOIN Accounts accounts ON payments.account_from = accounts.id " +
            "WHERE accounts.user = ?";
    private static final String GET_IN_PAYMENT_LIST_BY_USER_ID = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM Payments payments " +
            "JOIN Accounts accounts ON payments.account_to = accounts.id " +
            "WHERE accounts.user = ?";


    public PaymentDAOImpl() {
    }

    @Override
    public List<Payment> getInPaymentListByUserID(int userID) throws DAOException {
        return getPaymentListByID(userID, GET_IN_PAYMENT_LIST_BY_USER_ID);
    }

    @Override
    public List<Payment> getOutPaymentListByUserID(int userID) throws DAOException {
        return getPaymentListByID(userID, GET_OUT_PAYMENT_LIST_BY_USER_ID);
    }

    @Override
    public List<Payment> getInPaymentListByAccountID(int accountID) throws DAOException {
        return getPaymentListByID(accountID, GET_IN_PAYMENT_LIST_BY_ACCOUNT_ID);
    }

    @Override
    public List<Payment> getOutPaymentListByAccountID(int accountID) throws DAOException {
        return getPaymentListByID(accountID, GET_OUT_PAYMENT_LIST_BY_ACCOUNT_ID);
    }

    private List<Payment> getPaymentListByID(int id, String sqlStatement) throws DAOException {
        List<Payment> paymentList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(sqlStatement);

            ps.setInt(GetPaymentListByIDIndex.id, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Payment payment = new Payment();

                payment.setId(rs.getInt(ParamColumn.id));
                payment.setAccountFrom(rs.getInt(ParamColumn.accountFrom));
                payment.setAccountTo(rs.getInt(ParamColumn.accountTo));
                payment.setAmount(rs.getBigDecimal(ParamColumn.amount));
                payment.setDatetime(rs.getDate(ParamColumn.datetime));
                payment.setComment(rs.getString(ParamColumn.comment));

                paymentList.add(payment);
            }
        } catch (SQLException e) {
            throw new DAOException("Cant handle PaymentDAO.getPaymentListByAccountID request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return paymentList;
    }

    public Payment getPaymentByID(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        Payment payment = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_PAYMENT_BY_ID_SQL);
            ps.setInt(GetPaymentByIDIndex.id, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                payment = new Payment();
                payment.setId(rs.getInt(ParamColumn.id));
                payment.setAccountFrom(rs.getInt(ParamColumn.accountFrom));
                payment.setAccountTo(rs.getInt(ParamColumn.accountTo));
                payment.setAmount(rs.getBigDecimal(ParamColumn.amount));
                payment.setDatetime(rs.getDate(ParamColumn.datetime));
                payment.setComment(rs.getString(ParamColumn.comment));
            }
        } catch (SQLException e) {
            throw new DAOException("Can't handle PaymentDAO.getPaymentByID request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return payment;
    }

    @Override
    public void transferMoney(int accountFromID, int accountToID, BigDecimal amount, String comment) throws DAOException {
        final int ACCOUNT_STATUS_OPEN = 1;
        Connection connection = null;
        PreparedStatement ps = null;
        BigDecimal balanceFrom;
        BigDecimal balanceTo;

        connection = connectionPool.getConnection();
        try {
            connection.setAutoCommit(false);

            ps = connection.prepareStatement(GET_ACC_BALANCE_SQL);
            ps.setInt(GetAccBalanceIndex.id, accountFromID);
            ps.setInt(GetAccBalanceIndex.status, ACCOUNT_STATUS_OPEN);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                throw new DAOException("Can't get accountFrom balance");
            }
            balanceFrom = rs.getBigDecimal(AccParamColumn.balance);

            rs.close();
            ps.close();

            final boolean ENOUGH_MONEY = balanceFrom.compareTo(amount) > 0;
            if (!ENOUGH_MONEY) {
                throw new DAOException("Not enough balance to make a transfer");
            }

            ps = connection.prepareStatement(SET_ACC_BALANCE_BY_ID_SQL);
            ps.setBigDecimal(SetAccBalanceIndex.balance, balanceFrom.subtract(amount));
            ps.setInt(SetAccBalanceIndex.id, accountFromID);
            ps.setInt(SetAccBalanceIndex.status, ACCOUNT_STATUS_OPEN);
            ps.executeUpdate();

            ps.close();

            ps = connection.prepareStatement(GET_ACC_BALANCE_SQL);
            ps.setInt(GetAccBalanceIndex.id, accountToID);
            ps.setInt(GetAccBalanceIndex.status, ACCOUNT_STATUS_OPEN);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new DAOException("Can't get accountTo balance");
            }
            balanceTo = rs.getBigDecimal(AccParamColumn.balance);

            rs.close();
            ps.close(); //нужно ли?

            ps = connection.prepareStatement(SET_ACC_BALANCE_BY_ID_SQL);
            ps.setBigDecimal(SetAccBalanceIndex.balance, balanceTo.add(amount));
            ps.setInt(SetAccBalanceIndex.id, accountToID);
            ps.setInt(SetAccBalanceIndex.status, ACCOUNT_STATUS_OPEN);
            ps.executeUpdate();

            ps.close();

            ps = connection.prepareStatement(INSERT_PAYMENT_SQL);

            ps.setInt(AddPaymentIndex.cardFrom, accountFromID);
            ps.setInt(AddPaymentIndex.accountTo, accountToID);
            ps.setBigDecimal(AddPaymentIndex.amount, amount);
            ps.setDate(AddPaymentIndex.datetime, new java.sql.Date(new java.util.Date().getTime()));
            ps.setString(AddPaymentIndex.comment, comment);
            ps.execute();

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw new DAOException("Can't handle TransactionDAO.rollback request", exception);
            }
            throw new DAOException("Can't handle TransfactionDAO.transferMoney request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);

        }

    }

    private static class ParamColumn {
        private static final String id = "payments.id";
        private static final String accountFrom = "account_from";
        private static final String accountTo = "account_to";
        private static final String amount = "amount";
        private static final String datetime = "datetime";
        private static final String comment = "comment";
    }

    private static class GetPaymentByIDIndex {
        private static final int id = 1;
    }

    private static class GetPaymentListByIDIndex {
        private static final int id = 1;
    }

    private static class AddPaymentIndex {
        public static final int cardFrom = 1;
        private static final int accountTo = 2;
        public static final int amount = 3;
        public static final int datetime = 4;
        public static final int comment = 5;
    }

    private static class SetAccBalanceIndex {
        private static final int balance = 1;
        private static final int id = 2;
        private static final int status = 3;
    }

    private static class GetAccBalanceIndex {
        private static final int id = 1;
        private static final int status = 2;
    }

    private static class AccParamColumn {
        private static final String balance = "balance";
    }

}
