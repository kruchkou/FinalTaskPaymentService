package by.epamtc.paymentservice.dao.impl;

import by.epamtc.paymentservice.dao.DAOProvider;
import by.epamtc.paymentservice.dao.UserDAO;
import by.epamtc.paymentservice.dao.connection.impl.ConnectionPool;
import by.epamtc.paymentservice.bean.Account;
import by.epamtc.paymentservice.bean.AccountInfo;
import by.epamtc.paymentservice.bean.Status;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.dao.AccountDAO;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * Implementation of {@link AccountDAO}. Provides methods to interact with Users data from database.
 * Methods connect to database using {@link Connection} from {@link ConnectionPool} and manipulate with data(save, edit, etc.).
 *
 */
public class AccountDAOImpl implements AccountDAO {

    /** Instance of the class */
    private static final AccountDAOImpl instance = new AccountDAOImpl();

    /** An object of {@link ConnectionPool} */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /** Query for database to set organization status by account ID */
    private static final String SET_ORG_STATUS_BY_ACCOUNT_ID_SQL = "UPDATE Organizations SET status = ? WHERE account = ?";

    /** Query for database to add an account */
    private static final String ADD_ACCOUNT_SQL = "INSERT INTO Accounts(user,status,balance,creation_date) values (?,?,?,?)";

    /** Query for database to set an account status by account ID */
    private static final String SET_STATUS_BY_ID_SQL = "UPDATE Accounts SET status = ? WHERE id = ?";

    /** Query for database to set an account balance by account ID */
    private static final String SET_BALANCE_BY_ID_SQL = "UPDATE Accounts SET balance = ? WHERE (id = ? AND status = ?)";

    /** Query for database to get an account status by account ID */
    private static final String GET_BALANCE_BY_ID_SQL = "SELECT balance FROM Accounts WHERE (id = ? AND status = ?)";

    /** Query for database to get an account data by account ID */
    private static final String GET_ACCOUNT_BY_ID_SQL = "SELECT acc.id,user,acc.status,statuses.name,balance,creation_date FROM Accounts acc " +
            "JOIN AccountStatuses statuses ON acc.status = statuses.id " +
            "WHERE (acc.id = ?)";

    /** Query for database to get an account list by user ID */
    private static final String GET_ACCOUNT_LIST_BY_USER_ID_SQL = "SELECT acc.id,user,acc.status,statuses.name,balance,creation_date FROM Accounts acc " +
            "JOIN AccountStatuses statuses ON acc.status = statuses.id " +
            "WHERE (user = ? AND acc.status != ?) ORDER BY creation_date DESC";

    /** Query for database to get active accounts list by user ID  */
    private static final String GET_ACTIVE_ACCOUNT_LIST_BY_USER_ID_SQL = "SELECT acc.id,user,acc.status,statuses.name,balance,creation_date FROM Accounts acc " +
            "JOIN AccountStatuses statuses ON acc.status = statuses.id " +
            "WHERE (user = ? AND acc.status = ?) ORDER BY creation_date DESC";

    /** Query for database to get public account info by account ID */
    private static final String GET_ACCOUNT_INFO_SQL = "SELECT acc.id,acc.status,statuses.name,users.name,users.surname,users.patronymic FROM Accounts acc " +
            "JOIN Users users ON acc.user = users.id " +
            "JOIN AccountStatuses statuses ON acc.status = statuses.id " +
            "WHERE (acc.id = ?)";

    /** Query for database to get public account info list like account ID */
    private static final String GET_ACCOUNT_INFO_LIST_SQL = "SELECT acc.id,acc.status,statuses.name,users.name,users.surname,users.patronymic FROM Accounts acc " +
            "JOIN Users users ON acc.user = users.id " +
            "JOIN AccountStatuses statuses ON acc.status = statuses.id " +
            "WHERE (acc.id like ?)";

    /** Query for database to get active public account info list like account ID */
    private static final String GET_ACTIVE_ACCOUNT_INFO_LIST_SQL = "SELECT acc.id,acc.status,statuses.name,users.name,users.surname,users.patronymic FROM Accounts acc " +
            "JOIN Users users ON acc.user = users.id " +
            "JOIN AccountStatuses statuses ON acc.status = statuses.id " +
            "WHERE (acc.id like ? and acc.status = ?)";

    /**
     * Returns the instance of the class
     * @return Object of {@link AccountDAOImpl}
     */
    public static AccountDAOImpl getInstance() {
        return instance;
    }

    /** Private constructor without parameters */
    private AccountDAOImpl() {

    }

