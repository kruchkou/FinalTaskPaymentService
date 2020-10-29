package controller;

import dao.CardDAO;
import dao.DAOException;
import dao.entity.Card;

import java.util.List;

public class CardController {

    private static final CardController instance = new CardController();
    private final CardDAO cardDAO = new CardDAO();

    private CardController() {
    }

    public CardController getInstance() {
        return instance;
    }

    public List<Card> getCardList(int accountID) throws DAOException {
        return cardDAO.getCardListByAccountID(accountID);
    }

    public void addCard(Card card) throws DAOException {
        cardDAO.addCard(card);
    }

    public void setStatus(int id, int status) throws DAOException {
        cardDAO.setStatusByID(id, status);
    }

}
