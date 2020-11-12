package dao.impl;

import dao.exception.DAOException;
import dao.OrganizationDAO;
import dao.entity.*;
import dao.connection.impl.ConnectionPool;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDAOImpl implements OrganizationDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String ADD_ACCOUNT_SQL = "INSERT INTO Accounts(user,status,balance,creation_date) values (?,?,?,?,?)";

    private static final String ADD_ORGANIZATION_SQL = "INSERT INTO Organizations(name,account,status) VALUES (?,?,?)";
    private static final String SET_STATUS_BY_ID_SQL = "UPDATE Organizations SET status = ? where id = ?";
    private static final String GET_ORGANIZATION_LIST_BY_USER_ID_SQL = "SELECT org.id,org.name,account,org.status,statuses.name FROM Organizations org " +
            "JOIN OrganizationStatuses statuses ON org.status = statuses.id " +
            "JOIN Accounts acc ON org.account = acc.id " +
            "WHERE acc.user = ?";

    private static final String GET_ORGANIZATION_BY_ID = "SELECT org.id,org.name,account,org.status,statuses.name FROM Organizations org " +
            "JOIN OrganizationStatuses statuses ON org.status = statuses.id " +
            "WHERE org.id = ? " +
            "ORDER BY org.name";

    private static final String GET_ORGANIZATION_LIST = "SELECT org.id,org.name,account,org.status,statuses.name FROM Organizations org " +
            "JOIN OrganizationStatuses statuses ON org.status = statuses.id " +
            "WHERE org.status != ? " +
            "ORDER BY org.name";

    private static final String GET_ORGANIZATION_LIST_BY_NAME = "SELECT org.id,org.name,account,org.status,statuses.name FROM Organizations org " +
            "JOIN OrganizationStatuses statuses ON org.status = statuses.id " +
            "WHERE (org.name like ? AND org.status != ?)" +
            "ORDER BY org.name";

    private static final String GET_ACTIVE_ORGANIZATION_LIST = "SELECT org.id,org.name,account,org.status,statuses.name FROM Organizations org " +
            "JOIN OrganizationStatuses statuses ON org.status = statuses.id " +
            "WHERE org.status = ? " +
            "ORDER BY org.name";

    private static final String GET_ACTIVE_ORGANIZATION_LIST_BY_NAME = "SELECT org.id,org.name,account,org.status,statuses.name FROM Organizations org " +
            "JOIN OrganizationStatuses statuses ON org.status = statuses.id " +
            "WHERE (org.name like ? AND org.status = ?) " +
            "ORDER BY org.name";

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
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.statusID));
                status.setName(rs.getString(ParamColumn.statusName));


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
    public Organization getOrg(int id) throws DAOException {
        Organization organization = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ORGANIZATION_BY_ID);
            ps.setInt(GetOrgByIDIndex.id,id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                organization = new Organization();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.statusID));
                status.setName(rs.getString(ParamColumn.statusName));

                organization.setId(rs.getInt(ParamColumn.id));
                organization.setName(rs.getString(ParamColumn.name));
                organization.setAccount(rs.getInt(ParamColumn.account));
                organization.setStatus(status);
            }
        } catch (SQLException e) {
            throw new DAOException("Cant handle OrgDAO.getOrg request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return organization;
    }

    @Override
    public List<Organization> getOrgList(String name) throws DAOException {
        final int STATUS_DELETED = 3;
        List<Organization> orgList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ORGANIZATION_LIST_BY_NAME);
            ps.setString(GetOrgListByNameIndex.name,"%"+name+"%");
            ps.setInt(GetOrgListByNameIndex.status, STATUS_DELETED);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = new Organization();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.statusID));
                status.setName(rs.getString(ParamColumn.statusName));


                organization.setId(rs.getInt(ParamColumn.id));
                organization.setName(rs.getString(ParamColumn.name));
                organization.setAccount(rs.getInt(ParamColumn.account));
                organization.setStatus(status);

                orgList.add(organization);
            }
        } catch (SQLException e) {
            throw new DAOException("Cant handle OrgDAO.getOrgListByName request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return orgList;
    }

    @Override
    public List<Organization> getActiveOrgList(String name) throws DAOException {
        final int STATUS_OPEN = 1;
        final String ANY_SYMBOL_MATCH = "%";
        List<Organization> orgList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACTIVE_ORGANIZATION_LIST_BY_NAME);
            ps.setString(GetOrgListByNameIndex.name,ANY_SYMBOL_MATCH + name + ANY_SYMBOL_MATCH);
            ps.setInt(GetOrgListByNameIndex.status, STATUS_OPEN);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = new Organization();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.statusID));
                status.setName(rs.getString(ParamColumn.statusName));


                organization.setId(rs.getInt(ParamColumn.id));
                organization.setName(rs.getString(ParamColumn.name));
                organization.setAccount(rs.getInt(ParamColumn.account));
                organization.setStatus(status);

                orgList.add(organization);
            }
        } catch (SQLException e) {
            throw new DAOException("Cant handle OrgDAO.getOrgListByName request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return orgList;
    }


    @Override
    public List<Organization> getOrgList() throws DAOException {
        final int STATUS_DELETED = 3;
        List<Organization> orgList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ORGANIZATION_LIST);
            ps.setInt(GetOrgListIndex.status, STATUS_DELETED);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = new Organization();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.statusID));
                status.setName(rs.getString(ParamColumn.statusName));


                organization.setId(rs.getInt(ParamColumn.id));
                organization.setName(rs.getString(ParamColumn.name));
                organization.setAccount(rs.getInt(ParamColumn.account));
                organization.setStatus(status);

                orgList.add(organization);
            }
        } catch (SQLException e) {
            throw new DAOException("Cant handle OrgDAO.getOrgList request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return orgList;
    }

    @Override
    public List<Organization> getActiveOrgList() throws DAOException {
        final int STATUS_ACTIVE = 1;
        List<Organization> orgList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACTIVE_ORGANIZATION_LIST);
            ps.setInt(GetOrgListIndex.status, STATUS_ACTIVE);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = new Organization();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.statusID));
                status.setName(rs.getString(ParamColumn.statusName));


                organization.setId(rs.getInt(ParamColumn.id));
                organization.setName(rs.getString(ParamColumn.name));
                organization.setAccount(rs.getInt(ParamColumn.account));
                organization.setStatus(status);

                orgList.add(organization);
            }
        } catch (SQLException e) {
            throw new DAOException("Cant handle OrgDAO.getOrgList request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return orgList;
    }

    @Override
    public void addOrganization(String name, int accountID) throws DAOException {
        final int STATUS_OPENED = 1;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(ADD_ORGANIZATION_SQL);

            ps.setString(AddOrganizationIndex.name, name);
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

    private static class GetOrgListByNameIndex {
        public static final int name = 1;
        public static final int status = 2;
    }

    private static class GetOrgByIDIndex {
        public static final int id = 1;
    }

    private static class GetOrgListByUserIDIndex {
        public static final int userID = 1;
    }

    private static class GetOrgListIndex {
        public static final int status = 1;
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
        private static final int user = 1;
        private static final int status = 2;
        private static final int balance = 3;
        private static final int creationDate = 4;
    }


}
