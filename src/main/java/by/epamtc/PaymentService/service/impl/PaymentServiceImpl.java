package by.epamtc.PaymentService.service.impl;

import by.epamtc.PaymentService.bean.Payment;
import by.epamtc.PaymentService.dao.exception.DAOException;
import by.epamtc.PaymentService.dao.DAOProvider;
import by.epamtc.PaymentService.dao.PaymentDAO;
import by.epamtc.PaymentService.service.PaymentService;

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
