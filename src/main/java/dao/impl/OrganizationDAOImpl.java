package dao.impl;

import dao.exception.DAOException;
import dao.OrganizationDAO;
import dao.entity.*;
import dao.connection.impl.ConnectionPool;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrganizationDAOImpl implements OrganizationDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String ADD_ACCOUNT_SQL = "INSERT INTO Accounts(type,user,status,balance,creation_date) values (?,?,?,?,?)";

    private static final String ADD_ORGANIZATION_SQL = "INSERT INTO Organizations(name,account,status) VALUES (?,?,?)";
    private static final String SET_STATUS_BY_ID_SQL = "UPDATE Organizations SET status = ? where id = ?";
    private static final String GET_ORGANIZATION_LIST_BY_USER_ID_SQL = "SELECT org.id,org.name,account,org.status,statuses.name FROM Organizations org " +
            "JOIN OrganizationStatuses statuses ON org.status = statuses.id " +
            "JOIN Accounts acc ON org.account = acc.id " +
            "WHERE acc.user = ?";

    public OrganizationDAOImpl() {
    }

    @Override
    public List<Organization> getOrgListByUserID(int userID) throws DAOException {
        List<Organization> orgList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ORGANIZATION_LIST_BY_USER_ID_SQL);

            ps.setInt(GetOrgListByUserIDIndex.userID, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = new Organization();
                HashMap<Integer, String> status = new HashMap<>();
                status.put((rs.getInt(ParamColumn.statusID)), rs.getString(ParamColumn.statusName));


                organization.setId(rs.getInt(ParamColumn.id));
                organization.setName(rs.getString(ParamColumn.name));
                organization.setAccount(rs.getInt(ParamColumn.account));
                organization.setStatus(status);

                orgList.add(organization);
            }
        } catch (SQLException e) {
            throw new DAOException("Cant handle OrgDAO.getOrgListByUserID request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return orgList;
    }

    @Override
    public void addOrganization(Organization organization, int userID) throws DAOException {
        final int STATUS_OPENED = 1;

        int accountID;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            accountID = createOrgAccount(connection, userID);

            ps = connection.prepareStatement(ADD_ORGANIZATION_SQL);

            ps.setString(AddOrganizationIndex.name, organization.getName());
            ps.setInt(AddOrganizationIndex.account, accountID);
            ps.setInt(AddOrganizationIndex.status, STATUS_OPENED);
            ps.execute();

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw new DAOException("Can't handle OrgDAO.rollback request", exception);
            }

            throw new DAOException("Can't handle OrgDAO.addOrganization request");
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    //Можно ли создавать здесь счет?? Он ведь в АккаунтДАО должен быть
    //Но мне нужна транзакция
    private int createOrgAccount(Connection connection, int userID) throws SQLException {
        final int STATUS_OPENED = 1;
        final int ACCOUNT_TYPE_ORGANIZATION = 2;
        final String ADD_ACCOUNT_ID_COLUMN = "id";
        final String[] ADD_ACCOUNT_RETURN_COLUMN = {ADD_ACCOUNT_ID_COLUMN};

        int accountID;

        PreparedStatement ps = connection.prepareStatement(ADD_ACCOUNT_SQL, ADD_ACCOUNT_RETURN_COLUMN); //нужно ли его закрывать??

        ps.setInt(CreateAccountIndex.type, ACCOUNT_TYPE_ORGANIZATION);
        ps.setInt(CreateAccountIndex.user, userID);
        ps.setInt(CreateAccountIndex.status, STATUS_OPENED);
        ps.setBigDecimal(CreateAccountIndex.balance, new BigDecimal(0));
        ps.setDate(CreateAccountIndex.creationDate, new java.sql.Date(new java.util.Date().getTime()));
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        accountID = rs.getInt(ADD_ACCOUNT_ID_COLUMN);
        ps.close(); //Нужно ли?

        return accountID;
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
            throw new DAOException("Can't handle OrgDAO.setStatusByID request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    private static class ParamColumn {
        private static final String id = "org.id";
        private static final String name = "org.name";
        private static final String account = "account";
        private static final String statusID = "org.status";
        private static final String statusName = "statuses.name";
    }

    private static class GetOrgListByUserIDIndex {
        public static final int userID = 1;
    }

    private static class AddOrganizationIndex {
        private static final int name = 1;
        private static final int account = 2;
        private static final int status = 3;
    }

    private static class SetStatusByIDIndex {
        private static final int status = 1;
        private static final int id = 2;
    }

    private static class CreateAccountIndex {
        private static final int type = 1;
        private static final int user = 2;
        private static final int status = 3;
        private static final int balance = 4;
        private static final int creationDate = 5;
    }


}
