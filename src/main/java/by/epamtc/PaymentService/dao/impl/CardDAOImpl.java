package by.epamtc.PaymentService.dao.impl;

import by.epamtc.PaymentService.dao.connection.impl.ConnectionPool;
import by.epamtc.PaymentService.bean.Card;
import by.epamtc.PaymentService.dao.exception.DAOException;
import by.epamtc.PaymentService.dao.CardDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CardDAOImpl implements CardDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String INSERT_CARD_SQL = "INSERT INTO Cards(account,number,owner_name,exp_date,cvv,status) VALUES (?,?,?,?,?,?)";
    private static final String SET_STATUS_BY_ID_SQL = "UPDATE Cards SET status = ? WHERE id = ?";
    private static final String GET_CARD_BY_CARD_ID_SQL = "SELECT cards.id, account, number, owner_name, exp_date, cvv, cards.status, cardstatus.name FROM Cards cards " +
            "JOIN CardStatuses cardstatus ON cards.status = cardstatus.id " +
            "WHERE (cards.id = ?)";
    private static final String GET_CARD_LIST_BY_ACCOUNT_ID_SQL = "SELECT cards.id, account, number, owner_name, exp_date, cvv, cards.status, cardstatus.name FROM Cards cards " +
            "JOIN CardStatuses cardstatus ON cards.status = cardstatus.id " +
            "WHERE (account = ? AND cards.status = ?)";
    private static final String GET_CARD_LIST_BY_USER_ID_SQL = "SELECT cards.id, account, number, owner_name, exp_date, cvv, cards.status, cardstatus.name FROM Cards cards " +
            "JOIN Accounts accounts ON cards.account = accounts.id " +
            "JOIN CardStatuses cardstatus ON cards.status = cardstatus.id " +
            "WHERE (accounts.user = ? AND cards.status = ?)";

    public CardDAOImpl() {
    }

    @Override
    public List<Card> getCardListByAccountID(int accountID) throws DAOException {
        return getCardListByID(accountID, GET_CARD_LIST_BY_ACCOUNT_ID_SQL);
    }

    @Override
    public List<Card> getCardListByUserID(int userID) throws DAOException {
        return getCardListByID(userID, GET_CARD_LIST_BY_USER_ID_SQL);
    }

    @Override
    public Card getCard(int cardID) throws DAOException {
        Card card = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_CARD_BY_CARD_ID_SQL);

            ps.setInt(GetCardByIDIndex.id, cardID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                card = new Card();
                HashMap<Integer, String> status = new HashMap<>();
                status.put((rs.getInt(ParamColumn.statusID)), rs.getString(ParamColumn.statusName));

                card.setId(rs.getInt(ParamColumn.id));
                card.setAccount(rs.getInt(ParamColumn.account));
                card.setNumber(rs.getString(ParamColumn.number));
                card.setOwnerName(rs.getString(ParamColumn.ownerName));
                card.setExpDate(rs.getDate(ParamColumn.expDate));
                card.setCvv(rs.getInt(ParamColumn.cvv));
                card.setStatus(status);
            }
        } catch (SQLException e) {
            throw new DAOException("Cant handle CardDAO.getCard request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return card;
    }

    private List<Card> getCardListByID(int id, String sql) throws DAOException {
        final int STATUS_OPENED = 1;
        List<Card> cardList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setInt(GetCardByIDIndex.id, id);
            ps.setInt(GetCardByIDIndex.status, STATUS_OPENED);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Card card = new Card();
                HashMap<Integer, String> status = new HashMap<>();
                status.put((rs.getInt(ParamColumn.statusID)), rs.getString(ParamColumn.statusName));

                card.setId(rs.getInt(ParamColumn.id));
                card.setAccount(rs.getInt(ParamColumn.account));
                card.setNumber(rs.getString(ParamColumn.number));
                card.setOwnerName(rs.getString(ParamColumn.ownerName));
                card.setExpDate(rs.getDate(ParamColumn.expDate));
                card.setCvv(rs.getInt(ParamColumn.cvv));
                card.setStatus(status);

                cardList.add(card);
            }
        } catch (SQLException e) {
            throw new DAOException("Cant handle CardDAO.getCardListByID request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return cardList;
    }

    @Override
    public void addCard(Card card) throws DAOException {
        final int STATUS_ACTIVE = 1;

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(INSERT_CARD_SQL);

            ps.setInt(AddCardIndex.account, card.getAccount());
            ps.setString(AddCardIndex.number, card.getNumber());
            ps.setString(AddCardIndex.ownerName, card.getOwnerName());
            ps.setDate(AddCardIndex.expDate, new Date(card.getExpDate().getTime()));
            ps.setInt(AddCardIndex.cvv, card.getCvv());
            ps.setInt(AddCardIndex.status, STATUS_ACTIVE);

            ps.execute();

        } catch (SQLException e) {
            throw new DAOException("Can't handle CardDAO.addCard request");
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public void setStatusByID(int id, int status) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SET_STATUS_BY_ID_SQL);
            ps.setInt(SetStatusByIDIndex.status, status);
            ps.setInt(SetStatusByIDIndex.id, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException("Can't handle setStatusByID request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    private static class ParamColumn {
        private static final String id = "cards.id";
        private static final String account = "account";
        private static final String number = "number";
        private static final String ownerName = "owner_name";
        private static final String expDate = "exp_date";
        private static final String cvv = "cvv";
        private static final String statusID = "cards.status";
        private static final String statusName = "cardstatus.name";
    }

    private static class AddCardIndex {
        public static final int account = 1;
        private static final int number = 2;
        public static final int ownerName = 3;
        public static final int expDate = 4;
        public static final int cvv = 5;
        public static final int status = 6;
    }

    private static class GetCardByIDIndex {
        private static final int id = 1;
        private static final int status = 2;
    }

    private static class SetStatusByIDIndex {
        private static final int status = 1;
        private static final int id = 2;
    }

}
