package command.impl.auth.impl;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import dao.entity.User;
import dao.exception.DAOException;
import org.apache.log4j.Logger;
import service.AccountService;
import service.ServiceProvider;
import service.UserService;
import service.impl.UserServiceImpl;
import util.UserBuilder;

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
    private static final String MESSAGE_LOGIN_JUST_GET_OWNED = "Логин заняли перед вами. Выберите новый";
    private static final String PERSONAL_EDIT_PAGE_URL = "WEB-INF/personal_edit.jsp";
    private static final String PERSONAL_PAGE_URL = "WEB-INF/personal_page.jsp";

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

        final int DUBLICATE_LOGIN_ERROR_CODE = 1062;
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();
        final UserBuilder userBuilder = new UserBuilder();

        userBuilder.setId(user.getId());
        userBuilder.setLogin(login);
        userBuilder.setPassword(password);
        userBuilder.setName(name);
        userBuilder.setSurname(surname);
        userBuilder.setPatronymic(patronymic);
        userBuilder.setPhoneNumber(phoneNumber);
        try {
            userBuilder.setBirthDate(birthdate);
        } catch (ParseException e) {
            logger.error(ERROR_MESSAGE, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        User updatedUser = userBuilder.build();

        try {
            if (userService.updateUser(updatedUser) == null) {
                req.setAttribute(ATTRIBUTE_MESSAGE, MESSAGE_WRONG_PASSWORD);
                req.getRequestDispatcher(PERSONAL_EDIT_PAGE_URL).forward(req, resp);
            } else {
                req.getSession().setAttribute(ATTRIBUTE_USER, updatedUser);
                resp.sendRedirect(PERSONAL_PAGE_URL);
            }

        } catch (DAOException e) {
            if (e.getErrorCode() == DUBLICATE_LOGIN_ERROR_CODE) {
                req.setAttribute(ATTRIBUTE_MESSAGE, MESSAGE_LOGIN_JUST_GET_OWNED);
                req.getRequestDispatcher(PERSONAL_EDIT_PAGE_URL).forward(req, resp);
            } else {
                logger.error(ERROR_MESSAGE, e);
                req.setAttribute(ATTRIBUTE_EXCEPTION, e);
                CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
            }
        }

    }
}

