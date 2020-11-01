package dao.impl;

import dao.exception.DAOException;
import dao.UserDAO;
import dao.connection.impl.ConnectionPool;
import dao.entity.LoginUser;
import dao.entity.SignUpUser;
import dao.entity.User;
import util.StringHasher;

import java.sql.*;
import java.util.HashMap;

public class UserDAOImpl implements UserDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final StringHasher stringHasher = StringHasher.getInstance();

    private static final String SIGNUP_SQL = "INSERT INTO Users(login,password,role,name,surname,patronymic,birthdate,phone_number) VALUES (?,?,?,?,?,?,?,?)";
    private static final String GET_USER_BY_LOGIN_SQL = "SELECT users.id,login,password,role,usersroles.name,users.name,surname,patronymic,birthdate,phone_number,image_src FROM Users users JOIN UsersRoles usersroles ON users.role = usersroles.id WHERE (login = ?)";
    private static final String UPDATE_USER_BY_ID_SQL = "UPDATE Users SET login = ?, name = ?, surname = ?, patronymic = ?, birthdate = ?, phone_number = ? WHERE id = ?";
    private static final String SET_IMAGE_BY_ID_SQL = "UPDATE Users SET image_src = ? WHERE id = ?";
    private static final String SET_PASSWORD_BY_ID_SQL = "UPDATE Users SET password = ? WHERE id = ?";
    private static final String SET_ROLE_BY_ID_SQL = "UPDATE Users SET type = ? WHERE id = ?";

    public UserDAOImpl() {
    }

    @Override
    public User signIn(LoginUser loginUser) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_USER_BY_LOGIN_SQL);

            ps.setString(SignUpIndex.login, loginUser.getLogin());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString(ParamColumn.password);

                if (!stringHasher.checkHash(loginUser.getPassword(), hashedPassword)) {
                    return null;
                }

                user = new User();
                HashMap<Integer, String> role = new HashMap<>();
                role.put((rs.getInt(ParamColumn.roleID)), rs.getString(ParamColumn.roleName));

                user.setId(rs.getInt(ParamColumn.id));
                user.setLogin(rs.getString(ParamColumn.login));
                user.setRole(role);
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
    public void setRoleByID(int role, int id) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SET_ROLE_BY_ID_SQL);
            ps.setInt(SetRoleIndex.role,role);
            ps.setInt(SetRoleIndex.id, id);

            ps.execute();
        } catch (SQLException e) {
            throw new DAOException("Can't handle UserDAO.setRoleByID request", e);
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
    public void signUp(SignUpUser signUpUser) throws DAOException {
        final int USERROLE_USER = 1;
        final int DUBLICATE_LOGIN_ERROR_CODE = 1062;

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SIGNUP_SQL);
            ps.setString(SignUpIndex.login, signUpUser.getLogin());
            ps.setString(SignUpIndex.password, stringHasher.getHash(signUpUser.getPassword()));
            ps.setInt(SignUpIndex.role, USERROLE_USER);
            ps.setString(SignUpIndex.name, signUpUser.getName());
            ps.setString(SignUpIndex.surname, signUpUser.getSurname());
            ps.setString(SignUpIndex.patronymic, signUpUser.getPatronymic());
            ps.setDate(SignUpIndex.birthDate, new Date(signUpUser.getBirthDate().getTime()));
            ps.setString(SignUpIndex.phoneNumber, signUpUser.getPhoneNumber());
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
        private static final String roleID = "role";
        private static final String roleName = "usersroles.name";
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
        private static final int role = 3;
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

    private static class SetRoleIndex {
        private static final int role = 1;
        private static final int id = 2;
    }
}
