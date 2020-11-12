package dao.impl;

import dao.entity.Status;
import dao.exception.DAOException;
import dao.UserDAO;
import dao.connection.impl.ConnectionPool;
import dao.entity.LoginData;
import dao.entity.SignUpData;
import dao.entity.User;
import util.StringHasher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final StringHasher stringHasher = StringHasher.getInstance();

    private static final String SIGNUP_SQL = "INSERT INTO Users(login,password,status,name,surname,patronymic,birthdate,phone_number) VALUES (?,?,?,?,?,?,?,?)";
    private static final String UPDATE_USER_BY_ID_SQL = "UPDATE Users SET login = ?, name = ?, surname = ?, patronymic = ?, birthdate = ?, phone_number = ? WHERE id = ?";
    private static final String SET_IMAGE_BY_ID_SQL = "UPDATE Users SET image_src = ? WHERE id = ?";
    private static final String SET_PASSWORD_BY_ID_SQL = "UPDATE Users SET password = ? WHERE id = ?";
    private static final String SET_STATUS_BY_ID_SQL = "UPDATE Users SET status = ? WHERE id = ?";
    private static final String GET_USER_BY_LOGIN_SQL = "SELECT users.id,login,password,status,userstatus.name,users.name,surname,patronymic,birthdate,phone_number,image_src FROM Users users " +
            "JOIN UsersStatuses userstatus ON users.status = userstatus.id " +
            "WHERE (login = ?)";
    private static final String GET_USER_BY_ID_SQL = "SELECT users.id,login,password,status,userstatus.name,users.name,surname,patronymic,birthdate,phone_number,image_src FROM Users users " +
            "JOIN UsersStatuses userstatus ON users.status = userstatus.id " +
            "WHERE (users.id = ?)";
    private static final String GET_USER_LIST_SQL = "SELECT users.id,login,password,status,userstatus.name,users.name,surname,patronymic,birthdate,phone_number,image_src FROM Users users " +
            "JOIN UsersStatuses userstatus ON users.status = userstatus.id " +
            "WHERE (users.name REGEXP ? OR surname REGEXP ? OR patronymic REGEXP ?)";

    public UserDAOImpl() {
    }

    @Override
    public User signIn(LoginData loginData) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_USER_BY_LOGIN_SQL);

            ps.setString(SignUpIndex.login, loginData.getLogin());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString(ParamColumn.password);

                if (!stringHasher.checkHash(loginData.getPassword(), hashedPassword)) {
                    return null;
                }

                user = new User();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.statusID));
                status.setName(rs.getString(ParamColumn.statusName));

                user.setId(rs.getInt(ParamColumn.id));
                user.setLogin(rs.getString(ParamColumn.login));
                user.setStatus(status);
                user.setName(rs.getString(ParamColumn.name));
                user.setSurname(rs.getString(ParamColumn.surname));
                user.setPatronymic(rs.getString(ParamColumn.patronymic));
                user.setBirthDate(rs.getDate(ParamColumn.birthDate));
                user.setPhoneNumber(rs.getString(ParamColumn.phoneNumber));
                user.setImageSrc(rs.getString(ParamColumn.imageSrc));
            }
        } catch (SQLException e) {
            throw new DAOException("Cant handle UserDAO.getUserByLogin request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return user;
    }

    @Override
    public User updateUser(User user) throws DAOException {
        final int DUBLICATE_LOGIN_ERROR_CODE = 1062;

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(UPDATE_USER_BY_ID_SQL);
            ps.setString(UpdateImdex.login, user.getLogin());
            ps.setString(UpdateImdex.name, user.getName());
            ps.setString(UpdateImdex.surname, user.getSurname());
            ps.setString(UpdateImdex.patronymic, user.getPatronymic());
            ps.setDate(UpdateImdex.birthDate, new Date(user.getBirthDate().getTime()));
            ps.setString(UpdateImdex.phoneNumber, user.getPhoneNumber());
            ps.setInt(UpdateImdex.id, user.getId());
            ps.execute();

        } catch (SQLException e) {
            DAOException daoException = new DAOException("Can't handle UserDAO.updateUser request", e);
            if (e.getErrorCode() == DUBLICATE_LOGIN_ERROR_CODE) {
                daoException.setErrorCode(DUBLICATE_LOGIN_ERROR_CODE);
            }
            throw daoException;
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return user;
    }

    @Override
    public void setImageByID(String imageSrc, int id) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SET_IMAGE_BY_ID_SQL);
            ps.setString(SetImageIndex.imageSrc, imageSrc);
            ps.setInt(SetImageIndex.id, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException("Can't handle UserDAO.setImageByID request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public void setPasswordByID(String password, int id) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SET_PASSWORD_BY_ID_SQL);
            ps.setString(SetPasswordIndex.password, stringHasher.getHash(password));
            ps.setInt(SetPasswordIndex.id, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException("Can't handle UserDAO.setPasswordByID request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public void setStatus(int status, int id) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SET_STATUS_BY_ID_SQL);
            ps.setInt(SetStatusIndex.status,status);
            ps.setInt(SetStatusIndex.id, id);

            ps.execute();
        } catch (SQLException e) {
            throw new DAOException("Can't handle UserDAO.setStatusByID request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public boolean isLoginAvailable(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_USER_BY_LOGIN_SQL);
            ps.setString(SignUpIndex.login, login);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return false;
            }

        } catch (SQLException e) {
            throw new DAOException("Can't handle UserDAO.isLoginAvailable request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return true;
    }

    @Override
    public List<User> getUserList(String fio) throws DAOException {
        final String SPACE_REGEX_SYMBOL = " ";
        final String DELIMITER_SYMBOL = "|";
        fio = fio.replace(SPACE_REGEX_SYMBOL,DELIMITER_SYMBOL);

        final List<User> userList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_USER_LIST_SQL);

            ps.setString(GetUserListIndex.name,fio);
            ps.setString(GetUserListIndex.surname,fio);
            ps.setString(GetUserListIndex.patronymic,fio);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.statusID));
                status.setName(rs.getString(ParamColumn.statusName));

                user.setId(rs.getInt(ParamColumn.id));
                user.setLogin(rs.getString(ParamColumn.login));
                user.setStatus(status);
                user.setName(rs.getString(ParamColumn.name));
                user.setSurname(rs.getString(ParamColumn.surname));
                user.setPatronymic(rs.getString(ParamColumn.patronymic));
                user.setBirthDate(rs.getDate(ParamColumn.birthDate));
                user.setPhoneNumber(rs.getString(ParamColumn.phoneNumber));
                user.setImageSrc(rs.getString(ParamColumn.imageSrc));

                userList.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException("Cant handle UserDAO.getUserList request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return userList;
    }

    @Override
    public User getUser(int id) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_USER_BY_ID_SQL);
            ps.setInt(GetUserIndex.id, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                Status status = new Status();
                status.setId(rs.getInt(ParamColumn.statusID));
                status.setName(rs.getString(ParamColumn.statusName));

                user.setId(rs.getInt(ParamColumn.id));
                user.setLogin(rs.getString(ParamColumn.login));
                user.setStatus(status);
                user.setName(rs.getString(ParamColumn.name));
                user.setSurname(rs.getString(ParamColumn.surname));
                user.setPatronymic(rs.getString(ParamColumn.patronymic));
                user.setBirthDate(rs.getDate(ParamColumn.birthDate));
                user.setPhoneNumber(rs.getString(ParamColumn.phoneNumber));
                user.setImageSrc(rs.getString(ParamColumn.imageSrc));
            }
        } catch (SQLException e) {
            throw new DAOException("Cant handle UserDAO.getUserByID request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return user;
    }

    @Override
    public void signUp(SignUpData signUpData) throws DAOException {
        final int STATUS_USER = 1;
        final int DUBLICATE_LOGIN_ERROR_CODE = 1062;

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SIGNUP_SQL);
            ps.setString(SignUpIndex.login, signUpData.getLogin());
            ps.setString(SignUpIndex.password, stringHasher.getHash(signUpData.getPassword()));
            ps.setInt(SignUpIndex.status, STATUS_USER);
            ps.setString(SignUpIndex.name, signUpData.getName());
            ps.setString(SignUpIndex.surname, signUpData.getSurname());
            ps.setString(SignUpIndex.patronymic, signUpData.getPatronymic());
            ps.setDate(SignUpIndex.birthDate, new Date(signUpData.getBirthDate().getTime()));
            ps.setString(SignUpIndex.phoneNumber, signUpData.getPhoneNumber());
            ps.execute();

        } catch (SQLException e) {
            DAOException daoException = new DAOException("Can't handle UserDAO.SignUp request", e);
            if (e.getErrorCode() == DUBLICATE_LOGIN_ERROR_CODE) {
                daoException.setErrorCode(DUBLICATE_LOGIN_ERROR_CODE);
            }
            throw daoException;
        } finally {
            connectionPool.closeConnection(connection, ps);
        }

    }

    private static class ParamColumn {
        private static final String id = "users.id";
        private static final String login = "login";
        private static final String password = "password";
        private static final String statusID = "status";
        private static final String statusName = "userstatus.name";
        private static final String name = "users.name";
        private static final String surname = "surname";
        private static final String patronymic = "patronymic";
        private static final String birthDate = "birthdate";
        private static final String phoneNumber = "phone_number";
        private static final String imageSrc = "image_src";
    }

    private static class SignUpIndex {
        private static final int login = 1;
        private static final int password = 2;
        private static final int status = 3;
        private static final int name = 4;
        private static final int surname = 5;
        private static final int patronymic = 6;
        private static final int birthDate = 7;
        private static final int phoneNumber = 8;
    }

    private static class UpdateImdex {
        private static final int login = 1;
        private static final int name = 2;
        private static final int surname = 3;
        private static final int patronymic = 4;
        private static final int birthDate = 5;
        private static final int phoneNumber = 6;
        private static final int id = 7;
    }

    private static class SetImageIndex {
        private static final int imageSrc = 1;
        private static final int id = 2;
    }

    private static class SetPasswordIndex {
        private static final int password = 1;
        private static final int id = 2;
    }


    private static class GetUserListIndex {
        private static final int name = 1;
        private static final int surname = 2;
        private static final int patronymic = 3;
    }

    private static class SetStatusIndex {
        private static final int status = 1;
        private static final int id = 2;
    }

    private static class GetUserIndex {
        private static final int id = 1;
    }
}
