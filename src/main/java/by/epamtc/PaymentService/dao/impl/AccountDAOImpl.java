package by.epamtc.PaymentService.dao.impl;

import by.epamtc.PaymentService.dao.connection.impl.ConnectionPool;
import by.epamtc.PaymentService.bean.Account;
import by.epamtc.PaymentService.bean.AccountInfo;
import by.epamtc.PaymentService.bean.Status;
import by.epamtc.PaymentService.dao.exception.DAOException;
import by.epamtc.PaymentService.dao.AccountDAO;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AccountDAOImpl implements AccountDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SET_ORG_STATUS_BY_ACCOUNT_ID_SQL = "UPDATE Organizations SET status = ? WHERE account = ?";

    private static final String ADD_ACCOUNT_SQL = "INSERT INTO Accounts(user,status,balance,creation_date) values (?,?,?,?)";
    private static final String SET_STATUS_BY_ID_SQL = "UPDATE Accounts SET status = ? WHERE id = ?";

    private static final String SET_BALANCE_BY_ID_SQL = "UPDATE Accounts SET balance = ? WHERE (id = ? AND status = ?)";
    private static final String GET_BALANCE_BY_ID_SQL = "SELECT balance FROM Accounts WHERE (id = ? AND status = ?)";
    private static final String GET_ACCOUNT_BY_ID_SQL = "SELECT acc.id,user,acc.status,statuses.name,balance,creation_date FROM Accounts acc " +
            "JOIN AccountStatuses statuses ON acc.status = statuses.id " +
            "WHERE (acc.id = ?)";
    private static final String GET_ACCOUNT_LIST_BY_USER_ID_SQL = "SELECT acc.id,user,acc.status,statuses.name,balance,creation_date FROM Accounts acc " +
            "JOIN AccountStatuses statuses ON acc.status = statuses.id " +
            "WHERE (user = ? AND acc.status != ?) ORDER BY creation_date DESC LIMIT ? OFFSET ?";
    private static final String GET_ACTIVE_ACCOUNT_LIST_BY_USER_ID_SQL = "SELECT acc.id,user,acc.status,statuses.name,balance,creation_date FROM Accounts acc " +
            "JOIN AccountStatuses statuses ON acc.status = statuses.id " +
            "WHERE (user = ? AND acc.status = ?) ORDER BY creation_date DESC LIMIT ? OFFSET ?";
    private static final String GET_ACCOUNT_INFO_SQL = "SELECT acc.id,acc.status,statuses.name,users.name,users.surname,users.patronymic FROM Accounts acc " +
            "JOIN Users users ON acc.user = users.id " +
            "JOIN AccountStatuses statuses ON acc.status = statuses.id " +
            "WHERE (acc.id = ?)";
    private static final String GET_ACCOUNT_INFO_LIST_SQL = "SELECT acc.id,acc.status,statuses.name,users.name,users.surname,users.patronymic FROM Accounts acc " +
            "JOIN Users users ON acc.user = users.id " +
            "JOIN AccountStatuses statuses ON acc.status = statuses.id " +
            "WHERE (acc.id like ?)";

    @Override
    public List<Account> getAccountListByUserID(int userID, int limit, int offset) throws DAOException {
        final int STATUS_DELETED = 3;
        List<Account> accList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACCOUNT_LIST_BY_USER_ID_SQL);

            ps.setInt(GetAccountByID.id, userID);
            ps.setInt(GetAccountByID.status, STATUS_DELETED);
            ps.setInt(GetAccountByID.limit, limit);
            ps.setInt(GetAccountByID.offset, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                HashMap<Integer, String> status = new HashMap<>();
                status.put((rs.getInt(ParamColumn.statusId)), rs.getString(ParamColumn.statusName));

                account.setId(rs.getInt(ParamColumn.id));
                account.setUser(rs.getInt(ParamColumn.user));
                account.setStatus(status);
                account.setBalance(rs.getBigDecimal(ParamColumn.balance));
                account.setCreationDate(rs.getDate(ParamColumn.creationDate));

                accList.add(account);
            }

        } catch (SQLException e) {
            throw new DAOException("Cant handle AccountDAO.getUserByLogin request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }

        return accList;
    }

    @Override
    public List<Account> getActiveAccountListByUserID(int userID, int limit, int offset) throws DAOException {
        final int STATUS_OPENED = 1;
        List<Account> accList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACTIVE_ACCOUNT_LIST_BY_USER_ID_SQL);

            ps.setInt(GetAccountByID.id, userID);
            ps.setInt(GetAccountByID.status, STATUS_OPENED);
            ps.setInt(GetAccountByID.limit, limit);
            ps.setInt(GetAccountByID.offset, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                HashMap<Integer, String> status = new HashMap<>();
                status.put((rs.getInt(ParamColumn.statusId)), rs.getString(ParamColumn.statusName));

                account.setId(rs.getInt(ParamColumn.id));
                account.setUser(rs.getInt(ParamColumn.user));
                account.setStatus(status);
                account.setBalance(rs.getBigDecimal(ParamColumn.balance));
                account.setCreationDate(rs.getDate(ParamColumn.creationDate));

                accList.add(account);
            }

        } catch (SQLException e) {
            throw new DAOException("Cant handle AccountDAO.getUserByLogin request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }

        return accList;
    }

    public void topUpAccount(int accountID, BigDecimal amount) throws DAOException {
        final int STATUS_OPEN = 1;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            ps = connection.prepareStatement(GET_BALANCE_BY_ID_SQL);

            ps.setInt(GetBalanceIndex.id, accountID);
            ps.setInt(GetBalanceIndex.status, STATUS_OPEN);
            ResultSet rs = ps.executeQuery();

            if(!rs.next()) {
                throw new DAOException("Can't handle AccountDAO.topUpAccount.getBalance request");
            }

            BigDecimal balance = rs.getBigDecimal(ParamColumn.balance);
            ps = connection.prepareStatement(SET_BALANCE_BY_ID_SQL);

            ps.setInt(SetBalanceIndex.id, accountID);
            ps.setBigDecimal(SetBalanceIndex.balance, balance.add(amount));
            ps.setInt(SetBalanceIndex.status, STATUS_OPEN);

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

    @Override
    public int getUserAccountsQuantity(int userID) throws DAOException {
        final int STATUS_DELETED = 3;
        final int MAX_LIMIT = 100;
        final int ZERO_OFFSET = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        int userAccountsQuantity = 0;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACCOUNT_LIST_BY_USER_ID_SQL);

            ps.setInt(GetAccountByID.id, userID);
            ps.setInt(GetAccountByID.status, STATUS_DELETED);
            ps.setInt(GetAccountByID.limit, MAX_LIMIT);
            ps.setInt(GetAccountByID.offset, ZERO_OFFSET);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rs.last();
                userAccountsQuantity = rs.getRow();
            }

        } catch (SQLException e) {
            throw new DAOException("Cant handle AccountDAO.getUserAccountsQuantity request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }

        return userAccountsQuantity;
    }

    @Override
    public void addAccount(int userID) throws DAOException {
        final int STATUS_OPENED = 1;

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(ADD_ACCOUNT_SQL);

            ps.setInt(CreateIndex.user, userID);
            ps.setInt(CreateIndex.status, STATUS_OPENED);
            ps.setBigDecimal(CreateIndex.balance, new BigDecimal(0));
            ps.setDate(CreateIndex.creationDate, new java.sql.Date(new Date().getTime()));
            ps.execute();

        } catch (SQLException e) {
            throw new DAOException("Can't handle AccountDAO.addAccount request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public AccountInfo getAccountInfo(int accountID) throws DAOException {
        AccountInfo accountInfo = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACCOUNT_INFO_SQL);

            ps.setInt(GetAccountByID.id, accountID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                accountInfo = new AccountInfo();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.statusId));
                status.setName(rs.getString(ParamColumn.statusName));

                accountInfo.setId(rs.getInt(ParamColumn.id));
                accountInfo.setUserName(rs.getString(ParamColumn.userName));
                accountInfo.setUserSurname(rs.getString(ParamColumn.userSurname));
                accountInfo.setUserPatronymic(rs.getString(ParamColumn.userPatronymic));
                accountInfo.setStatus(status);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DAOException("Cant handle AccountDAO.getAccountInfo request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return accountInfo;
    }

    @Override
    public List<AccountInfo> getAccountInfoList(int accountID) throws DAOException {
        final String ALL_SYMBOLS_AFTER_POSTFIX = "%";
        List<AccountInfo> accountInfoList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACCOUNT_INFO_LIST_SQL);

            ps.setString(GetAccountByID.id, accountID+ALL_SYMBOLS_AFTER_POSTFIX);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AccountInfo accountInfo = new AccountInfo();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.statusId));
                status.setName(rs.getString(ParamColumn.statusName));

                accountInfo.setId(rs.getInt(ParamColumn.id));
                accountInfo.setUserName(rs.getString(ParamColumn.userName));
                accountInfo.setUserSurname(rs.getString(ParamColumn.userSurname));
                accountInfo.setUserPatronymic(rs.getString(ParamColumn.userPatronymic));
                accountInfo.setStatus(status);

                accountInfoList.add(accountInfo);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DAOException("Cant handle AccountDAO.getAccountInfoList request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return accountInfoList;
    }

    @Override
    public Account getAccountByAccountID(int accountID) throws DAOException {
        Account account = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACCOUNT_BY_ID_SQL);

            ps.setInt(GetAccountByID.id, accountID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                account = new Account();
                HashMap<Integer, String> status = new HashMap<>();
                status.put((rs.getInt(ParamColumn.statusId)), rs.getString(ParamColumn.statusName));

                account.setId(rs.getInt(ParamColumn.id));
                account.setUser(rs.getInt(ParamColumn.user));
                account.setStatus(status);
                account.setBalance(rs.getBigDecimal(ParamColumn.balance));
                account.setCreationDate(rs.getDate(ParamColumn.creationDate));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DAOException("Cant handle AccountDAO.getUserByLogin request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return account;
    }

    @Override
    public void setStatusByID(int id, int status) throws DAOException {
        //if there are organization linked, should be set deleted or blocked too
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            ps = connection.prepareStatement(SET_STATUS_BY_ID_SQL);
            ps.setInt(SetStatusByIDIndex.status, status);
            ps.setInt(SetStatusByIDIndex.id, id);
            ps.execute();

            ps.close();

            ps = connection.prepareStatement(SET_ORG_STATUS_BY_ACCOUNT_ID_SQL);
            ps.setInt(SetStatusByIDIndex.status, status);
            ps.setInt(SetStatusByIDIndex.id, id);
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



    private static class CreateIndex {
        private static final int user = 1;
        private static final int status = 2;
        private static final int balance = 3;
        private static final int creationDate = 4;
    }

    private static class SetBalanceIndex {
        private static final int balance = 1;
        private static final int id = 2;
        private static final int status = 3;
    }

    private static class GetBalanceIndex {
        private static final int id = 1;
        private static final int status = 2;
    }

    private static class GetAccountByID {
        private static final int id = 1;
        private static final int status = 2;
        private static final int limit = 3;
        private static final int offset = 4;
    }

    private static class SetStatusByIDIndex {
        private static final int status = 1;
        private static final int id = 2;
    }

    private static class ParamColumn {
        private static final String userName = "users.name";
        private static final String userSurname = "users.surname";
        private static final String userPatronymic = "users.patronymic";
        private static final String id = "acc.id";
        private static final String user = "user";
        private static final String statusId = "status";
        private static final String statusName = "statuses.name";
        private static final String balance = "balance";
        private static final String creationDate = "creation_date";
    }

}


