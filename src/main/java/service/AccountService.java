package service;

import dao.entity.Account;
import dao.entity.AccountInfo;
import dao.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    void addAccount(int userID) throws DAOException;
    AccountInfo getAccountInfo(int accountID) throws DAOException;
    List<AccountInfo> getAccountInfoList(int accountID) throws DAOException;
    List<Account> getAccountList(int userID) throws DAOException;
    List<Account> getActiveAccountList(int userID) throws DAOException;
    void topUp(int accountID, BigDecimal amount) throws DAOException;
    List<Account> getAccountList(int userID, int page) throws DAOException;
    Account getAccount(int accountID) throws DAOException;
    void setStatus(int id, int status) throws DAOException;

}
