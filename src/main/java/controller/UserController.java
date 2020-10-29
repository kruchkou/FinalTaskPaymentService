package controller;

import dao.DAOException;
import dao.UserDAO;
import dao.entity.LoginUser;
import dao.entity.SignUpUser;
import dao.entity.User;
import util.validator.UserValidator;

public class UserController {

    private static final UserController instance = new UserController();
    private final UserDAO userDAO = new UserDAO();
    private final UserValidator userValidator = UserValidator.getInstance();

    private UserController() {
    }

    public static UserController getInstance() {
        return instance;
    }

    public User signIn(LoginUser loginUser) throws DAOException {
        return userDAO.signIn(loginUser);
    }

    public void signUp(SignUpUser signUpUser) throws DAOException {
        if (!userValidator.validate(signUpUser)) {
            throw new DAOException("User data didn't passed validation");
        }
        userDAO.signUp(signUpUser);
    }

    public void setImageSrc(String imageSrc, int id) throws DAOException {
        userDAO.setImageByID(imageSrc, id);
    }

    public boolean setPassword(String newPassword, LoginUser loginUser) throws DAOException {
        User user = signIn(loginUser);
        if (user == null) {
            return false;
        }
        userDAO.setPasswordByID(newPassword, user.getId());
        return true;
    }

    public User updateUser(User updatedUser) throws DAOException {
        if(signIn(updatedUser) == null) {
            return null;
        }
        if (!userValidator.validate(updatedUser)) {
            throw new DAOException("User data didn't passed validation");
        }
        return userDAO.updateUser(updatedUser);
    }

    public boolean isLoginAvailable(String login) throws DAOException {
        return userDAO.isLoginAvailable(login);
    }

    public void setRoleByID(int role, int id) throws DAOException {
        userDAO.setRoleByID(role, id);
    }

}
