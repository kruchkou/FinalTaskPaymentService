package dao;

import dao.entity.Organization;
import dao.exception.DAOException;

import java.util.List;

public interface OrganizationDAO {

    List<Organization> getOrgListByUserID(int userID) throws DAOException;
    void addOrganization(String name, int accountID) throws DAOException;
    void setStatusByID(int id, int status) throws DAOException;
    List<Organization> getOrgList(String name) throws DAOException;
    List<Organization> getActiveOrgList(String name) throws DAOException;
    Organization getOrg(int id) throws DAOException;
    List<Organization> getOrgList() throws DAOException;
    List<Organization> getActiveOrgList() throws DAOException;

}
