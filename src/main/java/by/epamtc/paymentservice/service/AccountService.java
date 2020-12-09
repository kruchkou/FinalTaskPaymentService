package by.epamtc.paymentservice.service;

import by.epamtc.paymentservice.bean.Account;
import by.epamtc.paymentservice.bean.AccountInfo;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    void addAccount(int userID) throws ServiceException;
    AccountInfo getAccountInfo(int accountID) throws ServiceException;
    List<AccountInfo> getAccountInfoList(int accountID) throws ServiceException;
    List<AccountInfo> getActiveAccountInfoList(int accountID) throws ServiceException;
    List<Account> getAccountList(int userID) throws ServiceException;
    List<Account> getActiveAccountList(int userID) throws ServiceException;
    void topUp(int accountID, BigDecimal amount) throws ServiceException;
    Account getAccount(int accountID) throws ServiceException;
    void setStatus(int id, int status) throws ServiceException;

}
