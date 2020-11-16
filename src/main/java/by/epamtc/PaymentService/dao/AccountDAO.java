package by.epamtc.PaymentService.dao;

import by.epamtc.PaymentService.bean.Account;
import by.epamtc.PaymentService.bean.AccountInfo;
import by.epamtc.PaymentService.dao.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDAO {

    List<Account> getAccountListByUserID(int userID, int limit, int offset) throws DAOException;
    List<Account> getActiveAccountListByUserID(int userID, int limit, int offset) throws DAOException;
    Account getAccountByAccountID(int accountID) throws DAOException;
    AccountInfo getAccountInfo(int accountID) throws DAOException;
    List<AccountInfo> getAccountInfoList(int accountID) throws DAOException;
    int getUserAccountsQuantity(int userID) throws DAOException;
    void addAccount(int userID) throws DAOException;
    void topUpAccount(int accountID, BigDecimal amount) throws DAOException;
    void setStatusByID(int id, int status) throws DAOException;

}