    /**
     * Connects to database and return list of accounts owned by user.
     *
     * @param userID   is user ID
     * @return List of {@link Account} with all matching accounts.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<Account> getAccountListByUserID(int userID) throws DAOException {
        final int STATUS_DELETED = 3;
        List<Account> accList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACCOUNT_LIST_BY_USER_ID_SQL);

            ps.setInt(GetAccountByID.ID, userID);
            ps.setInt(GetAccountByID.STATUS, STATUS_DELETED);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = extractAccount(rs);
                accList.add(account);
            }

        } catch (SQLException e) {
            throw new DAOException("Cant handle AccountDAO.getUserByLogin request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }

        return accList;
    }

    /**
     * Connects to database and return list of active accounts owned by user.
     *
     * @param userID   is user ID
     * @return List of {@link Account} with all matching accounts.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<Account> getActiveAccountListByUserID(int userID) throws DAOException {
        final int STATUS_OPENED = 1;
        List<Account> accList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACTIVE_ACCOUNT_LIST_BY_USER_ID_SQL);

            ps.setInt(GetAccountByID.ID, userID);
            ps.setInt(GetAccountByID.STATUS, STATUS_OPENED);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = extractAccount(rs);
                accList.add(account);
            }

        } catch (SQLException e) {
            throw new DAOException("Cant handle AccountDAO.getUserByLogin request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }

        return accList;
    }

    /**
     * Connects to database and top up account by amount.
     *
     * @param accountID     is account ID value
     * @param amount     is {@link BigDecimal} object that contains amount.
     * @throws DAOException when problems with database connection occurs.
     */
    public void topUpAccount(int accountID, BigDecimal amount) throws DAOException {
        final int STATUS_OPEN = 1;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            ps = connection.prepareStatement(GET_BALANCE_BY_ID_SQL);

            ps.setInt(GetBalanceIndex.ID, accountID);
            ps.setInt(GetBalanceIndex.STATUS, STATUS_OPEN);
            ResultSet rs = ps.executeQuery();

            if(!rs.next()) {
                throw new DAOException("Can't handle AccountDAO.topUpAccount.getBalance request");
            }

            BigDecimal balance = rs.getBigDecimal(ParamColumn.BALANCE);
            ps = connection.prepareStatement(SET_BALANCE_BY_ID_SQL);

            ps.setInt(SetBalanceIndex.ID, accountID);
            ps.setBigDecimal(SetBalanceIndex.BALANCE, balance.add(amount));
            ps.setInt(SetBalanceIndex.STATUS, STATUS_OPEN);

            ps.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throw new DAOException("Can't rollback at AccountDAO.topUpAccount",throwables);
            }
            throw new DAOException("Can't handle AccountDAO.topUpAccount.setBalance request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }

    }

    /**
     * Connects to database and add an new account.
     *
     * @param userID     is id of the user that creating new account.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public void addAccount(int userID) throws DAOException {
        final int STATUS_OPENED = 1;

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(ADD_ACCOUNT_SQL);

            ps.setInt(CreateIndex.USER, userID);
            ps.setInt(CreateIndex.STATUS, STATUS_OPENED);
            ps.setBigDecimal(CreateIndex.BALANCE, new BigDecimal(0));
            ps.setDate(CreateIndex.CREATION_DATE, new java.sql.Date(new Date().getTime()));
            ps.execute();

        } catch (SQLException e) {
            throw new DAOException("Can't handle AccountDAO.addAccount request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    /**
     * Connects to database and returns public info about account.
     *
     * @param accountID    is account ID value.
     * @return {@link AccountInfo} if account's data found, null if not.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public AccountInfo getAccountInfo(int accountID) throws DAOException {
        AccountInfo accountInfo = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACCOUNT_INFO_SQL);

            ps.setInt(GetAccountByID.ID, accountID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                accountInfo = extractAccountInfo(rs);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DAOException("Cant handle AccountDAO.getAccountInfo request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return accountInfo;
    }


    /**
     * Connects to database and returns list of public infos about accounts that have id like parameter.
     *
     * @param accountID    is account ID value.
     * @return List of {@link AccountInfo} with all matching public account infos.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<AccountInfo> getAccountInfoList(int accountID) throws DAOException {
        final String ALL_SYMBOLS_AFTER_POSTFIX = "%";
        List<AccountInfo> accountInfoList;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACCOUNT_INFO_LIST_SQL);

            ps.setString(GetAccountByID.ID, accountID+ALL_SYMBOLS_AFTER_POSTFIX);
            ResultSet rs = ps.executeQuery();

            accountInfoList = extractAccountInfoList(rs);

            return accountInfoList;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DAOException("Cant handle AccountDAO.getAccountInfoList request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    /**
     * Connects to database and returns list of public infos about active accounts that have id like parameter.
     *
     * @param accountID    is account ID value.
     * @return List of {@link AccountInfo} with all matching public account infos.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<AccountInfo> getActiveAccountInfoList(int accountID) throws DAOException {
        final int STATUS_ACTIVE = 1;
        final String ALL_SYMBOLS_AFTER_POSTFIX = "%";
        List<AccountInfo> accountInfoList;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACTIVE_ACCOUNT_INFO_LIST_SQL);

            ps.setString(GetAccountByID.ID, accountID+ALL_SYMBOLS_AFTER_POSTFIX);
            ps.setInt(GetAccountByID.STATUS, STATUS_ACTIVE);
            ResultSet rs = ps.executeQuery();

            accountInfoList = extractAccountInfoList(rs);
            return accountInfoList;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DAOException("Cant handle AccountDAO.getAccountInfoList request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    /**
     * Extracts List of account public infos from the ResultSet
     *
     * @param rs    is an {@link ResultSet} object, that contains public account infos.
     * @return List of {@link AccountInfo} with all public account infos.
     * @throws SQLException when problems with database connection occurs.
     */
    private List<AccountInfo> extractAccountInfoList(ResultSet rs) throws SQLException {
        List<AccountInfo> accountInfoList = new ArrayList<>();
        while (rs.next()) {
            AccountInfo accountInfo = extractAccountInfo(rs);

            accountInfoList.add(accountInfo);
        }
        return accountInfoList;
    }

