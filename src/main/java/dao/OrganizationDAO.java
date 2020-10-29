package dao;

import dao.entity.*;
import dao.impl.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrganizationDAO {

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final String INSERT_ORGANIZATION_SQL = "INSERT INTO Organizations(name,account,status) VALUES (?,?,?)";
    private final String SET_STATUS_BY_ID_SQL = "UPDATE Organizations SET status = ? where id = ?";
    private final String GET_ORGANIZATION_LIST_BY_USER_ID_SQL = "SELECT org.id,org.name,account,org.status,statuses.name FROM Organizations org " +
            "JOIN OrganizationStatuses statuses ON org.status = statuses.id " +
            "JOIN Accounts acc ON org.account = acc.id " +
            "WHERE acc.user = ?";

    public OrganizationDAO() {
    }

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

    public void addOrganization(Organization organization) throws DAOException {
        final int STATUS_OPENED = 1;

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(INSERT_ORGANIZATION_SQL);

            ps.setString(AddOrganizationIndex.name, organization.getName());
            ps.setInt(AddOrganizationIndex.account, organization.getAccount());
            ps.setInt(AddOrganizationIndex.status, STATUS_OPENED);

            ps.execute();
        } catch (SQLException e) {
            throw new DAOException("Can't handle OrgDAO.addOrganization request");
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

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

}
