package by.epamtc.paymentservice.dao;

import by.epamtc.paymentservice.bean.*;
import by.epamtc.paymentservice.dao.exception.DAOException;

import java.util.List;

/**
 * Interface provides methods to interact with Organizations data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 *
 */
public interface OrganizationDAO {

    /**
     * Connects to database, creates new organization by name and links it to an account to receive payments.
     *
     * @param name     is text that contains name for new organization.
     * @param accountID     is an account ID that will be linked with organization.
     * @throws DAOException when problems with database connection occurs.
     */
    void addOrganization(String name, int accountID) throws DAOException;

    /**
     * Connects to database and set organization status to organization by ID.
     *
     * @param id     is organization ID value
     * @param status     is organization status ID.
     * @throws DAOException when problems with database connection occurs.
     */
    void setStatusByID(int id, int status) throws DAOException;

    /**
     * Connects to database and update organization's data.
     *
     * @param id     is organization ID value
     * @param name     is text that contains new name for organization.
     * @param accountID     is an account ID that will be linked with organization.
     * @throws DAOException when problems with database connection occurs.
     */
    void editOrganization(int id, String name, int accountID) throws DAOException;

    /**
     * Connects to database and returns organization by ID.
     *
     * @param id    is organization ID value.
     * @return {@link Organization} if organization data found, null if not.
     * @throws DAOException when problems with database connection occurs.
     */
    Organization getOrg(int id) throws DAOException;

    /**
     * Connects to database and returns list of all organizations.
     *
     * @return List of {@link Organization} with all organizations.
     * @throws DAOException when problems with database connection occurs.
     */
    List<Organization> getOrgList() throws DAOException;

    /**
     * Connects to database and return list of organizations that named like parameter.
     *
     * @param name   is text, that contains organization name.
     * @return List of {@link Organization} with all matching organizations.
     * @throws DAOException when problems with database connection occurs.
     */
    List<Organization> getOrgList(String name) throws DAOException;

    /**
     * Connects to database and returns list of all active organizations.
     *
     * @return List of {@link Organization} with all active organizations.
     * @throws DAOException when problems with database connection occurs.
     */
    List<Organization> getActiveOrgList() throws DAOException;

    /**
     * Connects to database and returns list of active organizations that named like parameter.
     *
     * @param name   is text, that contains organization name.
     * @return List of {@link Organization} with all matching organizations.
     * @throws DAOException when problems with database connection occurs.
     */
    List<Organization> getActiveOrgList(String name) throws DAOException;

    /**
     * Connects to database and return list of organizations that is linked to accounts owned by user ID.
     *
     * @param userID   is user ID
     * @return List of {@link Organization} with all matching organizations.
     * @throws DAOException when problems with database connection occurs.
     */
    List<Organization> getOrgListByUserID(int userID) throws DAOException;

}
