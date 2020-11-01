package dao;

import dao.entity.Organization;
import dao.exception.DAOException;

import java.util.List;

public interface OrganizationDAO {

    List<Organization> getOrgListByUserID(int userID) throws DAOException;
    void addOrganization(Organization organization, int userID) throws DAOException;
    void setStatusByID(int id, int status) throws DAOException;

}
