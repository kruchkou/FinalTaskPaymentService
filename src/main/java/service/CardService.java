package service;

import dao.entity.Card;
import dao.exception.DAOException;

import java.util.List;

public interface CardService {

    List<Card> getCardListByAccountID(int accountID) throws DAOException;
    List<Card> getCardListByUserID(int userID) throws DAOException;
    Card getCard(int cardID) throws DAOException;
    void addCard(Card card) throws DAOException;
    void setStatus(int id, int status) throws DAOException;

}
