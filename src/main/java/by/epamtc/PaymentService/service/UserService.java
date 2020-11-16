package by.epamtc.PaymentService.service;

import by.epamtc.PaymentService.bean.LoginData;
import by.epamtc.PaymentService.bean.SignUpData;
import by.epamtc.PaymentService.bean.User;
import by.epamtc.PaymentService.dao.exception.DAOException;

import java.util.List;

public interface UserService {

    User signIn(LoginData loginData) throws DAOException;
    User updateUser(User updatedUser) throws DAOException;
    User getUser(int id) throws DAOException;
    List<User> getUserList(String fio) throws DAOException;
    void setImage(String imageSrc, int id) throws DAOException;
    void signUp(SignUpData signUpData) throws DAOException;
    boolean setPassword(String newPassword, LoginData loginData) throws DAOException;
    boolean isLoginAvailable(String login) throws DAOException;
    void setStatus(int role, int id) throws DAOException;

}
