package controller;

import dao.DAOException;
import dao.PaymentDAO;
import dao.entity.Payment;

import java.util.List;

public class PaymentController {

    private static final PaymentController instance = new PaymentController();
    private final PaymentDAO paymentDAO = new PaymentDAO();

    private PaymentController() {
    }

    public static PaymentController getInstance() {
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
