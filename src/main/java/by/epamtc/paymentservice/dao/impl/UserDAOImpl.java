package by.epamtc.paymentservice.dao.impl;

import by.epamtc.paymentservice.bean.SignInData;
import by.epamtc.paymentservice.bean.SignUpData;
import by.epamtc.paymentservice.bean.Status;
import by.epamtc.paymentservice.bean.User;
import by.epamtc.paymentservice.dao.ResultCode;
import by.epamtc.paymentservice.dao.UserDAO;
import by.epamtc.paymentservice.dao.connection.ConnectionPool;
import by.epamtc.paymentservice.dao.connection.impl.ConnectionPoolImpl;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.util.StringHasher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link UserDAO}. Provides methods to interact with Users data from database.
 * Methods connect to database using {@link Connection} from {@link ConnectionPoolImpl} and manipulate with data(save, edit, etc.).
 *
 */
public class UserDAOImpl implements UserDAO {

    /** A single instance of the class (pattern Singleton) */
    private static final UserDAOImpl instance = new UserDAOImpl();

    /** An object of {@link ConnectionPoolImpl} */
    private static final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    /** An object of {@link StringHasher} */
    private static final StringHasher stringHasher = StringHasher.getInstance();

    /** Query for database to sign up new user */
    private static final String SIGNUP_SQL = "INSERT INTO Users(login,password,status,name,surname,patronymic,birthdate,phone_number) VALUES (?,?,?,?,?,?,?,?)";

    /** Query for database to update user data by user ID */
    private static final String UPDATE_USER_BY_ID_SQL = "UPDATE Users SET login = ?, name = ?, surname = ?, patronymic = ?, birthdate = ?, phone_number = ? WHERE id = ?";

    /** Query for database to set an image src by user ID */
    private static final String SET_IMAGE_BY_ID_SQL = "UPDATE Users SET image_src = ? WHERE id = ?";

    /** Query for database to set an password by user ID */
    private static final String SET_PASSWORD_BY_ID_SQL = "UPDATE Users SET password = ? WHERE id = ?";

    /** Query for database to set status by user ID */
    private static final String SET_STATUS_BY_ID_SQL = "UPDATE Users SET status = ? WHERE id = ?";

    /** Query for database to get user by login */
    private static final String GET_USER_BY_LOGIN_SQL = "SELECT users.id,login,password,status,userstatus.name,users.name,surname,patronymic,birthdate,phone_number,image_src FROM Users users " +
            "JOIN UsersStatuses userstatus ON users.status = userstatus.id " +
            "WHERE (login = ?)";

    /** Query for database to get user data by user ID */
    private static final String GET_USER_BY_ID_SQL = "SELECT users.id,login,password,status,userstatus.name,users.name,surname,patronymic,birthdate,phone_number,image_src FROM Users users " +
            "JOIN UsersStatuses userstatus ON users.status = userstatus.id " +
            "WHERE (users.id = ?)";

    /** Query for database to get user list like fio */
    private static final String GET_USER_LIST_SQL = "SELECT users.id,login,password,status,userstatus.name,users.name,surname,patronymic,birthdate,phone_number,image_src FROM Users users " +
            "JOIN UsersStatuses userstatus ON users.status = userstatus.id " +
            "WHERE (users.name REGEXP ? OR surname REGEXP ? OR patronymic REGEXP ?)";

    /** Message, that is putted in Exception if there are sign ip problem */
    private static final String MESSAGE_SIGN_IN_PROBLEM = "Can't handle UserDAO.signIn request";

    /** Message, that is putted in Exception if there are sign up problem */
    private static final String MESSAGE_SIGN_UP_PROBLEM = "Can't handle UserDAO.signUp request";

    /** Message, that is putted in Exception if there are update user problem */
    private static final String MESSAGE_UPDATE_USER_PROBLEM = "Can't handle UserDAO.updateUser request";

    /** Message, that is putted in Exception if there are set image src problem */
    private static final String MESSAGE_SET_IMAGE_SRC_PROBLEM = "Can't handle UserDAO.setImageByID request";

    /** Message, that is putted in Exception if there are set password problem */
    private static final String MESSAGE_SET_PASSWORD_PROBLEM = "Can't handle UserDAO.setPasswordByID request";

    /** Message, that is putted in Exception if there are set status problem */
    private static final String MESSAGE_SET_STATUS_PROBLEM = "Can't handle UserDAO.setStatusByID request";

    /** Message, that is putted in Exception if there are is login available problem */
    private static final String MESSAGE_IS_LOGIN_AVAILABLE_PROBLEM = "Can't handle UserDAO.isLoginAvailable request";

    /** Message, that is putted in Exception if there are is get user list problem */
    private static final String MESSAGE_GET_USER_LIST_PROBLEM = "Cant handle UserDAO.getUserList request";

    /** Message, that is putted in Exception if there are is get user list problem */
    private static final String MESSAGE_GET_USER_BY_ID_PROBLEM = "Cant handle UserDAO.getUserByID request";

