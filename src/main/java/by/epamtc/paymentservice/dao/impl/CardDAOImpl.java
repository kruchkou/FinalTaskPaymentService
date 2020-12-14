package by.epamtc.paymentservice.dao.impl;

import by.epamtc.paymentservice.bean.User;
import by.epamtc.paymentservice.dao.connection.ConnectionPool;
import by.epamtc.paymentservice.dao.connection.impl.ConnectionPoolImpl;
import by.epamtc.paymentservice.bean.Card;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.dao.CardDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Implementation of {@link CardDAO}. Provides methods to interact with Users data from database.
 * Methods connect to database using {@link Connection} from {@link ConnectionPoolImpl} and manipulate with data(save, edit, etc.).
 *
 */
public class CardDAOImpl implements CardDAO {

    /** Instance of the class */
    private static final CardDAOImpl instance = new CardDAOImpl();

    /** An object of {@link ConnectionPoolImpl} */
    private static final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    /** Query for database to add an card */
    private static final String INSERT_CARD_SQL = "INSERT INTO Cards(account,number,owner_name,exp_date,cvv,status) VALUES (?,?,?,?,?,?)";

    /** Query for database to set card status by card ID */
    private static final String SET_STATUS_BY_ID_SQL = "UPDATE Cards SET status = ? WHERE id = ?";

    /** Query for database to get an card data by card ID */
    private static final String GET_CARD_BY_CARD_ID_SQL = "SELECT cards.id, account, number, owner_name, exp_date, cvv, cards.status, cardstatus.name FROM Cards cards " +
            "JOIN CardStatuses cardstatus ON cards.status = cardstatus.id " +
            "WHERE (cards.id = ?)";

    /** Query for database to get list of cards that linked to an account ID */
    private static final String GET_CARD_LIST_BY_ACCOUNT_ID_SQL = "SELECT cards.id, account, number, owner_name, exp_date, cvv, cards.status, cardstatus.name FROM Cards cards " +
            "JOIN CardStatuses cardstatus ON cards.status = cardstatus.id " +
            "WHERE (account = ? AND cards.status = ?)";

    /** Query for database to get list of cards that linked to accounts owned by user ID */
    private static final String GET_CARD_LIST_BY_USER_ID_SQL = "SELECT cards.id, account, number, owner_name, exp_date, cvv, cards.status, cardstatus.name FROM Cards cards " +
            "JOIN Accounts accounts ON cards.account = accounts.id " +
            "JOIN CardStatuses cardstatus ON cards.status = cardstatus.id " +
            "WHERE (accounts.user = ? AND cards.status = ?)";

    /** Message, that is putted in Exception if there are get card problem */
    private static final String MESSAGE_GET_CARD_PROBLEM = "Cant handle CardDAO.getCard request";

    /** Message, that is putted in Exception if there are add card problem */
    private static final String MESSAGE_ADD_CARD_PROBLEM = "Can't handle CardDAO.addCard request";

    /** Message, that is putted in Exception if there are set status by ID problem */
    private static final String MESSAGE_SET_STATUS_BY_ID_PROBLEM = "Can't handle CardDAO.setStatusByID request";


    /**
     * Returns the instance of the class
     * @return Object of {@link CardDAOImpl}
     */
    public static CardDAOImpl getInstance() {
        return instance;
    }

    /** Private constructor without parameters */
    private CardDAOImpl() {

    }

