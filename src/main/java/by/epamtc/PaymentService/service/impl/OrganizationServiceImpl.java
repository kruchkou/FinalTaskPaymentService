package by.epamtc.PaymentService.service.impl;

import by.epamtc.PaymentService.bean.Organization;
import by.epamtc.PaymentService.dao.exception.DAOException;
import by.epamtc.PaymentService.dao.DAOProvider;
import by.epamtc.PaymentService.dao.OrganizationDAO;
import by.epamtc.PaymentService.service.OrganizationService;

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

    public void editOrganization(int id, String name, int accountID) throws DAOException {
        organizationDAO.editOrganization(id, name, accountID);
    }


    public void setStatus(int id, int status) throws DAOException {
        organizationDAO.setStatusByID(id, status);
    }

}
