package service;

import dao.AccountDAO;
import dao.DAOProvider;
import dao.exception.DAOException;
import dao.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {

    private static final AccountService instance = new AccountService();
    private final DAOProvider daoProvider = DAOProvider.getInstance();
    private final AccountDAO accountDAO = daoProvider.getAccountDAO();

    private AccountService() {
    }

    public static AccountService getInstance() {
        return instance;
    }

    public void createAccount(int type, int userID) throws DAOException {
        accountDAO.addAccount(type, userID);
    }

    public void sendMoney(int idFrom, int idTo, BigDecimal amount) throws DAOException {
        accountDAO.sendMoney(idFrom, idTo, amount);
    }

    public List<Account> getAccountList(int userID) throws DAOException {
        return accountDAO.getAccountListByUserID(userID);
    }

    public Account getAccount(int accountID) throws DAOException {
        return accountDAO.getAccountByAccountID(accountID);
    }

    public void setStatus(int id, int status) throws DAOException {
        accountDAO.setStatusByID(id, status);
    }

}
