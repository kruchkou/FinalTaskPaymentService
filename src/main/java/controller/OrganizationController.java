package controller;

import dao.CardDAO;
import dao.DAOException;
import dao.OrganizationDAO;
import dao.UserDAO;
import dao.entity.Organization;

import java.util.List;

public class OrganizationController {

    private static final OrganizationController instance = new OrganizationController();
    private final OrganizationDAO organizationDAO = new OrganizationDAO();

    private OrganizationController() {
    }

    public List<Organization> getOrgList(int userID) throws DAOException {
        return organizationDAO.getOrgListByUserID(userID);
    }

    public void addOrganization(Organization organization) throws DAOException {
        organizationDAO.addOrganization(organization);
    }

    public void setStatus(int id, int status) throws DAOException {
        organizationDAO.setStatusByID(id, status);
    }

}
