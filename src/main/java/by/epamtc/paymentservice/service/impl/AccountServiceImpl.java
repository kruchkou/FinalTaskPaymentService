package by.epamtc.paymentservice.service.impl;

import by.epamtc.paymentservice.bean.Account;
import by.epamtc.paymentservice.bean.AccountInfo;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.dao.AccountDAO;
import by.epamtc.paymentservice.dao.DAOProvider;
import by.epamtc.paymentservice.service.AccountService;
import by.epamtc.paymentservice.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final DAOProvider daoProvider = DAOProvider.getInstance();
    private final AccountDAO accountDAO = daoProvider.getAccountDAO();

    @Override
    public void addAccount(int userID) throws ServiceException {
        try {
            accountDAO.addAccount(userID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle addAccount request at AccountService",e);
        }
    }

    @Override
    public AccountInfo getAccountInfo(int accountID) throws ServiceException {
        try {
            return accountDAO.getAccountInfo(accountID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getAccountInfo request at AccountService",e);
        }
    }

    @Override
    public List<AccountInfo> getAccountInfoList(int accountID) throws ServiceException {
        try {
            return accountDAO.getAccountInfoList(accountID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getAccountInfoList request at AccountService",e);
        }
    }

    @Override
    public List<AccountInfo> getActiveAccountInfoList(int accountID) throws ServiceException {
        try {
            return accountDAO.getActiveAccountInfoList(accountID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getAccountInfoList request at AccountService",e);
        }
    }

    @Override
    public List<Account> getActiveAccountList(int userID) throws ServiceException {
        try {
            return accountDAO.getActiveAccountListByUserID(userID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getActiveAccountListByUserID request at AccountService",e);
        }
    }

    @Override
    public void topUp(int accountID, BigDecimal amount) throws ServiceException {
        try {
            accountDAO.topUpAccount(accountID, amount);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle topUp request at AccountService",e);
        }
    }

    @Override
    public List<Account> getAccountList(int userID) throws ServiceException {
        try {
            return accountDAO.getAccountListByUserID(userID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getAccountList request at AccountService",e);
        }
    }

    @Override
    public Account getAccount(int accountID) throws ServiceException {
        try {
            return accountDAO.getAccount(accountID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getAccount request at AccountService",e);
        }
    }

    @Override
    public void setStatus(int id, int status) throws ServiceException {
        try {
            accountDAO.setStatusByID(id, status);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle setStatus request at AccountService",e);
        }
    }

}