    /**
     * Connects to database and return list of cards that linked to an account
     *
     * @param accountID   is account ID
     * @return List of {@link Card} with all matching cards.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<Card> getCardListByAccountID(int accountID) throws DAOException {
        return getCardListByID(accountID, GET_CARD_LIST_BY_ACCOUNT_ID_SQL);
    }


    /**
     * Connects to database and return list of cards that linked to the accounts owned by user.
     *
     * @param userID    is user ID
     * @return List of {@link Card} with all matching cards.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<Card> getCardListByUserID(int userID) throws DAOException {
        return getCardListByID(userID, GET_CARD_LIST_BY_USER_ID_SQL);
    }


    /**
     * Connects to database and returns {@link Card} object, that contents all info about card by ID.
     *
     * @param cardID    is Card ID value.
     * @return {@link User} if card's data found, null if not.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public Card getCard(int cardID) throws DAOException {
        Card card = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_CARD_BY_CARD_ID_SQL);

            ps.setInt(GetCardByIDIndex.ID, cardID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                card = new Card();
                HashMap<Integer, String> status = new HashMap<>();
                status.put((rs.getInt(ParamColumn.STATUS_ID)), rs.getString(ParamColumn.STATUS_NAME));

                card.setId(rs.getInt(ParamColumn.ID));
                card.setAccount(rs.getInt(ParamColumn.ACCOUNT));
                card.setNumber(rs.getString(ParamColumn.NUMBER));
                card.setOwnerName(rs.getString(ParamColumn.OWNER_NAME));
                card.setExpDate(rs.getDate(ParamColumn.EXP_DATE));
                card.setCvv(rs.getInt(ParamColumn.CVV));
                card.setStatus(status);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_GET_CARD_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return card;
    }


    /**
     * Connects to database and return list of cards by provided ID and SQL request
     *
     * @param id    is linked ID
     * @param sql   is text that contains SQL request.
     * @return List of {@link Card} with all matching cards.
     * @throws DAOException when problems with database connection occurs.
     */
    private List<Card> getCardListByID(int id, String sql) throws DAOException {
        final int STATUS_OPENED = 1;
        List<Card> cardList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setInt(GetCardByIDIndex.ID, id);
            ps.setInt(GetCardByIDIndex.STATUS, STATUS_OPENED);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Card card = new Card();
                HashMap<Integer, String> status = new HashMap<>();
                status.put((rs.getInt(ParamColumn.STATUS_ID)), rs.getString(ParamColumn.STATUS_NAME));

                card.setId(rs.getInt(ParamColumn.ID));
                card.setAccount(rs.getInt(ParamColumn.ACCOUNT));
                card.setNumber(rs.getString(ParamColumn.NUMBER));
                card.setOwnerName(rs.getString(ParamColumn.OWNER_NAME));
                card.setExpDate(rs.getDate(ParamColumn.EXP_DATE));
                card.setCvv(rs.getInt(ParamColumn.CVV));
                card.setStatus(status);

                cardList.add(card);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_GET_CARD_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return cardList;
    }


    /**
     * Connects to database and add an new card.
     *
     * @param card     is ${@link Card} object that contains all info about card.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public void addCard(Card card) throws DAOException {
        final int STATUS_ACTIVE = 1;

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(INSERT_CARD_SQL);

            ps.setInt(AddCardIndex.ACCOUNT, card.getAccount());
            ps.setString(AddCardIndex.NUMBER, card.getNumber());
            ps.setString(AddCardIndex.OWNER_NAME, card.getOwnerName());
            ps.setDate(AddCardIndex.EXP_DATE, new Date(card.getExpDate().getTime()));
            ps.setInt(AddCardIndex.CVV, card.getCvv());
            ps.setInt(AddCardIndex.STATUS, STATUS_ACTIVE);

            ps.execute();

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_ADD_CARD_PROBLEM);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }


    /**
     * Connects to database and set card status to the card by ID.
     *
     * @param id     is card ID value
     * @param status     is card status ID.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public void setStatusByID(int id, int status) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SET_STATUS_BY_ID_SQL);
            ps.setInt(SetStatusByIDIndex.STATUS, status);
            ps.setInt(SetStatusByIDIndex.ID, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SET_STATUS_BY_ID_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    /**
     * Static class that contains parameters of Cards table.
     */
    private static class ParamColumn {
        private static final String ID = "cards.id";
        private static final String ACCOUNT = "account";
        private static final String NUMBER = "number";
        private static final String OWNER_NAME = "owner_name";
        private static final String EXP_DATE = "exp_date";
        private static final String CVV = "cvv";
        private static final String STATUS_ID = "cards.status";
        private static final String STATUS_NAME = "cardstatus.name";
    }

    /**
     * Static class that contains parameter indexes for creation a card
     */
    private static class AddCardIndex {
        public static final int ACCOUNT = 1;
        private static final int NUMBER = 2;
        public static final int OWNER_NAME = 3;
        public static final int EXP_DATE = 4;
        public static final int CVV = 5;
        public static final int STATUS = 6;
    }


    /**
     * Static class that contains parameter indexes for getting a card by ID
     */
    private static class GetCardByIDIndex {
        private static final int ID = 1;
        private static final int STATUS = 2;
    }


    /**
     * Static class that contains parameter indexes for setting status by ID
     */
    private static class SetStatusByIDIndex {
        private static final int STATUS = 1;
        private static final int ID = 2;
    }

}
