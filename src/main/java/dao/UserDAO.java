package dao;

import dao.entity.LoginUser;
import dao.entity.SignUpUser;
import dao.entity.User;
import dao.exception.DAOException;

public interface UserDAO {

    void signUp(SignUpUser signUpUser) throws DAOException;
    User signIn(LoginUser loginUser) throws DAOException;
    User updateUser(User user) throws DAOException;
    boolean isLoginAvailable(String login) throws DAOException;
    void setImageByID(String imageSrc, int id) throws DAOException;
    void setRoleByID(int role, int id) throws DAOException;
    void setPasswordByID(String password, int id) throws DAOException;

}
