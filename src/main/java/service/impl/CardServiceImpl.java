package service.impl;

import dao.CardDAO;
import dao.DAOProvider;
import dao.entity.Card;
import dao.exception.DAOException;
import service.CardService;

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
