package by.epamtc.paymentservice.dao;

import by.epamtc.paymentservice.bean.Account;
import by.epamtc.paymentservice.bean.AccountInfo;
import by.epamtc.paymentservice.dao.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface provides methods to interact with Accounts data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 *
 */
public interface AccountDAO {

    /**
     * Connects to database and returns all info about account by ID.
     *
     * @param accountID    is account ID value.
     * @return {@link Account} if account's data found, null if not.
     * @throws DAOException when problems with database connection occurs.
     */
    Account getAccount(int accountID) throws DAOException;

    /**
     * Connects to database and return list of accounts owned by user.
     *
     * @param userID   is user ID
     * @return List of {@link Account} with all matching accounts.
     * @throws DAOException when problems with database connection occurs.
     */
    List<Account> getAccountListByUserID(int userID) throws DAOException;

    /**
     * Connects to database and return list of active accounts owned by user.
     *
     * @param userID   is user ID
     * @return List of {@link Account} with all matching accounts.
     * @throws DAOException when problems with database connection occurs.
     */
    List<Account> getActiveAccountListByUserID(int userID) throws DAOException;

    /**
     * Connects to database and returns public info about account.
     *
     * @param accountID    is account ID value.
     * @return {@link AccountInfo} if account's data found, null if not.
     * @throws DAOException when problems with database connection occurs.
     */
    AccountInfo getAccountInfo(int accountID) throws DAOException;

    /**
     * Connects to database and returns list of public infos about accounts that have id like parameter.
     *
     * @param accountID    is account ID value.
     * @return List of {@link AccountInfo} with all matching public account infos.
     * @throws DAOException when problems with database connection occurs.
     */
    List<AccountInfo> getAccountInfoList(int accountID) throws DAOException;

    /**
     * Connects to database and returns list of public infos about active accounts that have id like parameter.
     *
     * @param accountID    is account ID value.
     * @return List of {@link AccountInfo} with all matching public account infos.
     * @throws DAOException when problems with database connection occurs.
     */
    List<AccountInfo> getActiveAccountInfoList(int accountID) throws DAOException;

    /**
     * Connects to database and add an new account.
     *
     * @param userID     is id of the user that creating new account.
     * @throws DAOException when problems with database connection occurs.
     */
    void addAccount(int userID) throws DAOException;

    /**
     * Connects to database and top up account by amount.
     *
     * @param accountID     is account ID value
     * @param amount     is {@link BigDecimal} object that contains amount.
     * @throws DAOException when problems with database connection occurs.
     */
    void topUpAccount(int accountID, BigDecimal amount) throws DAOException;

    /**
     * Connects to database and set account status to the account by ID.
     * if there are organization linked, sets its status too.
     *
     * @param id     is account ID value
     * @param status     is account status ID.
     * @throws DAOException when problems with database connection occurs.
     */
    void setStatusByID(int id, int status) throws DAOException;

}
