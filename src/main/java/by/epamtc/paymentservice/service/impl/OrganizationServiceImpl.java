package by.epamtc.paymentservice.service.impl;

import by.epamtc.paymentservice.bean.Organization;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.dao.DAOProvider;
import by.epamtc.paymentservice.dao.OrganizationDAO;
import by.epamtc.paymentservice.service.OrganizationService;
import by.epamtc.paymentservice.service.exception.ServiceException;

import java.util.List;

public class OrganizationServiceImpl implements OrganizationService {

    private final DAOProvider daoProvider = DAOProvider.getInstance();
    private final OrganizationDAO organizationDAO = daoProvider.getOrganizationDAO();

    @Override
    public List<Organization> getOrgList() throws ServiceException {
        try {
            return organizationDAO.getOrgList();
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getOrgList request at OrganizationService",e);
        }
    }

    @Override
    public List<Organization> getOrgList(String name) throws ServiceException {
        try {
            return organizationDAO.getOrgList(name);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getOrgList request at OrganizationService",e);
        }
    }

    @Override
    public List<Organization> getActiveOrgList() throws ServiceException {
        try {
            return organizationDAO.getActiveOrgList();
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getActiveOrgList request at OrganizationService",e);
        }
    }

    @Override
    public List<Organization> getActiveOrgList(String name) throws ServiceException {
        try {
            return organizationDAO.getActiveOrgList(name);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getActiveOrgList request at OrganizationService",e);
        }
    }

    @Override
    public Organization getOrg(int id) throws ServiceException {
        try {
            return organizationDAO.getOrg(id);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getOrg request at OrganizationService",e);
        }
    }

    @Override
    public void addOrganization(String name, int accountID) throws ServiceException {
        try {
            organizationDAO.addOrganization(name, accountID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle addOrganization request at OrganizationService",e);
        }
    }

    @Override
    public void editOrganization(int id, String name, int accountID) throws ServiceException {
        try {
            organizationDAO.editOrganization(id, name, accountID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle editOrganization request at OrganizationService",e);
        }
    }

    @Override
    public void setStatus(int id, int status) throws ServiceException {
        try {
            organizationDAO.setStatusByID(id, status);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle setStatus request at OrganizationService",e);
        }
    }

}
