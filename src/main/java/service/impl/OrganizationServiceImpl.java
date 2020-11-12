package service.impl;

import dao.DAOProvider;
import dao.OrganizationDAO;
import dao.exception.DAOException;
import dao.entity.Organization;
import service.OrganizationService;

import java.util.List;

public class OrganizationServiceImpl implements OrganizationService {

    private final DAOProvider daoProvider = DAOProvider.getInstance();
    private final OrganizationDAO organizationDAO = daoProvider.getOrganizationDAO();

    public List<Organization> getOrgList(int userID) throws DAOException {
        return organizationDAO.getOrgListByUserID(userID);
    }

    public List<Organization> getOrgList() throws DAOException {
        return organizationDAO.getOrgList();
    }

    public List<Organization> getOrgList(String name) throws DAOException {
        return organizationDAO.getOrgList(name);
    }

    public List<Organization> getActiveOrgList() throws DAOException {
        return organizationDAO.getActiveOrgList();
    }

    public List<Organization> getActiveOrgList(String name) throws DAOException {
        return organizationDAO.getActiveOrgList(name);
    }

    public Organization getOrg(int id) throws DAOException {
        return organizationDAO.getOrg(id);
    }

    public void addOrganization(String name, int accountID) throws DAOException {
        organizationDAO.addOrganization(name, accountID);
    }

    public void setStatus(int id, int status) throws DAOException {
        organizationDAO.setStatusByID(id, status);
    }

}
