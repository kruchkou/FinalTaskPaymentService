package dao;

import dao.impl.*;

public class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();

    private final UserDAO userDAO = new UserDAOImpl();
    private final CardDAO cardDAO = new CardDAOImpl();
    private final PaymentDAO paymentDAO = new PaymentDAOImpl();
    private final OrganizationDAO organizationDAO = new OrganizationDAOImpl();
    private final AccountDAO accountDAO = new AccountDAOImpl();

    private DAOProvider() {}

    public static DAOProvider getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public CardDAO getCardDAO() {
        return cardDAO;
    }

    public PaymentDAO getPaymentDAO() {
        return paymentDAO;
    }

    public OrganizationDAO getOrganizationDAO() {
        return organizationDAO;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }
}
