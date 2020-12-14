package by.epamtc.paymentservice.dao.impl;

import by.epamtc.paymentservice.bean.Payment;
import by.epamtc.paymentservice.dao.PaymentDAO;
import by.epamtc.paymentservice.dao.connection.ConnectionPool;
import by.epamtc.paymentservice.dao.connection.impl.ConnectionPoolImpl;
import by.epamtc.paymentservice.dao.exception.DAOException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link PaymentDAO}. Provides methods to interact with Users data from database.
 * Methods connect to database using {@link Connection} from {@link ConnectionPoolImpl} and manipulate with data(save, edit, etc.).
 *
 */
public class PaymentDAOImpl implements PaymentDAO {

    /** Instance of the class */
    private static final PaymentDAOImpl instance = new PaymentDAOImpl();

    /** An object of {@link ConnectionPoolImpl} */
    private static final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    /** Query for database to set account balance by account ID */
    private static final String SET_ACC_BALANCE_BY_ID_SQL = "UPDATE Accounts SET balance = ? WHERE (id = ? AND status = ?)";

    /** Query for database to get account balance by account ID */
    private static final String GET_ACC_BALANCE_SQL = "SELECT balance FROM Accounts where (id = ? AND status = ?)"; //FOR UPDATE

    /** Query for database to add an payment */
    private static final String INSERT_PAYMENT_SQL = "INSERT INTO Payments(account_from,account_to,amount,datetime,comment) VALUES (?,?,?,?,?)";

    /** Query for database to get payment data by payment ID */
    private static final String GET_PAYMENT_BY_ID_SQL = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM Payments payments WHERE payments.id = ?";

    /** Query for database to get out payment list from account ID */
    private static final String GET_OUT_PAYMENT_LIST_BY_ACCOUNT_ID = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM Payments payments WHERE account_from = ?";

    /** Query for database to get in payment list to account ID */
    private static final String GET_IN_PAYMENT_LIST_BY_ACCOUNT_ID = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM Payments payments WHERE account_to = ?";

    /** Query for database to get out payment list by accounts that owned by user ID */
    private static final String GET_OUT_PAYMENT_LIST_BY_USER_ID = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM Payments payments " +
            "JOIN Accounts accounts ON payments.account_from = accounts.id " +
            "WHERE accounts.user = ?";

    /** Query for database to get in payment list by accounts that owned by user ID */
    private static final String GET_IN_PAYMENT_LIST_BY_USER_ID = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM Payments payments " +
            "JOIN Accounts accounts ON payments.account_to = accounts.id " +
            "WHERE accounts.user = ?";


    /** Message, that is putted in Exception if there are get payment list by account ID problem */
    private static final String MESSAGE_GET_PAYMENT_LIST_BY_ACCOUNT_ID_PROBLEM = "Cant handle PaymentDAO.getPaymentListByAccountID request";

    /** Message, that is putted in Exception if there are get payment by ID problem */
    private static final String MESSAGE_GET_PAYMENT_BY_ID_PROBLEM = "Can't handle PaymentDAO.getPaymentByID request";

    /** Message, that is putted in Exception if there are get accountFrom balance problem */
    private static final String MESSAGE_GET_ACCOUNT_FROM_BALANCE_PROBLEM = "Can't get accountFrom balance";

    /** Message, that is putted in Exception if there are not enough balance problem problem */
    private static final String MESSAGE_NOT_ENOUGH_BALANCE_PROBLEM = "Not enough balance to make a transfer";

    /** Message, that is putted in Exception if there are get accountTo balance problem */
    private static final String MESSAGE_GET_ACCOUNT_TO_BALANCE_PROBLEM = "Can't get accountTo balance";

    /** Message, that is putted in Exception if there are transfer money rollback problem */
    private static final String MESSAGE_TRANSFER_MONEY_ROLLBACK_PROBLEM = "Can't handle TransactionDAO.transferMoney.rollback request";

    /** Message, that is putted in Exception if there are transfer money problem */
    private static final String MESSAGE_TRANSFER_MONEY_PROBLEM = "Can't handle TransfactionDAO.transferMoney request";


    /**
     * Returns the instance of the class
     * @return Object of {@link PaymentDAOImpl}
     */
    public static PaymentDAOImpl getInstance() {
        return instance;
    }

