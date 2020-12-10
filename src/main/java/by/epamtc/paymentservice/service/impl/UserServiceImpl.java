package by.epamtc.paymentservice.service.impl;

import by.epamtc.paymentservice.bean.SignInData;
import by.epamtc.paymentservice.bean.ResultCode;
import by.epamtc.paymentservice.bean.SignUpData;
import by.epamtc.paymentservice.bean.User;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.dao.DAOProvider;
import by.epamtc.paymentservice.dao.UserDAO;
import by.epamtc.paymentservice.service.UserService;
import by.epamtc.paymentservice.service.exception.ServiceException;
import by.epamtc.paymentservice.service.validator.UserValidator;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserValidator userValidator = UserValidator.getInstance();
    private final DAOProvider daoProvider = DAOProvider.getInstance();
    private final UserDAO userDAO = daoProvider.getUserDAO();

    @Override
    public User signIn(SignInData signInData) throws ServiceException {
        try {
            return userDAO.signIn(signInData);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle signIn request at UserService",e);
        }
    }

    @Override
    public User getUser(int id) throws ServiceException {
        try {
            return userDAO.getUser(id);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getUser request at UserService",e);
        }
    }

    @Override
    public ResultCode signUp(SignUpData signUpData) throws ServiceException {
        if (!userValidator.validate(signUpData)) {
            throw new ServiceException("User data didn't passed validation");
        } else {
            try {
                return userDAO.signUp(signUpData);
            } catch (DAOException e) {
                throw new ServiceException("Can't handle signUp request at UserService", e);
            }
        }
    }

    @Override
    public void setImage(String imageSrc, int id) throws ServiceException {
        try {
            userDAO.setImageByID(imageSrc, id);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle setImage request at UserService",e);
        }
    }

    @Override
    public boolean setPassword(String newPassword, SignInData signInData) throws ServiceException {
        User user = signIn(signInData);
        if (user == null) {
            return false;
        }
        try {
            userDAO.setPasswordByID(newPassword, user.getId());
            return true;
        } catch (DAOException e) {
            throw new ServiceException("Can't handle setPassword request at UserService",e);
        }
    }

    @Override
    public ResultCode updateUser(User updatedUser) throws ServiceException {
        if(signIn(updatedUser) == null) {
            return ResultCode.RESULT_WRONG_PASSWORD;
        }
        if (!userValidator.validate(updatedUser)) {
            throw new ServiceException("User data didn't passed validation");
        }
        try {
            return userDAO.updateUser(updatedUser);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle updateUser request at UserService",e);
        }
    }

    @Override
    public List<User> getUserList(String fio) throws ServiceException {
        try {
            return userDAO.getUserList(fio);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle getUserList request at UserService",e);
        }
    }

    @Override
    public boolean isLoginAvailable(String login) throws ServiceException {
        try {
            return userDAO.isLoginAvailable(login);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle isLoginAvailable request at UserService",e);
        }
    }

    @Override
    public void setStatus(int status, int id) throws ServiceException {
        try {
            userDAO.setStatus(status, id);
        } catch (DAOException e) {
            throw new ServiceException("Can't handle setStatus request at UserService",e);
        }
    }

}
