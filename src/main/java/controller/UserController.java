package controller;

import dao.DAOException;
import dao.UserDAO;
import entity.LoginUser;
import entity.SignUpUser;
import entity.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserController {

    private static UserController instance;
    private UserDAO userDAO = new UserDAO();

    private UserController() {

    }

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    public User signIn(LoginUser loginUser) throws DAOException {
        return userDAO.signIn(loginUser);
    }

    public void signUp(SignUpUser signUpUser) throws DAOException {
        userDAO.signUp(signUpUser);
    }

}
