package by.epamtc.paymentservice.service.impl;

import by.epamtc.paymentservice.bean.Payment;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.dao.DAOProvider;
import by.epamtc.paymentservice.dao.PaymentDAO;
import by.epamtc.paymentservice.service.PaymentService;
import by.epamtc.paymentservice.service.exception.ServiceException;
import by.epamtc.paymentservice.util.validator.PaymentValidator;
import by.epamtc.paymentservice.util.validator.UserValidator;

import java.math.BigDecimal;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentValidator paymentValidator = PaymentValidator.getInstance();
    private final DAOProvider daoProvider = DAOProvider.getInstance();
    private final PaymentDAO paymentDAO = daoProvider.getPaymentDAO();

    @Override
    public List<Payment> getInPaymentListByAccountID(int accountID) throws ServiceException {
        try {
            return paymentDAO.getInPaymentListByAccountID(accountID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getInPaymentListByAccountID request at PaymentService",e);
        }
    }

    @Override
    public List<Payment> getOutPaymentListByAccountID(int accountID) throws ServiceException {
        try {
            return paymentDAO.getOutPaymentListByAccountID(accountID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getOutPaymentListByAccountID request at PaymentService",e);
        }
    }

    @Override
    public List<Payment> getInPaymentListByUserID(int userID) throws ServiceException {
        try {
            return paymentDAO.getInPaymentListByUserID(userID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getInPaymentListByUserID request at PaymentService",e);
        }
    }

    @Override
    public List<Payment> getOutPaymentListByUserID(int userID) throws ServiceException {
        try {
            return paymentDAO.getOutPaymentListByUserID(userID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getOutPaymentListByUserID request at PaymentService",e);
        }
    }

    @Override
    public void transferMoney(int cardFromID, int accountToID, BigDecimal amount, String comment) throws ServiceException {
        if (!paymentValidator.validateAmount(amount)) {
            throw new ServiceException("Amount didn't passed validation");
        } else {
            try {
                paymentDAO.transferMoney(cardFromID,accountToID,amount,comment);
            } catch (DAOException e) {
                throw new ServiceException("Can't handle transferMoney request at PaymentService", e);
            }
        }
    }

}
