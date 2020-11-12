package dao;

import dao.entity.LoginData;
import dao.entity.SignUpData;
import dao.entity.User;
import dao.exception.DAOException;

import java.util.List;

public interface UserDAO {

    void signUp(SignUpData signUpData) throws DAOException;
    User signIn(LoginData loginData) throws DAOException;
    User updateUser(User user) throws DAOException;
    User getUser(int id) throws DAOException;
    List<User> getUserList(String fio) throws DAOException;
    boolean isLoginAvailable(String login) throws DAOException;
    void setImageByID(String imageSrc, int id) throws DAOException;
    void setStatus(int status, int id) throws DAOException;
    void setPasswordByID(String password, int id) throws DAOException;

}
