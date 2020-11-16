package by.epamtc.PaymentService.service.impl;

import by.epamtc.PaymentService.bean.Card;
import by.epamtc.PaymentService.dao.exception.DAOException;
import by.epamtc.PaymentService.dao.CardDAO;
import by.epamtc.PaymentService.dao.DAOProvider;
import by.epamtc.PaymentService.service.CardService;

import java.util.List;

public class CardServiceImpl implements CardService {

    private final DAOProvider daoProvider = DAOProvider.getInstance();
    private final CardDAO cardDAO = daoProvider.getCardDAO();

    public List<Card> getCardListByAccountID(int accountID) throws DAOException {
        return cardDAO.getCardListByAccountID(accountID);
    }

    public List<Card> getCardListByUserID(int userID) throws DAOException {
        return cardDAO.getCardListByUserID(userID);
    }

    public Card getCard(int cardID) throws DAOException {
        return cardDAO.getCard(cardID);
    }

    public void addCard(Card card) throws DAOException {
        cardDAO.addCard(card);
    }

    public void setStatus(int id, int status) throws DAOException {
        cardDAO.setStatusByID(id, status);
    }

}