    /**
     * Returns the instance of the class
     * @return Object of {@link UserDAOImpl}
     */
    public static UserDAOImpl getInstance() {
        return instance;
    }

    /** Private constructor without parameters */
    private UserDAOImpl() {

    }

    /**
     * Connects to database, checks the credentials and returns an User object if success.
     *
     * @param signInData    is Object of {@link SignInData}, which contains information about user's login and password.
     * @return {@link User} if user's data exists and password matches, null if user's login and password are not correct.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public User signIn(SignInData signInData) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_USER_BY_LOGIN_SQL);

            ps.setString(SignUpIndex.LOGIN, signInData.getLogin());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString(ParamColumn.PASSWORD);

                if (!stringHasher.checkHash(signInData.getPassword(), hashedPassword)) {
                    return null;
                }

                user = new User();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.STATUS_ID));
                status.setName(rs.getString(ParamColumn.STATUS_NAME));

                user.setId(rs.getInt(ParamColumn.ID));
                user.setLogin(rs.getString(ParamColumn.LOGIN));
                user.setStatus(status);
                user.setName(rs.getString(ParamColumn.NAME));
                user.setSurname(rs.getString(ParamColumn.SURNAME));
                user.setPatronymic(rs.getString(ParamColumn.PATRONYMIC));
                user.setBirthDate(rs.getDate(ParamColumn.BIRTHDATE));
                user.setPhoneNumber(rs.getString(ParamColumn.PHONE_NUMBER));
                user.setImageSrc(rs.getString(ParamColumn.IMAGE_SRC));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SIGN_IN_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return user;
    }


    /**
     * Connects to database, update user's data and returns {@link ResultCode} object
     *
     * @param user  is Object of {@link User}, which contains full information about user.
     * @return {@link ResultCode} enum, that shows the result of the method execution.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public ResultCode updateUser(User user) throws DAOException {
        final int DUPLICATE_LOGIN_ERROR_CODE = 1062;

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(UPDATE_USER_BY_ID_SQL);
            ps.setString(UpdateImdex.LOGIN, user.getLogin());
            ps.setString(UpdateImdex.NAME, user.getName());
            ps.setString(UpdateImdex.SURNAME, user.getSurname());
            ps.setString(UpdateImdex.PATRONYMIC, user.getPatronymic());
            ps.setDate(UpdateImdex.BIRTH_DATE, new Date(user.getBirthDate().getTime()));
            ps.setString(UpdateImdex.PHONE_NUMBER, user.getPhoneNumber());
            ps.setInt(UpdateImdex.ID, user.getId());
            ps.execute();

            return ResultCode.RESULT_SUCCESS;

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_LOGIN_ERROR_CODE) {
                return ResultCode.RESULT_ERROR_DUPLICATE_LOGIN;
            } else {
                throw new DAOException(MESSAGE_UPDATE_USER_PROBLEM, e);
            }
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }


    /**
     * Connects to database and set image src to user by ID.
     *
     * @param imageSrc  is text that contains image src.
     * @param id    is User's ID value.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public void setImageByID(String imageSrc, int id) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SET_IMAGE_BY_ID_SQL);
            ps.setString(SetImageIndex.IMAGE_SRC, imageSrc);
            ps.setInt(SetImageIndex.ID, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SET_IMAGE_SRC_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }


    /**
     * Connects to database and set password to user by ID.
     *
     * @param password  is text that contains user's password.
     * @param id is User's ID value.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public void setPasswordByID(String password, int id) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SET_PASSWORD_BY_ID_SQL);
            ps.setString(SetPasswordIndex.PASSWORD, stringHasher.getHash(password));
            ps.setInt(SetPasswordIndex.ID, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SET_PASSWORD_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    /**
     * Connects to database and set user status to user by ID.
     *
     * @param status    is user status ID.
     * @param id    is User's ID value.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public void setStatus(int status, int id) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SET_STATUS_BY_ID_SQL);
            ps.setInt(SetStatusIndex.STATUS, status);
            ps.setInt(SetStatusIndex.ID, id);

            ps.execute();
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SET_STATUS_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    /**
     * Connects to database and return if the login is free.
     *
     * @param login     is text that contains login.
     * @return true if login is already taken, false if not.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public boolean isLoginAvailable(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_USER_BY_LOGIN_SQL);
            ps.setString(SignUpIndex.LOGIN, login);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return false;
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_IS_LOGIN_AVAILABLE_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return true;
    }

    /**
     * Connects to database and returns List of {@link User} objects that has FIO like parameter.
     *
     * @param fio   is text, contains user's name, surname or patronymic
     * @return List of {@link User} with all matching users.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public List<User> getUserList(String fio) throws DAOException {
        final String SPACE_REGEX_SYMBOL = " ";
        final String DELIMITER_SYMBOL = "|";
        fio = fio.replace(SPACE_REGEX_SYMBOL, DELIMITER_SYMBOL);

        final List<User> userList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_USER_LIST_SQL);

            ps.setString(GetUserListIndex.NAME, fio);
            ps.setString(GetUserListIndex.SURNAME, fio);
            ps.setString(GetUserListIndex.PATRONYMIC, fio);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.STATUS_ID));
                status.setName(rs.getString(ParamColumn.STATUS_NAME));

                user.setId(rs.getInt(ParamColumn.ID));
                user.setLogin(rs.getString(ParamColumn.LOGIN));
                user.setStatus(status);
                user.setName(rs.getString(ParamColumn.NAME));
                user.setSurname(rs.getString(ParamColumn.SURNAME));
                user.setPatronymic(rs.getString(ParamColumn.PATRONYMIC));
                user.setBirthDate(rs.getDate(ParamColumn.BIRTHDATE));
                user.setPhoneNumber(rs.getString(ParamColumn.PHONE_NUMBER));
                user.setImageSrc(rs.getString(ParamColumn.IMAGE_SRC));

                userList.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_GET_USER_LIST_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return userList;
    }

    /**
     * Connects to database and returns {@link User} object, that contents all info about user by ID.
     *
     * @param id    is User's ID value.
     * @return {@link User} if user's data found, null if not.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public User getUser(int id) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_USER_BY_ID_SQL);
            ps.setInt(GetUserIndex.ID, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.STATUS_ID));
                status.setName(rs.getString(ParamColumn.STATUS_NAME));

                user.setId(rs.getInt(ParamColumn.ID));
                user.setLogin(rs.getString(ParamColumn.LOGIN));
                user.setStatus(status);
                user.setName(rs.getString(ParamColumn.NAME));
                user.setSurname(rs.getString(ParamColumn.SURNAME));
                user.setPatronymic(rs.getString(ParamColumn.PATRONYMIC));
                user.setBirthDate(rs.getDate(ParamColumn.BIRTHDATE));
                user.setPhoneNumber(rs.getString(ParamColumn.PHONE_NUMBER));
                user.setImageSrc(rs.getString(ParamColumn.IMAGE_SRC));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_GET_USER_BY_ID_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return user;
    }

    /**
     * Connects to database, creates new user by data provided and returns {@link ResultCode} object as result.
     *
     * @param signUpData    Object of {@link SignUpData}, which contains user information.
     * @return {@link ResultCode} enum, that shows the result of the method execution.
     * @throws DAOException when problems with database connection occurs.
     */
    @Override
    public ResultCode signUp(SignUpData signUpData) throws DAOException {
        final int STATUS_USER = 1;
        final int DUBLICATE_LOGIN_ERROR_CODE = 1062;

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SIGNUP_SQL);
            ps.setString(SignUpIndex.LOGIN, signUpData.getLogin());
            ps.setString(SignUpIndex.PASSWORD, stringHasher.getHash(signUpData.getPassword()));
            ps.setInt(SignUpIndex.STATUS, STATUS_USER);
            ps.setString(SignUpIndex.NAME, signUpData.getName());
            ps.setString(SignUpIndex.SURNAME, signUpData.getSurname());
            ps.setString(SignUpIndex.PATRONYMIC, signUpData.getPatronymic());
            ps.setDate(SignUpIndex.BIRTH_DATE, new Date(signUpData.getBirthDate().getTime()));
            ps.setString(SignUpIndex.PHONE_NUMBER, signUpData.getPhoneNumber());
            ps.execute();

