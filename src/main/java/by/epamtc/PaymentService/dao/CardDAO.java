package by.epamtc.PaymentService.dao;

import by.epamtc.PaymentService.bean.Card;
import by.epamtc.PaymentService.dao.exception.DAOException;

import java.util.List;

public interface CardDAO {

    List<Card> getCardListByAccountID(int accountID) throws DAOException;
    List<Card> getCardListByUserID(int userID) throws DAOException;
    Card getCard(int cardID) throws DAOException;
    void addCard(Card card) throws DAOException;
    void setStatusByID(int id, int status) throws DAOException;

}