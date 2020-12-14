package by.epamtc.paymentservice.dao;

import by.epamtc.paymentservice.bean.SignInData;
import by.epamtc.paymentservice.bean.SignUpData;
import by.epamtc.paymentservice.bean.User;
import by.epamtc.paymentservice.dao.exception.DAOException;

import java.util.List;

/**
 * Interface provides methods to interact with Users data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 *
 */
public interface UserDAO {

    /**
     * Connects to database, creates new user by data provided and returns {@link ResultCode} object as result.
     *
     * @param signUpData    Object of {@link SignUpData}, which contains user information.
     * @return {@link ResultCode} enum, that shows the result of the method execution.
     * @throws DAOException when problems with database connection occurs.
     */
    ResultCode signUp(SignUpData signUpData) throws DAOException;

    /**
     * Connects to database, checks the credentials and returns an User object if success.
     *
     * @param signInData    is Object of {@link SignInData}, which contains information about user's login and password.
     * @return {@link User} if user's data exists and password matches, null if user's login and password are not correct.
     * @throws DAOException when problems with database connection occurs.
     */
    User signIn(SignInData signInData) throws DAOException;

    /**
     * Connects to database, update user's data and returns {@link ResultCode} object
     *
     * @param user  is Object of {@link User}, which contains full information about user.
     * @return {@link ResultCode} enum, that shows the result of the method execution.
     * @throws DAOException when problems with database connection occurs.
     */
    ResultCode updateUser(User user) throws DAOException;

    /**
     * Connects to database and returns {@link User} object, that contents all info about user by ID.
     *
     * @param id    is User's ID value.
     * @return {@link User} if user's data found, null if not.
     * @throws DAOException when problems with database connection occurs.
     */
    User getUser(int id) throws DAOException;

    /**
     * Connects to database and returns List of {@link User} objects that has FIO like parameter.
     *
     * @param fio   is text, contains user's name, surname or patronymic
     * @return List of {@link User} with all matching users.
     * @throws DAOException when problems with database connection occurs.
     */
    List<User> getUserList(String fio) throws DAOException;

    /**
     * Connects to database and return if the login is free.
     *
     * @param login     is text that contains login.
     * @return true if login is already taken, false if not.
     * @throws DAOException when problems with database connection occurs.
     */
    boolean isLoginAvailable(String login) throws DAOException;

    /**
     * Connects to database and set image src to user by ID.
     *
     * @param imageSrc  is text that contains image src.
     * @param id    is User's ID value.
     * @throws DAOException when problems with database connection occurs.
     */
    void setImageByID(String imageSrc, int id) throws DAOException;

    /**
     * Connects to database and set user status to user by ID.
     *
     * @param status    is user status ID.
     * @param id    is User's ID value.
     * @throws DAOException when problems with database connection occurs.
     */
    void setStatus(int status, int id) throws DAOException;

    /**
     * Connects to database and set password to user by ID.
     *
     * @param password  is text that contains user's password.
     * @param id is User's ID value.
     * @throws DAOException when problems with database connection occurs.
     */
    void setPasswordByID(String password, int id) throws DAOException;

}
