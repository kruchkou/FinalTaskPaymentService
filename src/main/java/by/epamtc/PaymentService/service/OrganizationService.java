package by.epamtc.PaymentService.service;

import by.epamtc.PaymentService.bean.Organization;
import by.epamtc.PaymentService.dao.exception.DAOException;

import java.util.List;

public interface OrganizationService {

//    List<Organization> getOrgList(int userID) throws DAOException;
    List<Organization> getOrgList() throws DAOException;
    List<Organization> getOrgList(String name) throws DAOException;
    List<Organization> getActiveOrgList() throws DAOException;
    List<Organization> getActiveOrgList(String name) throws DAOException;
    Organization getOrg(int id) throws DAOException;
    void addOrganization(String name, int accountID) throws DAOException;
    void editOrganization(int id, String name, int accountID) throws DAOException;
    void setStatus(int id, int status) throws DAOException;


}
