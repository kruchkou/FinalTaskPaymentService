package service;

import dao.DAOProvider;
import dao.OrganizationDAO;
import dao.exception.DAOException;
import dao.entity.Organization;

import java.util.List;

public class OrganizationService {

    private static final OrganizationService instance = new OrganizationService();
    private final DAOProvider daoProvider = DAOProvider.getInstance();
    private final OrganizationDAO organizationDAO = daoProvider.getOrganizationDAO();

    private OrganizationService() {
    }

    public static OrganizationService getInstance() {
        return instance;
    }

    public List<Organization> getOrgList(int userID) throws DAOException {
        return organizationDAO.getOrgListByUserID(userID);
    }

    public void addOrganization(Organization organization, int userID) throws DAOException {
        organizationDAO.addOrganization(organization, userID);
    }

    public void setStatus(int id, int status) throws DAOException {
        organizationDAO.setStatusByID(id, status);
    }

}
