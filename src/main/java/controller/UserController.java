package controller;

import dao.DAOException;
import dao.UserDAO;
import dao.entity.LoginUser;
import dao.entity.SignUpUser;
import dao.entity.User;
import util.validator.SignUpValidator;

public class UserController {

    private static UserController instance = new UserController();
    private UserDAO userDAO = new UserDAO();
    private SignUpValidator signUpValidator = SignUpValidator.getInstance();

    private UserController() {
    }

    public static UserController getInstance() {
        return instance;
    }

    public User signIn(LoginUser loginUser) throws DAOException {
        return userDAO.signIn(loginUser);
    }

    public void signUp(SignUpUser signUpUser) throws DAOException {
        if (!signUpValidator.validate(signUpUser)) {
            throw new DAOException("User data didn't passed validation");
        }
        userDAO.signUp(signUpUser);
    }

    public boolean isLoginAvailable(String login) throws DAOException {
        return userDAO.isLoginAvailable(login);
    }

}
