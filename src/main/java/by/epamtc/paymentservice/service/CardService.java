package by.epamtc.paymentservice.service;

import by.epamtc.paymentservice.bean.Card;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.service.exception.ServiceException;

import java.util.List;

public interface CardService {

    List<Card> getCardListByAccountID(int accountID) throws ServiceException;
    List<Card> getCardListByUserID(int userID) throws ServiceException;
    Card getCard(int cardID) throws ServiceException;
    void addCard(Card card) throws ServiceException;
    void setStatus(int id, int status) throws ServiceException;

}