    /** Private constructor without parameters */
    private PaymentDAOImpl() {

    }

    /**
     * Connects to database and returns List of {@link Payment} objects by user ID that receives payments.
     *
     * @param userID     is ID of the User that received payments.
     * @return List of {@link Payment} with all matching payments.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<Payment> getInPaymentListByUserID(int userID) throws DAOException {
        return getPaymentListByID(userID, GET_IN_PAYMENT_LIST_BY_USER_ID);
    }

    /**
     * Connects to database and returns List of {@link Payment} objects by user ID that sends payments.
     *
     * @param userID     is ID of the User that received payments.
     * @return List of {@link Payment} with all matching payments.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<Payment> getOutPaymentListByUserID(int userID) throws DAOException {
        return getPaymentListByID(userID, GET_OUT_PAYMENT_LIST_BY_USER_ID);
    }

    /**
     * Connects to database and returns List of {@link Payment} objects by account ID that receives payments.
     *
     * @param accountID     is ID of the Account that received payments.
     * @return List of {@link Payment} with all matching payments.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<Payment> getInPaymentListByAccountID(int accountID) throws DAOException {
        return getPaymentListByID(accountID, GET_IN_PAYMENT_LIST_BY_ACCOUNT_ID);
    }

    /**
     * Connects to database and returns List of {@link Payment} objects by account ID that sends payments.
     *
     * @param accountID     is ID of the Account that sends payments.
     * @return List of {@link Payment} with all matching payments.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<Payment> getOutPaymentListByAccountID(int accountID) throws DAOException {
        return getPaymentListByID(accountID, GET_OUT_PAYMENT_LIST_BY_ACCOUNT_ID);
    }


    /**
     * Connects to database and return list of payments by provided ID and SQL request
     *
     * @param id    is linked ID
     * @param sqlStatement   is text that contains SQL request.
     * @return List of {@link Payment} with all matching cards.
     * @throws DAOException when problems with database connection occurs.
     */
    private List<Payment> getPaymentListByID(int id, String sqlStatement) throws DAOException {
        List<Payment> paymentList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(sqlStatement);

            ps.setInt(GetPaymentListByIDIndex.ID, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Payment payment = new Payment();

                payment.setId(rs.getInt(ParamColumn.ID));
                payment.setAccountFrom(rs.getInt(ParamColumn.ACCOUNT_FROM));
                payment.setAccountTo(rs.getInt(ParamColumn.ACCOUNT_TO));
                payment.setAmount(rs.getBigDecimal(ParamColumn.AMOUNT));
                payment.setDatetime(rs.getDate(ParamColumn.DATETIME));
                payment.setComment(rs.getString(ParamColumn.COMMENT));

                paymentList.add(payment);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_GET_PAYMENT_LIST_BY_ACCOUNT_ID_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return paymentList;
    }

    /**
     * Connects to database and returns payment data by ID.
     *
     * @param id    is Payment ID value.
     * @return {@link Payment} if payment data found, null if not.
     * @throws DAOException when problems with database connection occurs.
     */
    public Payment getPayment(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        Payment payment = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_PAYMENT_BY_ID_SQL);
            ps.setInt(GetPaymentByIDIndex.ID, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                payment = new Payment();
                payment.setId(rs.getInt(ParamColumn.ID));
                payment.setAccountFrom(rs.getInt(ParamColumn.ACCOUNT_FROM));
                payment.setAccountTo(rs.getInt(ParamColumn.ACCOUNT_TO));
                payment.setAmount(rs.getBigDecimal(ParamColumn.AMOUNT));
                payment.setDatetime(rs.getDate(ParamColumn.DATETIME));
                payment.setComment(rs.getString(ParamColumn.COMMENT));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_GET_PAYMENT_BY_ID_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return payment;
    }


    /**
     * Connects to database and transfers money from one account to another.
     *
     * @param accountFromID     is ID of the Account that pays.
     * @param accountToID     is ID of the Account that receives payment.
     * @param amount     is {@link BigDecimal} object that contains amount of payment.
     * @param comment     is text contains comment for payment.
     * @throws DAOException when problems with database connection occurs.
     */
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
            ps.setInt(GetAccBalanceIndex.ID, accountFromID);
            ps.setInt(GetAccBalanceIndex.STATUS, ACCOUNT_STATUS_OPEN);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                throw new DAOException(MESSAGE_GET_ACCOUNT_FROM_BALANCE_PROBLEM);
            }
            balanceFrom = rs.getBigDecimal(AccParamColumn.BALANCE);

