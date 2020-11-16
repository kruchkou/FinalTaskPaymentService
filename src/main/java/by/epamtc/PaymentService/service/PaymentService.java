package by.epamtc.PaymentService.service;

import by.epamtc.PaymentService.bean.Payment;
import by.epamtc.PaymentService.dao.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    List<Payment> getInPaymentListByAccountID(int accountID) throws DAOException;
    List<Payment> getOutPaymentListByAccountID(int accountID) throws DAOException;
    List<Payment> getInPaymentListByUserID(int userID) throws DAOException;
    List<Payment> getOutPaymentListByUserID(int userID) throws DAOException;
    void transferMoney(int cardFromID, int accountToID, BigDecimal amount, String comment) throws DAOException;

}
