package dao;

import dao.impl.ConnectionPool;
import entity.LoginUser;
import entity.SignUpUser;
import entity.User;

import java.sql.*;
import java.util.HashMap;

public class UserDAO {

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final String SIGNUP_SQL = "INSERT INTO Users(login,password,role,name,surname,patronymic,birthdate,phone_number) VALUES (?,?,1,?,?,?,?,?)";
    private final String SIGNIN_SQL = "SELECT id FROM Users WHERE (login = ? AND password = ?)";
    private final String GET_USER_BY_LOGIN_SQL = "SELECT login,role,usersroles.name,users.name,surname,patronymic,birthdate,phone_number FROM Users users JOIN UsersRoles usersroles ON users.role = usersroles.id WHERE (login = ? AND password = ?)";

    public User signIn(LoginUser loginUser) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(GET_USER_BY_LOGIN_SQL);

            ps.setString(1, loginUser.getLogin());
            ps.setString(2, loginUser.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                HashMap<Integer, String> role = new HashMap<>();
                role.put((rs.getInt(2)), rs.getString(3));

                user.setLogin(rs.getString(1));
                user.setRole(role);
                user.setName(rs.getString(4));
                user.setSurname(rs.getString(5));
                user.setPatronymic(rs.getString(6));
                user.setBirthDate(rs.getDate(7));
                user.setPhoneNumber(rs.getString(8));
            }
        } catch (SQLException e) {
            throw new DAOException("Cant handle getUserByLogin request", e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return user;
    }

    public void signUp(SignUpUser signUpUser) throws DAOException {
        String string = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.getConnection();
            ps = connection.prepareStatement(SIGNUP_SQL);

            ps.setString(1, signUpUser.getLogin());
            ps.setString(2, signUpUser.getPassword());
            ps.setString(3, signUpUser.getName());
            ps.setString(4, signUpUser.getSurname());
            ps.setString(5, signUpUser.getPatronymic());
            ps.setDate(6, new Date(signUpUser.getBirthDate().getTime()));
            ps.setString(7, signUpUser.getPhoneNumber());
            ps.execute();

        } catch (SQLException e) {
            throw new DAOException("Cant handle SignUp request", e); //В базе логин - unique, обработчик ексепшенов отловит и уведомит пользователя
        } finally {
            connectionPool.closeConnection(connection, ps);
        }


    }

}