            rs.close();
            ps.close();

            final boolean ENOUGH_MONEY = balanceFrom.compareTo(amount) > 0;
            if (!ENOUGH_MONEY) {
                throw new DAOException(MESSAGE_NOT_ENOUGH_BALANCE_PROBLEM);
            }

            ps = connection.prepareStatement(SET_ACC_BALANCE_BY_ID_SQL);
            ps.setBigDecimal(SetAccBalanceIndex.BALANCE, balanceFrom.subtract(amount));
            ps.setInt(SetAccBalanceIndex.ID, accountFromID);
            ps.setInt(SetAccBalanceIndex.STATUS, ACCOUNT_STATUS_OPEN);
            ps.executeUpdate();

            ps.close();

            ps = connection.prepareStatement(GET_ACC_BALANCE_SQL);
            ps.setInt(GetAccBalanceIndex.ID, accountToID);
            ps.setInt(GetAccBalanceIndex.STATUS, ACCOUNT_STATUS_OPEN);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new DAOException(MESSAGE_GET_ACCOUNT_TO_BALANCE_PROBLEM);
            }
            balanceTo = rs.getBigDecimal(AccParamColumn.BALANCE);

            rs.close();
            ps.close(); //нужно ли?

            ps = connection.prepareStatement(SET_ACC_BALANCE_BY_ID_SQL);
            ps.setBigDecimal(SetAccBalanceIndex.BALANCE, balanceTo.add(amount));
            ps.setInt(SetAccBalanceIndex.ID, accountToID);
            ps.setInt(SetAccBalanceIndex.STATUS, ACCOUNT_STATUS_OPEN);
            ps.executeUpdate();

            ps.close();

            ps = connection.prepareStatement(INSERT_PAYMENT_SQL);

            ps.setInt(AddPaymentIndex.CARD_FROM, accountFromID);
            ps.setInt(AddPaymentIndex.ACCOUNT_TO, accountToID);
            ps.setBigDecimal(AddPaymentIndex.AMOUNT, amount);
            ps.setDate(AddPaymentIndex.DATETIME, new java.sql.Date(new java.util.Date().getTime()));
            ps.setString(AddPaymentIndex.COMMENT, comment);
            ps.execute();

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw new DAOException(MESSAGE_TRANSFER_MONEY_ROLLBACK_PROBLEM, exception);
            }
            throw new DAOException(MESSAGE_TRANSFER_MONEY_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);

        }

    }

    /**
     * Static class that contains parameters of Accounts table.
     */
    private static class AccParamColumn {
        private static final String BALANCE = "balance";
    }

    /**
     * Static class that contains parameters of Payments table.
     */
    private static class ParamColumn {
        private static final String ID = "payments.id";
        private static final String ACCOUNT_FROM = "account_from";
        private static final String ACCOUNT_TO = "account_to";
        private static final String AMOUNT = "amount";
        private static final String DATETIME = "datetime";
        private static final String COMMENT = "comment";
    }

    /**
     * Static class that contains parameter indexes for getting payment by ID
     */
    private static class GetPaymentByIDIndex {
        private static final int ID = 1;
    }

    /**
     * Static class that contains parameter indexes for getting payment list like ID
     */
    private static class GetPaymentListByIDIndex {
        private static final int ID = 1;
    }

    /**
     * Static class that contains parameter indexes for creating an new payment
     */
    private static class AddPaymentIndex {
        public static final int CARD_FROM = 1;
        private static final int ACCOUNT_TO = 2;
        public static final int AMOUNT = 3;
        public static final int DATETIME = 4;
        public static final int COMMENT = 5;
    }

    /**
     * Static class that contains parameter indexes for setting account balance by account ID
     */
    private static class SetAccBalanceIndex {
        private static final int BALANCE = 1;
        private static final int ID = 2;
        private static final int STATUS = 3;
    }

    /**
     * Static class that contains parameter indexes for getting account balance by account ID
     */
    private static class GetAccBalanceIndex {
        private static final int ID = 1;
        private static final int STATUS = 2;
    }

}
