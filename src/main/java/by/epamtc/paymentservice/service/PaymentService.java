package by.epamtc.paymentservice.service;

import by.epamtc.paymentservice.bean.Payment;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    List<Payment> getInPaymentListByAccountID(int accountID) throws ServiceException;
    List<Payment> getOutPaymentListByAccountID(int accountID) throws ServiceException;
    List<Payment> getInPaymentListByUserID(int userID) throws ServiceException;
    List<Payment> getOutPaymentListByUserID(int userID) throws ServiceException;
    void transferMoney(int cardFromID, int accountToID, BigDecimal amount, String comment) throws ServiceException;

}
