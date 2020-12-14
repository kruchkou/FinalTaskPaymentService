package by.epamtc.paymentservice.dao.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import by.epamtc.paymentservice.bean.*;
import by.epamtc.paymentservice.dao.DAOProvider;
import by.epamtc.paymentservice.dao.UserDAO;
import by.epamtc.paymentservice.dao.connection.ConnectionPool;
import by.epamtc.paymentservice.dao.connection.ConnectionPoolTest;
import by.epamtc.paymentservice.dao.connection.impl.ConnectionPoolImpl;
import by.epamtc.paymentservice.dao.connection.impl.ConnectionPoolTestImpl;
import by.epamtc.paymentservice.dao.exception.ConnectionPoolInitError;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.util.StringHasher;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDAOImplTest {

    private static final String TEST_LOGIN = "kruchkou";
    private static final String TEST_PASS = "mypass";
    private static final int TEST_ID = 1;

    private static final Logger logger = Logger.getLogger(UserDAOImplTest.class);

    final ConnectionPoolTest connectionPoolTest = ConnectionPoolTestImpl.getInstance();
    private static final StringHasher stringHasher = StringHasher.getInstance();

    private static final String DB_PROPERTY_FILE_URL = "src/main/resources/db.properties";

    private static final String DB_URL_PROPERTY = "db.url";
    private static final String DB_URL = "jdbc:mysql://178.154.224.83:3306/mydb?autoReconnect=true";
    private static final String TEST_DB_URL = "jdbc:mysql://178.154.224.83:3306/ps_test?autoReconnect=true";

    
    private static final String GET_USER_BY_ID_SQL = "SELECT users.id,login,password,status,userstatus.name,users.name,surname,patronymic,birthdate,phone_number,image_src FROM Users users " +
            "JOIN UsersStatuses userstatus ON users.status = userstatus.id " +
            "WHERE (users.id = ?)";

    private static final String GET_USER_BY_LOGIN_SQL = "SELECT users.id,login,password,status,userstatus.name,users.name,surname,patronymic,birthdate,phone_number,image_src FROM Users users " +
            "JOIN UsersStatuses userstatus ON users.status = userstatus.id " +
            "WHERE (login = ?)";

    private static final String MESSAGE_GET_USER_BY_ID_PROBLEM = "Cant handle UserDAO.getUserByID request";
    private static final String MESSAGE_SIGN_IN_PROBLEM = "Can't handle UserDAO.signIn request";


    @BeforeClass
    public static void setDBParamsToTestDB() throws ConnectionPoolInitError, IOException {

        FileInputStream in = new FileInputStream(DB_PROPERTY_FILE_URL);
        Properties props = new Properties();
        props.load(in);
        in.close();

        FileOutputStream out = new FileOutputStream(DB_PROPERTY_FILE_URL);
        props.setProperty(DB_URL_PROPERTY, TEST_DB_URL);
        props.store(out, null);
        out.close();
    }
    
    @Test
    public void getUser() throws DAOException {
        User userSample = null;
        Connection connection = null;
        PreparedStatement ps = null;
        
        try {
            connection = connectionPoolTest.getConnection();
            ps = connection.prepareStatement(GET_USER_BY_ID_SQL);
            ps.setInt(GetUserIndex.ID, TEST_ID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userSample = new User();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.STATUS_ID));
                status.setName(rs.getString(ParamColumn.STATUS_NAME));

                userSample.setId(rs.getInt(ParamColumn.ID));
                userSample.setLogin(rs.getString(ParamColumn.LOGIN));
                userSample.setStatus(status);
                userSample.setName(rs.getString(ParamColumn.NAME));
                userSample.setSurname(rs.getString(ParamColumn.SURNAME));
                userSample.setPatronymic(rs.getString(ParamColumn.PATRONYMIC));
                userSample.setBirthDate(rs.getDate(ParamColumn.BIRTHDATE));
                userSample.setPhoneNumber(rs.getString(ParamColumn.PHONE_NUMBER));
                userSample.setImageSrc(rs.getString(ParamColumn.IMAGE_SRC));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_GET_USER_BY_ID_PROBLEM, e);
        } finally {
            connectionPoolTest.closeConnection(connection, ps);
        }

        UserDAO userDAO = DAOProvider.getInstance().getUserDAO();

        User userTest = userDAO.getUser(TEST_ID);

        org.junit.Assert.assertEquals(userSample, userTest);
    }

    @Test
    public void signIn() throws DAOException, ConnectionPoolInitError {
        SignInData signInData = new SignUpData();

        signInData.setLogin(TEST_LOGIN);
        signInData.setPassword(TEST_PASS);

        User userSample = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPoolTest.getConnection();
            ps = connection.prepareStatement(GET_USER_BY_LOGIN_SQL);

            ps.setString(SignUpIndex.LOGIN, signInData.getLogin());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString(ParamColumn.PASSWORD);

                if (stringHasher.checkHash(signInData.getPassword(), hashedPassword)) {

                    userSample = new User();
                    Status status = new Status();
                    status.setId(rs.getInt(ParamColumn.STATUS_ID));
                    status.setName(rs.getString(ParamColumn.STATUS_NAME));

                    userSample.setId(rs.getInt(ParamColumn.ID));
                    userSample.setLogin(rs.getString(ParamColumn.LOGIN));
                    userSample.setStatus(status);
                    userSample.setName(rs.getString(ParamColumn.NAME));
                    userSample.setSurname(rs.getString(ParamColumn.SURNAME));
                    userSample.setPatronymic(rs.getString(ParamColumn.PATRONYMIC));
                    userSample.setBirthDate(rs.getDate(ParamColumn.BIRTHDATE));
                    userSample.setPhoneNumber(rs.getString(ParamColumn.PHONE_NUMBER));
                    userSample.setImageSrc(rs.getString(ParamColumn.IMAGE_SRC));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SIGN_IN_PROBLEM, e);
        } finally {
            connectionPoolTest.closeConnection(connection, ps);
        }


        UserDAO userDAO = DAOProvider.getInstance().getUserDAO();

        User userTest = userDAO.signIn(signInData);

        org.junit.Assert.assertEquals(userSample, userTest);

    }

    @AfterClass
    public static void setDBParamsToNormalDB() throws IOException {
        FileInputStream in = new FileInputStream(DB_PROPERTY_FILE_URL);
        Properties props = new Properties();
        props.load(in);
        in.close();

        FileOutputStream out = new FileOutputStream(DB_PROPERTY_FILE_URL);
        props.setProperty(DB_URL_PROPERTY, DB_URL);
        props.store(out, null);
        out.close();
    }

    private static class ParamColumn {
        private static final String ID = "users.id";
        private static final String LOGIN = "login";
        private static final String PASSWORD = "password";
        private static final String STATUS_ID = "status";
        private static final String STATUS_NAME = "userstatus.name";
        private static final String NAME = "users.name";
        private static final String SURNAME = "surname";
        private static final String PATRONYMIC = "patronymic";
        private static final String BIRTHDATE = "birthdate";
        private static final String PHONE_NUMBER = "phone_number";
        private static final String IMAGE_SRC = "image_src";
    }

    private static class SignUpIndex {
        private static final int LOGIN = 1;
        private static final int PASSWORD = 2;
        private static final int STATUS = 3;
        private static final int NAME = 4;
        private static final int SURNAME = 5;
        private static final int PATRONYMIC = 6;
        private static final int BIRTH_DATE = 7;
        private static final int PHONE_NUMBER = 8;
    }

    private static class GetUserIndex {
        private static final int ID = 1;
    }
}
