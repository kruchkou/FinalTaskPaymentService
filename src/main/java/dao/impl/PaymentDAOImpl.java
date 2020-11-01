package dao.impl;

import dao.exception.DAOException;
import dao.PaymentDAO;
import dao.entity.Payment;
import dao.connection.impl.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String INSERT_PAYMENT_SQL = "INSERT INTO Payments(card_from,account_to,amount,datetime,type,comment) VALUES (?,?,?,?,?,?)";
    private static final String GET_PAYMENT_BY_ID_SQL = "SELECT payments.id, card_from, account_to, amount, datetime, payments.type, types.name, comment FROM Payments payments " +
            "JOIN PaymentTypes types ON payments.id = types.id " +
            "WHERE payments.id = ?";
    private static final String GET_OUT_PAYMENT_LIST_BY_ACCOUNT_ID = "SELECT payments.id, card_from, account_to, amount, datetime, payments.type, types.name, comment FROM Payments payments " +
            "JOIN PaymentTypes types ON payments.id = types.id " +
            "JOIN Cards cards ON payments.card_from = cards.id " +
            "WHERE cards.account = ?";
    private static final String GET_IN_PAYMENT_LIST_BY_ACCOUNT_ID = "SELECT payments.id, card_from, account_to, amount, datetime, payments.type, types.name, comment FROM Payments payments " +
            "JOIN PaymentTypes types ON payments.id = types.id " +
            "WHERE account_to = ?";

    public PaymentDAOImpl() {
    }

    @Override
    public List<Payment> getInPaymentListByAccountID(int accountID) throws DAOException {
        return getPaymentListByAccountID(accountID, GET_IN_PAYMENT_LIST_BY_ACCOUNT_ID);
    }

    @Override
    public List<Payment> getOutPaymentListByAccountID(int accountID) throws DAOException {
        return getPaymentListByAccountID(accountID, GET_OUT_PAYMENT_LIST_BY_ACCOUNT_ID);
    }

    private List<Payment> getPaymentListByAccountID(int accountID, String sqlStatement) throws DAOException {
        List<Payment> paymentList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(sqlStatement);

            ps.setInt(GetInPaymentListByAccountIDIndex.accountID, accountID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Payment payment = new Payment();

                HashMap<Integer, String> type = new HashMap<>();
                type.put((rs.getInt(ParamColumn.typeID)), rs.getString(ParamColumn.typeName));

                payment.setId(rs.getInt(ParamColumn.id));
                payment.setCardFrom(rs.getInt(ParamColumn.cardFrom));
                payment.setAccountTo(rs.getInt(ParamColumn.accountTo));
                payment.setAmount(rs.getBigDecimal(ParamColumn.amount));
                payment.setDatetime(rs.getDate(ParamColumn.datetime));
                payment.setType(type);
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

    @Override
    public void addPayment(Payment payment, int paymentType) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(INSERT_PAYMENT_SQL);

            ps.setInt(AddPaymentIndex.cardFrom, payment.getCardFrom());
            ps.setInt(AddPaymentIndex.accountTo, payment.getAccountTo());
            ps.setBigDecimal(AddPaymentIndex.amount, payment.getAmount());
            ps.setDate(AddPaymentIndex.datetime, new Date(payment.getDatetime().getTime()));
            ps.setInt(AddPaymentIndex.type, paymentType);
            ps.setString(AddPaymentIndex.comment, payment.getComment());

            ps.execute();
        } catch (SQLException e) {
            throw new DAOException("Can't handle PaymentDAO.addPayment request");
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
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
                HashMap<Integer, String> type = new HashMap<>();
                type.put((rs.getInt(ParamColumn.typeID)), rs.getString(ParamColumn.typeName));

                payment.setId(rs.getInt(ParamColumn.id));
                payment.setAccountTo(rs.getInt(ParamColumn.accountTo));
                payment.setCardFrom(rs.getInt(ParamColumn.cardFrom));
                payment.setAmount(rs.getBigDecimal(ParamColumn.amount));
                payment.setDatetime(rs.getDate(ParamColumn.datetime));
                payment.setType(type);
                payment.setComment(rs.getString(ParamColumn.comment));
            }
        } catch (SQLException e) {
            throw new DAOException("Can't handle PaymentDAO.getPaymentByID request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return payment;
    }

    private static class ParamColumn {
        private static final String id = "payments.id";
        private static final String cardFrom = "card_from";
        private static final String accountTo = "account_to";
        private static final String amount = "amount";
        private static final String datetime = "datetime";
        private static final String typeID = "payments.type";
        private static final String typeName = "types.name";
        private static final String comment = "comment";
    }

    private static class AddPaymentIndex {
        public static final int cardFrom = 1;
        private static final int accountTo = 2;
        public static final int amount = 3;
        public static final int datetime = 4;
        public static final int type = 5;
        public static final int comment = 6;
    }

    private static class GetPaymentByIDIndex {
        private static final int id = 1;
    }

    private static class GetInPaymentListByAccountIDIndex {
        private static final int accountID = 1;
    }

    private static class SetStatusByIDIndex {
        private static final int status = 1;
        private static final int id = 2;
    }

}
