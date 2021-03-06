package by.epamtc.paymentservice.controller.command.impl.auth.impl;

import by.epamtc.paymentservice.bean.SignInData;
import by.epamtc.paymentservice.bean.SignUpData;
import by.epamtc.paymentservice.bean.User;
import by.epamtc.paymentservice.controller.command.CommandProvider;
import by.epamtc.paymentservice.controller.command.impl.auth.AuthCommand;
import by.epamtc.paymentservice.dao.ResultCode;
import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.service.UserService;
import by.epamtc.paymentservice.service.exception.ServiceException;
import by.epamtc.paymentservice.util.DateParser;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class PersonalEditCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(PersonalEditCommand.class);

    private static final String ERROR_MESSAGE = "Error at PersonalEditCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_LOGIN = "login";
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_SURNAME = "surname";
    private static final String ATTRIBUTE_PATRONYMIC = "patronymic";
    private static final String ATTRIBUTE_PHONE_NUMBER = "phone_number";
    private static final String ATTRIBUTE_BIRTHDATE = "birthdate";
    private static final String ATTRIBUTE_PASSWORD = "password";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String MESSAGE_WRONG_PASSWORD = "Неверный пароль";
    private static final String MESSAGE_DUPLICATE_LOGIN = "Логин заняли перед вами. Выберите новый";
    private static final String PERSONAL_EDIT_PAGE_URL = "WEB-INF/personal_edit.jsp";
    private static final String PERSONAL_PAGE_URL = "WEB-INF/user_about.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);
        final String login = req.getParameter(ATTRIBUTE_LOGIN);
        final String password = req.getParameter(ATTRIBUTE_PASSWORD);
        final String name = req.getParameter(ATTRIBUTE_NAME);
        final String surname = req.getParameter(ATTRIBUTE_SURNAME);
        final String patronymic = req.getParameter(ATTRIBUTE_PATRONYMIC);
        final String phoneNumber = req.getParameter(ATTRIBUTE_PHONE_NUMBER);
        final String birthdate = req.getParameter(ATTRIBUTE_BIRTHDATE);

        final DateParser dateParser = DateParser.getInstance();
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();

        final SignInData signInData = new SignUpData();
        signInData.setLogin(login);
        signInData.setPassword(password);

        user.setLogin(login);
        user.setName(name);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setPhoneNumber(phoneNumber);

        try {
            user.setBirthDate(dateParser.parse(birthdate));

            ResultCode resultCode = userService.updateUser(signInData, user);

                switch (resultCode) {

                    case RESULT_WRONG_PASSWORD: {
                        req.setAttribute(ATTRIBUTE_MESSAGE, MESSAGE_WRONG_PASSWORD);
                        req.getRequestDispatcher(PERSONAL_EDIT_PAGE_URL).forward(req, resp);
                        break;
                    }
                    case RESULT_ERROR_DUPLICATE_LOGIN: {
                        req.setAttribute(ATTRIBUTE_MESSAGE, MESSAGE_DUPLICATE_LOGIN);
                        req.getRequestDispatcher(PERSONAL_EDIT_PAGE_URL).forward(req, resp);
                        break;
                    }
                    case RESULT_SUCCESS: {
                        req.getSession().setAttribute(ATTRIBUTE_USER, user);
                        resp.sendRedirect(PERSONAL_PAGE_URL);
                        break;
                    }
                }

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);

        } catch (ParseException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

    }
}

