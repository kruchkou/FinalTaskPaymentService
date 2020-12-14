package by.epamtc.paymentservice.dao.impl;

import by.epamtc.paymentservice.bean.Organization;
import by.epamtc.paymentservice.bean.Status;
import by.epamtc.paymentservice.dao.OrganizationDAO;
import by.epamtc.paymentservice.dao.connection.ConnectionPool;
import by.epamtc.paymentservice.dao.connection.impl.ConnectionPoolImpl;
import by.epamtc.paymentservice.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of {@link OrganizationDAO}. Provides methods to interact with Users data from database.
 * Methods connect to database using {@link Connection} from {@link ConnectionPoolImpl} and manipulate with data(save, edit, etc.).
 */
public class OrganizationDAOImpl implements OrganizationDAO {

    /**
     * Instance of the class
     */
    private static final OrganizationDAOImpl instance = new OrganizationDAOImpl();

    /**
     * An object of {@link ConnectionPoolImpl}
     */
    private static final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    /**
     * Query for database to add an organization
     */
    private static final String ADD_ORGANIZATION_SQL = "INSERT INTO Organizations(name,account,status) VALUES (?,?,?)";

    /**
     * Query for database to update organization data by organization ID
     */
    private static final String EDIT_ORGANIZATION_SQL = "UPDATE Organizations SET name = ?, account = ?, status = ? WHERE id = ?";

    /**
     * Query for database to set status of organization data by organization ID
     */
    private static final String SET_STATUS_BY_ID_SQL = "UPDATE Organizations SET status = ? where id = ?";

    /**
     * Query for database to get organization list by accounts linked owned by user ID
     */
    private static final String GET_ORGANIZATION_LIST_BY_USER_ID_SQL = "SELECT org.id,org.name,account,org.status,statuses.name FROM Organizations org " +
            "JOIN OrganizationStatuses statuses ON org.status = statuses.id " +
            "JOIN Accounts acc ON org.account = acc.id " +
            "WHERE acc.user = ?";

    /**
     * Query for database to get organization data by organization ID
     */
    private static final String GET_ORGANIZATION_BY_ID = "SELECT org.id,org.name,account,org.status,statuses.name FROM Organizations org " +
            "JOIN OrganizationStatuses statuses ON org.status = statuses.id " +
            "WHERE org.id = ? " +
            "ORDER BY org.name";

    /**
     * Query for database to get all organizations
     */
    private static final String GET_ORGANIZATION_LIST = "SELECT org.id,org.name,account,org.status,statuses.name FROM Organizations org " +
            "JOIN OrganizationStatuses statuses ON org.status = statuses.id " +
            "WHERE org.status != ? " +
            "ORDER BY org.name";

    /**
     * Query for database to get organizations with name like name
     */
    private static final String GET_ORGANIZATION_LIST_BY_NAME = "SELECT org.id,org.name,account,org.status,statuses.name FROM Organizations org " +
            "JOIN OrganizationStatuses statuses ON org.status = statuses.id " +
            "WHERE (org.name like ? AND org.status != ?)" +
            "ORDER BY org.name";

    /**
     * Query for database to get all active organizations
     */
    private static final String GET_ACTIVE_ORGANIZATION_LIST = "SELECT org.id,org.name,account,org.status,statuses.name FROM Organizations org " +
            "JOIN OrganizationStatuses statuses ON org.status = statuses.id " +
            "WHERE org.status = ? " +
            "ORDER BY org.name";
    /**
     * Query for database to get active organizations like name
     */
    private static final String GET_ACTIVE_ORGANIZATION_LIST_BY_NAME = "SELECT org.id,org.name,account,org.status,statuses.name FROM Organizations org " +
            "JOIN OrganizationStatuses statuses ON org.status = statuses.id " +
            "WHERE (org.name like ? AND org.status = ?) " +
            "ORDER BY org.name";

    /** Message, that is putted in Exception if there are get org lsit by user ID problem */
    private static final String MESSAGE_GET_ORG_LIST_BY_USER_ID_PROBLEM = "Cant handle OrgDAO.getOrgListByUserID request";

    /** Message, that is putted in Exception if there are get org problem */
    private static final String MESSAGE_GET_ORG_PROBLEM = "Cant handle OrgDAO.getOrg request";

    /** Message, that is putted in Exception if there are get org list by name problem */
    private static final String MESSAGE_GET_ORG_LIST_BY_NAME_PROBLEM = "Cant handle OrgDAO.getOrgListByName request";

    /** Message, that is putted in Exception if there are get active org list by name problem */
    private static final String MESSAGE_GET_ACTIVE_ORG_LIST_BY_NAME_PROBLEM = "Cant handle OrgDAO.getActiveOrgListByName request";

    /** Message, that is putted in Exception if there are get org list problem */
    private static final String MESSAGE_GET_ORG_LIST_PROBLEM = "Cant handle OrgDAO.getOrgList request";

    /** Message, that is putted in Exception if there are get active org list problem */
    private static final String MESSAGE_GET_ACTIVE_ORG_LIST_PROBLEM = "Cant handle OrgDAO.getActiveOrgList request";

