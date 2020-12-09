package by.epamtc.paymentservice.dao;

import by.epamtc.paymentservice.dao.impl.*;

/**
 * Factory class that provides implementations of DAO interfaces.
 */
public class DAOProvider {

    /** Instance of the class */
    private static final DAOProvider instance = new DAOProvider();
    /** An object of {@link UserDAOImpl} */
    private final UserDAO userDAO = UserDAOImpl.getInstance();
    /** An object of {@link CardDAOImpl} */
    private final CardDAO cardDAO = CardDAOImpl.getInstance();
    /** An object of {@link PaymentDAOImpl} */
    private final PaymentDAO paymentDAO = PaymentDAOImpl.getInstance();
    /** An object of {@link OrganizationDAOImpl} */
    private final OrganizationDAO organizationDAO = OrganizationDAOImpl.getInstance();
    /** An object of {@link AccountDAOImpl} */
    private final AccountDAO accountDAO = AccountDAOImpl.getInstance();

    /** Private constructor without parameters */
    private DAOProvider() {
    }

    /**
     * Returns the instance of the class
     * @return Object of {@link DAOProvider}
     */
    public static DAOProvider getInstance() {
        return instance;
    }

    /**
     * Method returns field of {@link UserDAO} object
     * @return {@link UserDAO} object
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }
    /**
     * Method returns field of {@link CardDAO} object
     * @return {@link CardDAO} object
     */
    public CardDAO getCardDAO() {
        return cardDAO;
    }
    /**
     * Method returns field of {@link PaymentDAO} object
     * @return {@link PaymentDAO} object
     */
    public PaymentDAO getPaymentDAO() {
        return paymentDAO;
    }
    /**
     * Method returns field of {@link OrganizationDAO} object
     * @return {@link OrganizationDAO} object
     */
    public OrganizationDAO getOrganizationDAO() {
        return organizationDAO;
    }
    /**
     * Method returns field of {@link AccountDAO} object
     * @return {@link AccountDAO} object
     */
    public AccountDAO getAccountDAO() {
        return accountDAO;
    }
}
