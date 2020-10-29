package controller;

import dao.AccountDAO;
import dao.DAOException;
import dao.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public class AccountController {

    private static final AccountController instance = new AccountController();
    private final AccountDAO accountDAO = new AccountDAO();

    private AccountController() {
    }

    public AccountController getInstance() {
        return instance;
    }

    public void createAccount(int type, int userID) throws DAOException {
        accountDAO.createAccount(type, userID);
    }

    public void sendMoney(int idFrom, int idTo, BigDecimal amount) throws DAOException {
        accountDAO.sendMoney(idFrom, idTo, amount);
    }

    public List<Account> getAccountList(int userID) throws DAOException {
        return accountDAO.getAccountListByUserID(userID);
    }

    public void setStatus(int id, int status) throws DAOException {
        accountDAO.setStatusByID(id, status);
    }

}
