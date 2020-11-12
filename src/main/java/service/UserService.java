package service;

import dao.entity.LoginData;
import dao.entity.SignUpData;
import dao.entity.User;
import dao.exception.DAOException;

import java.util.List;

public interface UserService {

    User signIn(LoginData loginData) throws DAOException;
    User updateUser(User updatedUser) throws DAOException;
    User getUser(int id) throws DAOException;
    List<User> getUserList(String fio) throws DAOException;
    void signUp(SignUpData signUpData) throws DAOException;
    boolean setPassword(String newPassword, LoginData loginData) throws DAOException;
    boolean isLoginAvailable(String login) throws DAOException;
    void setStatus(int role, int id) throws DAOException;

}
