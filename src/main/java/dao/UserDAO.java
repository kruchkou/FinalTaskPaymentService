package dao;

import dao.impl.ConnectionPool;
import dao.entity.LoginUser;
import dao.entity.SignUpUser;
import dao.entity.User;
import util.StringHasher;

import java.sql.*;
import java.util.HashMap;

public class UserDAO {

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final StringHasher stringHasher = StringHasher.getInstance();

    private final String SIGNUP_SQL = "INSERT INTO Users(login,password,role,name,surname,patronymic,birthdate,phone_number) VALUES (?,?,1,?,?,?,?,?)";
    private final String GET_USER_BY_LOGIN_SQL = "SELECT login,password,role,usersroles.name,users.name,surname,patronymic,birthdate,phone_number FROM Users users JOIN UsersRoles usersroles ON users.role = usersroles.id WHERE (login = ?)";

    public UserDAO() {
    }

    public User signIn(LoginUser loginUser) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_USER_BY_LOGIN_SQL);

            ps.setString(UserParamIndex.login, loginUser.getLogin());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString(UserParamColumn.password);

                if (!stringHasher.checkHash(loginUser.getPassword(), hashedPassword)) {
                    return null;
                }

                user = new User();
                HashMap<Integer, String> role = new HashMap<>();
                role.put((rs.getInt(UserParamColumn.role)), rs.getString(UserParamColumn.roleName));

                user.setLogin(rs.getString(UserParamColumn.login));
                user.setRole(role);
                user.setName(rs.getString(UserParamColumn.name));
                user.setSurname(rs.getString(UserParamColumn.surname));
                user.setPatronymic(rs.getString(UserParamColumn.patronymic));
                user.setBirthDate(rs.getDate(UserParamColumn.birthDate));
                user.setPhoneNumber(rs.getString(UserParamColumn.phoneNumber));
            }
        } catch (SQLException e) {
            throw new DAOException("Cant handle getUserByLogin request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return user;
    }

    public void signUp(SignUpUser signUpUser) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SIGNUP_SQL);

            ps.setString(UserParamIndex.login, signUpUser.getLogin());
            ps.setString(UserParamIndex.password, stringHasher.getHash(signUpUser.getPassword()));
            ps.setString(UserParamIndex.name, signUpUser.getName());
            ps.setString(UserParamIndex.surname, signUpUser.getSurname());
            ps.setString(UserParamIndex.patronymic, signUpUser.getPatronymic());
            ps.setDate(UserParamIndex.birthDate, new Date(signUpUser.getBirthDate().getTime()));
            ps.setString(UserParamIndex.phoneNumber, signUpUser.getPhoneNumber());
            ps.execute();

        } catch (SQLException e) {
            throw new DAOException("Cant handle SignUp request", e); //В базе логин - unique, обработчик ексепшенов отловит и уведомит пользователя
        } finally {
            connectionPool.closeConnection(connection, ps);
        }

    }

    private static class UserParamColumn {
        private static final String login = "login";
        private static final String password = "password";
        private static final String role = "role";
        private static final String roleName = "usersroles.name";
        private static final String name = "users.name";
        private static final String surname = "surname";
        private static final String patronymic = "patronymic";
        private static final String birthDate = "birthdate";
        private static final String phoneNumber = "phone_number";
    }

    private static class UserParamIndex {
        private static final int login = 1;
        private static final int password = 2;
        private static final int name = 3;
        private static final int surname = 4;
        private static final int patronymic = 5;
        private static final int birthDate = 6;
        private static final int phoneNumber = 7;
    }
}