    /**
     * Extracts account public infos from the ResultSet
     *
     * @param rs    is an {@link ResultSet} object, that contains public account infos.
     * @return Object of {@link AccountInfo} with public account info.
     * @throws SQLException when problems with database connection occurs.
     */
    private AccountInfo extractAccountInfo(ResultSet rs) throws SQLException {
        AccountInfo accountInfo = new AccountInfo();

        Status status = new Status();
        status.setId(rs.getInt(ParamColumn.STATUS_ID));
        status.setName(rs.getString(ParamColumn.STATUS_NAME));

        accountInfo.setId(rs.getInt(ParamColumn.ID));
        accountInfo.setUserName(rs.getString(ParamColumn.USER_NAME));
        accountInfo.setUserSurname(rs.getString(ParamColumn.USER_SURNAME));
        accountInfo.setUserPatronymic(rs.getString(ParamColumn.USER_PATRONYMIC));
        accountInfo.setStatus(status);

        return accountInfo;
    }

    /**
     * Connects to database and returns all info about account by ID.
     *
     * @param accountID    is account ID value.
     * @return {@link Account} if account's data found, null if not.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public Account getAccount(int accountID) throws DAOException {
        Account account = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACCOUNT_BY_ID_SQL);

            ps.setInt(GetAccountByID.ID, accountID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                account = extractAccount(rs);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DAOException("Cant handle AccountDAO.getUserByLogin request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return account;
    }


    /**
     * Extracts account info from the ResultSet
     *
     * @param rs    is an {@link ResultSet} object, that contains account info.
     * @return Object of {@link Account} with account info.
     * @throws SQLException when problems with database connection occurs.
     */
    private Account extractAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        Status status = new Status();

        status.setId(rs.getInt(ParamColumn.STATUS_ID));
        status.setName(rs.getString(ParamColumn.STATUS_NAME));

        account.setId(rs.getInt(ParamColumn.ID));
        account.setUser(rs.getInt(ParamColumn.USER));
        account.setStatus(status);
        account.setBalance(rs.getBigDecimal(ParamColumn.BALANCE));
        account.setCreationDate(rs.getDate(ParamColumn.CREATION_DATE));

        return account;
    }


    /**
     * Connects to database and set account status to the account by ID.
     * if there are organization linked, sets its status too.
     *
     * @param id     is account ID value
     * @param status     is account status ID.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public void setStatusByID(int id, int status) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            ps = connection.prepareStatement(SET_STATUS_BY_ID_SQL);
            ps.setInt(SetStatusByIDIndex.STATUS, status);
            ps.setInt(SetStatusByIDIndex.ID, id);
            ps.execute();

            ps.close();

            ps = connection.prepareStatement(SET_ORG_STATUS_BY_ACCOUNT_ID_SQL);
            ps.setInt(SetStatusByIDIndex.STATUS, status);
            ps.setInt(SetStatusByIDIndex.ID, id);
            ps.execute();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throw new DAOException("Can't rollback at AccountDAO.setStatusByID request.", throwables);
            }
            throw new DAOException("Can't handle AccountDAO.setStatusByID request.", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    /**
     * Static class that contains parameter indexes for creation an account
     */
    private static class CreateIndex {
        private static final int USER = 1;
        private static final int STATUS = 2;
        private static final int BALANCE = 3;
        private static final int CREATION_DATE = 4;
    }

    /**
     * Static class that contains parameter indexes for setting balance
     */
    private static class SetBalanceIndex {
        private static final int BALANCE = 1;
        private static final int ID = 2;
        private static final int STATUS = 3;
    }

    /**
     * Static class that contains parameter indexes for getting balance
     */
    private static class GetBalanceIndex {
        private static final int ID = 1;
        private static final int STATUS = 2;
    }

    /**
     * Static class that contains parameter indexes for getting account by ID
     */
    private static class GetAccountByID {
        private static final int ID = 1;
        private static final int STATUS = 2;
    }

    /**
     * Static class that contains parameter indexes for setting status by ID
     */
    private static class SetStatusByIDIndex {
        private static final int STATUS = 1;
        private static final int ID = 2;
    }

    /**
     * Static class that contains parameters of Accounts table.
     */
    private static class ParamColumn {
        private static final String USER_NAME = "users.name";
        private static final String USER_SURNAME = "users.surname";
        private static final String USER_PATRONYMIC = "users.patronymic";
        private static final String ID = "acc.id";
        private static final String USER = "user";
        private static final String STATUS_ID = "status";
        private static final String STATUS_NAME = "statuses.name";
        private static final String BALANCE = "balance";
        private static final String CREATION_DATE = "creation_date";
    }

}


