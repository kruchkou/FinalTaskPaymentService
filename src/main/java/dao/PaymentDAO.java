package dao;

import dao.entity.Payment;
import dao.exception.DAOException;

import java.util.List;

public interface PaymentDAO {

    List<Payment> getInPaymentListByAccountID(int accountID) throws DAOException;
    List<Payment> getOutPaymentListByAccountID(int accountID) throws DAOException;
    void addPayment(Payment payment, int paymentType) throws DAOException;

}
