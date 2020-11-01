package dao;

import dao.impl.*;

public class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();

    private final UserDAO userDAO = new UserDAOImpl();
    private final CardDAO carDAO = new CardDAOImpl();
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

    public CardDAO getCarDAO() {
        return carDAO;
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
