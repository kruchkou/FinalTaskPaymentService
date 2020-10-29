package dao;

import dao.entity.*;
import dao.impl.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CardDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String INSERT_CARD_SQL = "INSERT INTO Cards(account,number,owner_name,exp_date,cvv,status) VALUES (?,?,?,?,?,?)";
    private static final String GET_CARD_BY_ACCOUNT_ID_SQL = "SELECT cards.id, account, number, owner_name, exp_date, cvv, status, cardstatus.name FROM Cards cards JOIN CardStatuses cardstatus ON cards.status = cardstatus.id WHERE account = ?";
    private static final String SET_STATUS_BY_ID_SQL = "UPDATE Cards SET status = ? where id = ?";

    public CardDAO() {
    }

    public List<Card> getCardListByAccountID(int accountID) throws DAOException {
        List<Card> cardList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_CARD_BY_ACCOUNT_ID_SQL);

            ps.setInt(GetCardByAccountIDIndex.account, accountID);
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
            throw new DAOException("Cant handle CardDAO.getCardListByAccountID request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return cardList;
    }

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
        private static final String statusID = "status";
        private static final String statusName = "cardstatus.status";
    }

    private static class AddCardIndex {
        public static final int account = 1;
        private static final int number = 2;
        public static final int ownerName = 3;
        public static final int expDate = 4;
        public static final int cvv = 5;
        public static final int status = 6;
    }

    private static class GetCardByAccountIDIndex {
        private static final int account = 1;
    }

    private static class SetStatusByIDIndex {
        private static final int status = 1;
        private static final int id = 2;
    }

}
