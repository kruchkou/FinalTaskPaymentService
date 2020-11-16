package by.epamtc.PaymentService.service.impl;

import by.epamtc.PaymentService.bean.Account;
import by.epamtc.PaymentService.bean.AccountInfo;
import by.epamtc.PaymentService.dao.exception.DAOException;
import by.epamtc.PaymentService.dao.AccountDAO;
import by.epamtc.PaymentService.dao.DAOProvider;
import by.epamtc.PaymentService.service.AccountService;

import java.math.BigDecimal;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final DAOProvider daoProvider = DAOProvider.getInstance();
    private final AccountDAO accountDAO = daoProvider.getAccountDAO();

    public void addAccount(int userID) throws DAOException {
        accountDAO.addAccount(userID);
    }

    public AccountInfo getAccountInfo(int accountID) throws DAOException {
        return accountDAO.getAccountInfo(accountID);
    }

    @Override
    public List<AccountInfo> getAccountInfoList(int accountID) throws DAOException {
        return accountDAO.getAccountInfoList(accountID);
    }

    public List<Account> getAccountList(int userID) throws DAOException {
        final int MAX_LIMIT = 100;
        final int OFFSET = 0;
        return accountDAO.getAccountListByUserID(userID, MAX_LIMIT, OFFSET);
    }

    public List<Account> getActiveAccountList(int userID) throws DAOException {
        final int MAX_LIMIT = 100;
        final int OFFSET = 0;
        return accountDAO.getActiveAccountListByUserID(userID, MAX_LIMIT, OFFSET);
    }

    public int getUserAccountsQuantity(int userID) throws DAOException {
        return accountDAO.getUserAccountsQuantity(userID);
    }

    public void topUp(int accountID, BigDecimal amount) throws DAOException {
        accountDAO.topUpAccount(accountID, amount);
    }

    public List<Account> getAccountList(int userID, int page) throws DAOException {
        final int ACCOUNTS_PER_PAGE = 10;
        final int OFFSET = page * ACCOUNTS_PER_PAGE;
        return accountDAO.getAccountListByUserID(userID, ACCOUNTS_PER_PAGE, OFFSET);
    }

    public Account getAccount(int accountID) throws DAOException {
        return accountDAO.getAccountByAccountID(accountID);
    }

    public void setStatus(int id, int status) throws DAOException {
        accountDAO.setStatusByID(id, status);
    }

}
