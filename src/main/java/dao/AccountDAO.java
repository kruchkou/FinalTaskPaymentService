package dao;

import dao.entity.Account;
import dao.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDAO {

    List<Account> getAccountListByUserID(int userID) throws DAOException;
    void addAccount(int type, int userID) throws DAOException;
    Account getAccountByAccountID(int accountID) throws DAOException;
    void setStatusByID(int id, int status) throws DAOException;
    void sendMoney(int idFrom, int idTo, BigDecimal amount) throws DAOException;

}
