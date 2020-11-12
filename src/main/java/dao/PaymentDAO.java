package dao;

import dao.entity.Payment;
import dao.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentDAO {

    List<Payment> getInPaymentListByAccountID(int accountID) throws DAOException;
    List<Payment> getOutPaymentListByAccountID(int accountID) throws DAOException;
    List<Payment> getInPaymentListByUserID(int userID) throws DAOException;
    List<Payment> getOutPaymentListByUserID(int userID) throws DAOException;
    void transferMoney(int accountFromID, int accountToID, BigDecimal amount, String comment) throws DAOException;

}
