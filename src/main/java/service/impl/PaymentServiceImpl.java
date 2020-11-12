package service.impl;

import dao.DAOProvider;
import dao.PaymentDAO;
import dao.exception.DAOException;
import dao.entity.Payment;
import service.PaymentService;

import java.math.BigDecimal;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private final DAOProvider daoProvider = DAOProvider.getInstance();
    private final PaymentDAO paymentDAO = daoProvider.getPaymentDAO();

    public List<Payment> getInPaymentListByAccountID(int accountID) throws DAOException {
        return paymentDAO.getInPaymentListByAccountID(accountID);
    }

    public List<Payment> getOutPaymentListByAccountID(int accountID) throws DAOException {
        return paymentDAO.getOutPaymentListByAccountID(accountID);
    }

    public List<Payment> getInPaymentListByUserID(int userID) throws DAOException {
        return paymentDAO.getInPaymentListByAccountID(userID);
    }

    public List<Payment> getOutPaymentListByUserID(int userID) throws DAOException {
        return paymentDAO.getOutPaymentListByAccountID(userID);
    }

    public void transferMoney(int cardFromID, int accountToID, BigDecimal amount, String comment) throws DAOException {
        paymentDAO.transferMoney(cardFromID,accountToID,amount,comment);
    }

}
