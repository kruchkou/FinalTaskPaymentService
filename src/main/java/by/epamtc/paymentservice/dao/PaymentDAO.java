package by.epamtc.paymentservice.dao;

import by.epamtc.paymentservice.bean.Payment;
import by.epamtc.paymentservice.dao.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface provides methods to interact with Payments data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 *
 */
public interface PaymentDAO {


    /**
     * Connects to database and returns List of {@link Payment} objects by account ID that receives payments.
     *
     * @param accountID     is ID of the Account that received payments.
     * @return List of {@link Payment} with all matching payments.
     * @throws DAOException when problems with database connection occurs.
     */
    List<Payment> getInPaymentListByAccountID(int accountID) throws DAOException;

    /**
     * Connects to database and returns List of {@link Payment} objects by account ID that sends payments.
     *
     * @param accountID     is ID of the Account that sends payments.
     * @return List of {@link Payment} with all matching payments.
     * @throws DAOException when problems with database connection occurs.
     */
    List<Payment> getOutPaymentListByAccountID(int accountID) throws DAOException;

    /**
     * Connects to database and returns List of {@link Payment} objects by user ID that receives payments.
     *
     * @param userID     is ID of the User that received payments.
     * @return List of {@link Payment} with all matching payments.
     * @throws DAOException when problems with database connection occurs.
     */
    List<Payment> getInPaymentListByUserID(int userID) throws DAOException;

    /**
     * Connects to database and returns List of {@link Payment} objects by user ID that sends payments.
     *
     * @param userID     is ID of the User that received payments.
     * @return List of {@link Payment} with all matching payments.
     * @throws DAOException when problems with database connection occurs.
     */
    List<Payment> getOutPaymentListByUserID(int userID) throws DAOException;

    /**
     * Connects to database and transfers money from one account to another.
     *
     * @param accountFromID     is ID of the Account that pays.
     * @param accountToID     is ID of the Account that receives payment.
     * @param amount     is {@link BigDecimal} object that contains amount of payment.
     * @param comment     is text contains comment for payment.
     * @throws DAOException when problems with database connection occurs.
     */
    void transferMoney(int accountFromID, int accountToID, BigDecimal amount, String comment) throws DAOException;

    /**
     * Connects to database and returns payment data by ID.
     *
     * @param id    is Payment ID value.
     * @return {@link Payment} if payment data found, null if not.
     * @throws DAOException when problems with database connection occurs.
     */
     Payment getPayment(int id) throws DAOException;

}
