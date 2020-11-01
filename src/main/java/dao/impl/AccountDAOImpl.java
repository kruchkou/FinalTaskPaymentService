package dao.impl;

import dao.AccountDAO;
import dao.exception.DAOException;
import dao.entity.Account;
import dao.connection.impl.ConnectionPool;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AccountDAOImpl implements AccountDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String ADD_ACCOUNT_SQL = "INSERT INTO Accounts(type,user,status,balance,creation_date) values (?,?,?,?,?)";
    private static final String SET_STATUS_BY_ID_SQL = "UPDATE Accounts SET status = ? WHERE id = ?";
    private static final String SET_BALANCE_BY_ID_SQL = "UPDATE Accounts SET balance = ? WHERE id = ?";
    private static final String GET_BALANCE_FOR_UPDATE_SQL = "SELECT balance FROM Accounts where id = ? FOR UPDATE";
    private static final String GET_ACCOUNT_BY_ID_SQL = "SELECT acc.id,type,types.name,user,status,statuses.name,balance,creation_date FROM Accounts acc " +
            "JOIN AccountTypes types ON acc.type = types.id " +
            "JOIN AccountStatuses statuses ON acc.status = statuses.id " +
            "WHERE (acc.id = ? AND status=?)";
    private static final String GET_ACCOUNT_BY_USER_ID_SQL = "SELECT acc.id,type,types.name,user,status,statuses.name,balance,creation_date FROM Accounts acc " +
            "JOIN AccountTypes types ON acc.type = types.id " +
            "JOIN AccountStatuses statuses ON acc.status = statuses.id " +
            "WHERE (user = ? AND status = ?) ORDER BY creation_date DESC";

    @Override
    public List<Account> getAccountListByUserID(int userID) throws DAOException {
        final int STATUS_OPENED = 1;
        List<Account> accList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACCOUNT_BY_USER_ID_SQL);

            ps.setInt(GetAccountByID.id, userID);
            ps.setInt(GetAccountByID.status, STATUS_OPENED);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                HashMap<Integer, String> type = new HashMap<>();
                HashMap<Integer, String> status = new HashMap<>();
                type.put((rs.getInt(ParamColumn.typeId)), rs.getString(ParamColumn.typeName));
                status.put((rs.getInt(ParamColumn.statusId)), rs.getString(ParamColumn.statusName));

                account.setId(rs.getInt(ParamColumn.id));
                account.setType(type);
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
    public void addAccount(int type, int userID) throws DAOException {
        final int STATUS_OPENED = 1;

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(ADD_ACCOUNT_SQL);

            ps.setInt(CreateIndex.type, type);
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
    public Account getAccountByAccountID(int accountID) throws DAOException {
        final int STATUS_ACTIVE = 1;
        Account account = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACCOUNT_BY_ID_SQL);

            ps.setInt(GetAccountByID.id, accountID);
            ps.setInt(GetAccountByID.status, STATUS_ACTIVE);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                account = new Account();
                HashMap<Integer, String> type = new HashMap<>();
                HashMap<Integer, String> status = new HashMap<>();
                type.put((rs.getInt(ParamColumn.typeId)), rs.getString(ParamColumn.typeName));
                status.put((rs.getInt(ParamColumn.statusId)), rs.getString(ParamColumn.statusName));

                account.setId(rs.getInt(ParamColumn.id));
                account.setType(type);
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
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SET_STATUS_BY_ID_SQL);
            ps.setInt(SetStatusByIDIndex.status, status);
            ps.setInt(SetStatusByIDIndex.id, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException("Can't handle AccountDAO.setStatusByID request.", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    public void sendMoney(int idFrom, int idTo, BigDecimal amount) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        BigDecimal balanceFrom;
        BigDecimal balanceTo;

        connection = connectionPool.getConnection();
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(GET_BALANCE_FOR_UPDATE_SQL);

            ps.setInt(GetBalanceIndex.id, idFrom);
            ResultSet rs = ps.executeQuery();
            balanceFrom = rs.getBigDecimal(ParamColumn.balance);

            ps.setInt(GetBalanceIndex.id, idTo);
            rs = ps.executeQuery();
            balanceTo = rs.getBigDecimal(ParamColumn.balance);

            ps.close(); //нужно ли?

            final boolean ENOUGH_MONEY = balanceFrom.compareTo(balanceTo) > 0;
            if (!ENOUGH_MONEY) {
                throw new DAOException("Not enough balance");
            }

            ps = connection.prepareStatement(SET_BALANCE_BY_ID_SQL);
            ps.setBigDecimal(SetBalanceIndex.balance, balanceFrom.subtract(amount));
            ps.setInt(SetBalanceIndex.id, idFrom);
            ps.execute();

            ps.setBigDecimal(SetBalanceIndex.balance, balanceTo.add(amount));
            ps.setInt(SetBalanceIndex.id, idTo);
            ps.execute();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw new DAOException("Can't handle AccountDAO.rollback request", exception);
            }
            throw new DAOException("Can't handle AccountDAO.sendMoney request", e);
        }

    }

    private static class CreateIndex {
        private static final int type = 1;
        private static final int user = 2;
        private static final int status = 3;
        private static final int balance = 4;
        private static final int creationDate = 5;
    }

    private static class SetBalanceIndex {
        private static final int balance = 1;
        private static final int id = 2;
    }

    private static class GetBalanceIndex {
        private static final int id = 1;
    }

    private static class GetAccountByID {
        private static final int id = 1;
        private static final int status = 2;
    }

    private static class SetStatusByIDIndex {
        private static final int status = 1;
        private static final int id = 2;
    }

    private static class ParamColumn {
        private static final String id = "acc.id";
        private static final String typeId = "type";
        private static final String typeName = "types.name";
        private static final String user = "user";
        private static final String statusId = "status";
        private static final String statusName = "statuses.name";
        private static final String balance = "balance";
        private static final String creationDate = "creation_date";
    }

}


