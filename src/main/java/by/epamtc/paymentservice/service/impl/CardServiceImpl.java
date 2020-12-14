package by.epamtc.paymentservice.service.impl;

import by.epamtc.paymentservice.bean.Card;
import by.epamtc.paymentservice.dao.CardDAO;
import by.epamtc.paymentservice.dao.DAOProvider;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.service.CardService;
import by.epamtc.paymentservice.service.exception.ServiceException;
import by.epamtc.paymentservice.service.validator.CardValidator;

import java.util.List;

public class CardServiceImpl implements CardService {

    private static final DAOProvider daoProvider = DAOProvider.getInstance();
    private static final CardDAO cardDAO = daoProvider.getCardDAO();
    private static final CardValidator cardValidator = CardValidator.getInstance();

    @Override
    public List<Card> getCardListByAccountID(int accountID) throws ServiceException {
        try {
            return cardDAO.getCardListByAccountID(accountID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getCardListByAccountID request at CardService",e);
        }
    }

    @Override
    public List<Card> getCardListByUserID(int userID) throws ServiceException {
        try {
            return cardDAO.getCardListByUserID(userID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getCardListByUserID request at CardService",e);
        }
    }

    @Override
    public Card getCard(int cardID) throws ServiceException {
        try {
            return cardDAO.getCard(cardID);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getCard request at CardService",e);
        }
    }

    @Override
    public void addCard(Card card) throws ServiceException {
        if (cardValidator.validate(card)) {
            try {
                cardDAO.addCard(card);
            } catch (DAOException e) {
                throw new ServiceException("Can't handle addCard request at CardService",e);
            }
        } else {
            throw new ServiceException("Card data didn't passed validation at addCard");
        }
    }

    @Override
    public void setStatus(int id, int status) throws ServiceException {
        try {
            cardDAO.setStatusByID(id, status);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle setStatus request at CardService",e);
        }
    }

}