    /** Message, that is putted in Exception if there are edit organization rollback problem */
    private static final String MESSAGE_EDIT_ORGANIZATION_ROLLBACK_PROBLEM = "Can't handle OrgDAO.EditOrganization.rollback request";

    /** Message, that is putted in Exception if there are edit organization problem */
    private static final String MESSAGE_EDIT_ORGANIZATION_PROBLEM = "Can't handle OrgDAO.editOrganization request";

    /** Message, that is putted in Exception if there are add organization problem */
    private static final String MESSAGE_ADD_ORGANIZATION_PROBLEM = "Can't handle OrgDAO.addOrganization request";

    /** Message, that is putted in Exception if there are set status by ID problem */
    private static final String MESSAGE_SET_STATUS_BY_ID_PROBLEM = "Can't handle OrgDAO.setStatusByID request";


    /**
     * Returns the instance of the class
     *
     * @return Object of {@link OrganizationDAOImpl}
     */
    public static OrganizationDAOImpl getInstance() {
        return instance;
    }

    /**
     * Private constructor without parameters
     */
    private OrganizationDAOImpl() {

    }

    /**
     * Connects to database and return list of organizations that is linked to accounts owned by user ID.
     *
     * @param userID is user ID
     * @return List of {@link Organization} with all matching organizations.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<Organization> getOrgListByUserID(int userID) throws DAOException {
        List<Organization> orgList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ORGANIZATION_LIST_BY_USER_ID_SQL);

            ps.setInt(GetOrgListByUserIDIndex.USER_ID, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = new Organization();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.STATUS_ID));
                status.setName(rs.getString(ParamColumn.STATUS_NAME));


                organization.setId(rs.getInt(ParamColumn.ID));
                organization.setName(rs.getString(ParamColumn.NAME));
                organization.setAccount(rs.getInt(ParamColumn.ACCOUNT));
                organization.setStatus(status);

                orgList.add(organization);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_GET_ORG_LIST_BY_USER_ID_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return orgList;
    }


    /**
     * Connects to database and returns organization by ID.
     *
     * @param id is organization ID value.
     * @return {@link Organization} if organization data found, null if not.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public Organization getOrg(int id) throws DAOException {
        Organization organization = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ORGANIZATION_BY_ID);
            ps.setInt(GetOrgByIDIndex.ID, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                organization = new Organization();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.STATUS_ID));
                status.setName(rs.getString(ParamColumn.STATUS_NAME));

                organization.setId(rs.getInt(ParamColumn.ID));
                organization.setName(rs.getString(ParamColumn.NAME));
                organization.setAccount(rs.getInt(ParamColumn.ACCOUNT));
                organization.setStatus(status);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_GET_ORG_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return organization;
    }


    /**
     * Connects to database and return list of organizations that named like parameter.
     *
     * @param name is text, that contains organization name.
     * @return List of {@link Organization} with all matching organizations.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<Organization> getOrgList(String name) throws DAOException {
        final int STATUS_DELETED = 3;
        List<Organization> orgList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ORGANIZATION_LIST_BY_NAME);
            ps.setString(GetOrgListByNameIndex.NAME, "%" + name + "%");
            ps.setInt(GetOrgListByNameIndex.STATUS, STATUS_DELETED);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = new Organization();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.STATUS_ID));
                status.setName(rs.getString(ParamColumn.STATUS_NAME));


                organization.setId(rs.getInt(ParamColumn.ID));
                organization.setName(rs.getString(ParamColumn.NAME));
                organization.setAccount(rs.getInt(ParamColumn.ACCOUNT));
                organization.setStatus(status);

                orgList.add(organization);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_GET_ORG_LIST_BY_NAME_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return orgList;
    }


    /**
     * Connects to database and returns list of active organizations that named like parameter.
     *
     * @param name is text, that contains organization name.
     * @return List of {@link Organization} with all matching organizations.
     * @throws DAOException when problems with database connection occurs.
     */
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
            ps.setString(GetOrgListByNameIndex.NAME, ANY_SYMBOL_MATCH + name + ANY_SYMBOL_MATCH);
            ps.setInt(GetOrgListByNameIndex.STATUS, STATUS_OPEN);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = new Organization();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.STATUS_ID));
                status.setName(rs.getString(ParamColumn.STATUS_NAME));


                organization.setId(rs.getInt(ParamColumn.ID));
                organization.setName(rs.getString(ParamColumn.NAME));
                organization.setAccount(rs.getInt(ParamColumn.ACCOUNT));
                organization.setStatus(status);

                orgList.add(organization);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_GET_ACTIVE_ORG_LIST_BY_NAME_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return orgList;
    }


    /**
     * Connects to database and returns list of all organizations.
     *
     * @return List of {@link Organization} with all organizations.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<Organization> getOrgList() throws DAOException {
        final int STATUS_DELETED = 3;
        List<Organization> orgList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ORGANIZATION_LIST);
            ps.setInt(GetOrgListIndex.STATUS, STATUS_DELETED);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = new Organization();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.STATUS_ID));
                status.setName(rs.getString(ParamColumn.STATUS_NAME));


                organization.setId(rs.getInt(ParamColumn.ID));
                organization.setName(rs.getString(ParamColumn.NAME));
                organization.setAccount(rs.getInt(ParamColumn.ACCOUNT));
                organization.setStatus(status);

                orgList.add(organization);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_GET_ORG_LIST_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return orgList;
    }


    /**
     * Connects to database and returns list of all active organizations.
     *
     * @return List of {@link Organization} with all active organizations.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<Organization> getActiveOrgList() throws DAOException {
        final int STATUS_ACTIVE = 1;
        List<Organization> orgList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_ACTIVE_ORGANIZATION_LIST);
            ps.setInt(GetOrgListIndex.STATUS, STATUS_ACTIVE);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = new Organization();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.STATUS_ID));
                status.setName(rs.getString(ParamColumn.STATUS_NAME));


                organization.setId(rs.getInt(ParamColumn.ID));
                organization.setName(rs.getString(ParamColumn.NAME));
                organization.setAccount(rs.getInt(ParamColumn.ACCOUNT));
                organization.setStatus(status);

                orgList.add(organization);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_GET_ACTIVE_ORG_LIST_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return orgList;
    }


    /**
     * Connects to database and update organization's data.
     *
     * @param id        is organization ID value
     * @param name      is text that contains new name for organization.
     * @param accountID is an account ID that will be linked with organization.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public void editOrganization(int id, String name, int accountID) throws DAOException {
        final int STATUS_OPENED = 1;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(EDIT_ORGANIZATION_SQL);

            ps.setString(EditOrganizationIndex.NAME, name);
            ps.setInt(EditOrganizationIndex.ACCOUNT, accountID);
            ps.setInt(EditOrganizationIndex.STATUS, STATUS_OPENED);
            ps.setInt(EditOrganizationIndex.ID, id);
            ps.execute();

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw new DAOException(MESSAGE_EDIT_ORGANIZATION_ROLLBACK_PROBLEM, exception);
            }

            throw new DAOException(MESSAGE_EDIT_ORGANIZATION_PROBLEM);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    /**
     * Connects to database, creates new organization by name and links it to an account to receive payments.
     *
     * @param name      is text that contains name for new organization.
     * @param accountID is an account ID that will be linked with organization.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public void addOrganization(String name, int accountID) throws DAOException {
        final int STATUS_OPENED = 1;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(ADD_ORGANIZATION_SQL);

            ps.setString(AddOrganizationIndex.NAME, name);
            ps.setInt(AddOrganizationIndex.ACCOUNT, accountID);
            ps.setInt(AddOrganizationIndex.STATUS, STATUS_OPENED);
            ps.execute();

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_ADD_ORGANIZATION_PROBLEM);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    /**
     * Connects to database and set organization status to organization by ID.
     *
     * @param id     is organization ID value
     * @param status is organization status ID.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public void setStatusByID(int id, int status) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SET_STATUS_BY_ID_SQL);
            ps.setInt(SetStatusByIDIndex.STATUS, status);
            ps.setInt(SetStatusByIDIndex.ID, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SET_STATUS_BY_ID_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    /**
     * Static class that contains parameter indexes for creation a card
     */
    private static class ParamColumn {
        private static final String ID = "org.id";
        private static final String NAME = "org.name";
        private static final String ACCOUNT = "account";
        private static final String STATUS_ID = "org.status";
        private static final String STATUS_NAME = "statuses.name";
    }

    /**
     * Static class that contains parameter indexes for getting an organization list like name
     */
    private static class GetOrgListByNameIndex {
        public static final int NAME = 1;
        public static final int STATUS = 2;
    }

    /**
     * Static class that contains parameter indexes for getting an organization by ID
     */
    private static class GetOrgByIDIndex {
        public static final int ID = 1;
    }

    /**
     * Static class that contains parameter indexes for getting an organization list
     * by linked accounts that owned by UserID
     */
    private static class GetOrgListByUserIDIndex {
        public static final int USER_ID = 1;
    }

    /**
     * Static class that contains parameter indexes for getting an organization list
     */
    private static class GetOrgListIndex {
        public static final int STATUS = 1;
    }

    /**
     * Static class that contains parameter indexes for getting a card by ID
     */
    private static class AddOrganizationIndex {
        private static final int NAME = 1;
        private static final int ACCOUNT = 2;
        private static final int STATUS = 3;
    }

    /**
     * Static class that contains parameter indexes for editing an organization by ID
     */
    private static class EditOrganizationIndex {
        private static final int NAME = 1;
        private static final int ACCOUNT = 2;
        private static final int STATUS = 3;
        private static final int ID = 4;
    }

    /**
     * Static class that contains parameter indexes for setting organization status by ID
     */
    private static class SetStatusByIDIndex {
        private static final int STATUS = 1;
        private static final int ID = 2;
    }

}
