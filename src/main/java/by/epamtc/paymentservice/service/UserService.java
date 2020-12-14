package by.epamtc.paymentservice.service;

import by.epamtc.paymentservice.bean.SignInData;
import by.epamtc.paymentservice.bean.SignUpData;
import by.epamtc.paymentservice.bean.User;
import by.epamtc.paymentservice.dao.ResultCode;
import by.epamtc.paymentservice.service.exception.ServiceException;

import java.util.List;

public interface UserService {

    User signIn(SignInData signInData) throws ServiceException;
    ResultCode updateUser(SignInData signInData, User updatedUser) throws ServiceException;
    User getUser(int id) throws ServiceException;
    List<User> getUserList(String fio) throws ServiceException;
    void setImage(String imageSrc, int id) throws ServiceException;
    ResultCode signUp(SignUpData signUpData) throws ServiceException;
    boolean setPassword(String newPassword, SignInData signInData) throws ServiceException;
    boolean isLoginAvailable(String login) throws ServiceException;
    void setStatus(int role, int id) throws ServiceException;

}