            return ResultCode.RESULT_SUCCESS;

        } catch (SQLException e) {
            if (e.getErrorCode() == DUBLICATE_LOGIN_ERROR_CODE) {
                return ResultCode.RESULT_ERROR_DUPLICATE_LOGIN;
            } else {
                throw new DAOException(MESSAGE_SIGN_UP_PROBLEM, e);
            }
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }


    /**
     * Static class that contains parameters of Users table.
     */
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


    /**
     * Static class that contains parameter indexes for sign up
     */
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

    /**
     * Static class that contains parameter indexes for updating user data by user ID
     */
    private static class UpdateImdex {
        private static final int LOGIN = 1;
        private static final int NAME = 2;
        private static final int SURNAME = 3;
        private static final int PATRONYMIC = 4;
        private static final int BIRTH_DATE = 5;
        private static final int PHONE_NUMBER = 6;
        private static final int ID = 7;
    }

    /**
     * Static class that contains parameter indexes for setting image src by user ID
     */
    private static class SetImageIndex {
        private static final int IMAGE_SRC = 1;
        private static final int ID = 2;
    }

    /**
     * Static class that contains parameter indexes for setting password by user ID
     */
    private static class SetPasswordIndex {
        private static final int PASSWORD = 1;
        private static final int ID = 2;
    }

    /**
     * Static class that contains parameter indexes for getting user list like FIO
     */
    private static class GetUserListIndex {
        private static final int NAME = 1;
        private static final int SURNAME = 2;
        private static final int PATRONYMIC = 3;
    }

    /**
     * Static class that contains parameter indexes for setting status by user ID
     */
    private static class SetStatusIndex {
        private static final int STATUS = 1;
        private static final int ID = 2;
    }

    /**
     * Static class that contains parameter indexes for getting user data by user ID
     */
    private static class GetUserIndex {
        private static final int ID = 1;
    }
}
