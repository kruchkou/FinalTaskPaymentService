package by.epamtc.paymentservice.dao;

import by.epamtc.paymentservice.bean.Card;
import by.epamtc.paymentservice.dao.exception.DAOException;

import java.util.List;

/**
 * Interface provides methods to interact with Cards data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 *
 */
public interface CardDAO {

    /**
     * Connects to database and return list of cards that linked to an account
     *
     * @param accountID   is account ID
     * @return List of {@link Card} with all matching cards.
     * @throws DAOException when problems with database connection occurs.
     */
    List<Card> getCardListByAccountID(int accountID) throws DAOException;

    /**
     * Connects to database and return list of cards that linked to the accounts owned by user.
     *
     * @param userID    is user ID
     * @return List of {@link Card} with all matching cards.
     * @throws DAOException when problems with database connection occurs.
     */
    List<Card> getCardListByUserID(int userID) throws DAOException;

    /**
     * Connects to database and returns {@link Card} object, that contents all info about card by ID.
     *
     * @param cardID    is Card ID value.
     * @return {@link Card} if card's data found, null if not.
     * @throws DAOException when problems with database connection occurs.
     */
    Card getCard(int cardID) throws DAOException;

    /**
     * Connects to database and add an new card.
     *
     * @param card     is ${@link Card} object that contains all info about card.
     * @throws DAOException when problems with database connection occurs.
     */
    void addCard(Card card) throws DAOException;

    /**
     * Connects to database and set card status to the card by ID.
     *
     * @param id     is card ID value
     * @param status     is card status ID.
     * @throws DAOException when problems with database connection occurs.
     */
    void setStatusByID(int id, int status) throws DAOException;

}
