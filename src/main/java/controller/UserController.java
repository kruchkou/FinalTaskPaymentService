package controller;

import dao.DAOException;
import dao.UserDAO;
import dao.entity.LoginUser;
import dao.entity.SignUpUser;
import dao.entity.User;

public class UserController {

    private static UserController instance = new UserController();
    private UserDAO userDAO = new UserDAO();

    private UserController() {
    }

    public static UserController getInstance() {
        return instance;
    }

    public User signIn(LoginUser loginUser) throws DAOException {
        return userDAO.signIn(loginUser);
    }

    public void signUp(SignUpUser signUpUser) throws DAOException {
        userDAO.signUp(signUpUser);
    }

}
