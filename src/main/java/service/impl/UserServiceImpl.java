package service.impl;

import dao.DAOProvider;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.entity.LoginData;
import dao.entity.SignUpData;
import dao.entity.User;
import service.UserService;
import util.validator.UserValidator;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserValidator userValidator = UserValidator.getInstance();
    private final DAOProvider daoProvider = DAOProvider.getInstance();
    private final UserDAO userDAO = daoProvider.getUserDAO();

    public User signIn(LoginData loginData) throws DAOException {
        return userDAO.signIn(loginData);
    }

    public User getUser(int id) throws DAOException {
        return userDAO.getUser(id);
    }

    public void signUp(SignUpData signUpData) throws DAOException {
        if (!userValidator.validate(signUpData)) {
            throw new DAOException("User data didn't passed validation");
        }
        userDAO.signUp(signUpData);
    }

    public void setImageSrc(String imageSrc, int id) throws DAOException {
        userDAO.setImageByID(imageSrc, id);
    }

    public boolean setPassword(String newPassword, LoginData loginData) throws DAOException {
        User user = signIn(loginData);
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

    public List<User> getUserList(String fio) throws DAOException {
        return userDAO.getUserList(fio);
    }

    public boolean isLoginAvailable(String login) throws DAOException {
        return userDAO.isLoginAvailable(login);
    }

    public void setStatus(int status, int id) throws DAOException {
        userDAO.setStatus(status, id);
    }

}
