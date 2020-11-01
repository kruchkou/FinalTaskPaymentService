package service;

import dao.DAOProvider;
import dao.PaymentDAO;
import dao.exception.DAOException;
import dao.entity.Payment;

import java.util.List;

public class PaymentService {

    private static final PaymentService instance = new PaymentService();
    private final DAOProvider daoProvider = DAOProvider.getInstance();
    private final PaymentDAO paymentDAO = daoProvider.getPaymentDAO();

    private PaymentService() {
    }

    public static PaymentService getInstance() {
        return instance;
    }

    public List<Payment> getInPaymentList(int accountID) throws DAOException {
        return paymentDAO.getInPaymentListByAccountID(accountID);
    }

    public List<Payment> getOutPaymentList(int accountID) throws DAOException {
        return paymentDAO.getOutPaymentListByAccountID(accountID);
    }

    public void addPayment(Payment payment, int paymentTypeID) throws DAOException {
        paymentDAO.addPayment(payment, paymentTypeID);
    }

}
