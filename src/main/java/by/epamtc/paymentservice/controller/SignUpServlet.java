package by.epamtc.paymentservice.controller;

import by.epamtc.paymentservice.bean.ResultCode;
import by.epamtc.paymentservice.controller.command.CommandProvider;
import by.epamtc.paymentservice.bean.SignUpData;
import by.epamtc.paymentservice.service.exception.ServiceException;
import org.apache.log4j.Logger;
import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.service.UserService;
import by.epamtc.paymentservice.util.UserBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class SignUpServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SignUpServlet.class);

    private static final String ERROR_MESSAGE = "Error at SignUpServlet";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_LOGIN = "login";
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_SURNAME = "surname";
    private static final String ATTRIBUTE_PATRONYMIC = "patronymic";
    private static final String ATTRIBUTE_PHONE_NUMBER = "phone_number";
    private static final String ATTRIBUTE_BIRTHDATE = "birthdate";
    private static final String ATTRIBUTE_PASSWORD = "password";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String MESSAGE_DUPLICATE_LOGIN_LOCALE = "login_already_taken";
    private static final String SIGN_UP_PAGE_URL = "sign_up.jsp";
    private static final String SUCCESS_PAGE_REDIRECT_URL = "Controller?command=go_to_success_page_command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String login = req.getParameter(ATTRIBUTE_LOGIN);
        final String password = req.getParameter(ATTRIBUTE_PASSWORD);
        final String name = req.getParameter(ATTRIBUTE_NAME);
        final String surname = req.getParameter(ATTRIBUTE_SURNAME);
        final String patronymic = req.getParameter(ATTRIBUTE_PATRONYMIC);
        final String phoneNumber = req.getParameter(ATTRIBUTE_PHONE_NUMBER);
        final String birthdate = req.getParameter(ATTRIBUTE_BIRTHDATE);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();

        UserBuilder userBuilder = new UserBuilder();
        userBuilder.setLogin(login);
        userBuilder.setPassword(password);
        userBuilder.setName(name);
        userBuilder.setSurname(surname);
        userBuilder.setPatronymic(patronymic);
        userBuilder.setPhoneNumber(phoneNumber);
        try {
            userBuilder.setBirthDate(birthdate);

            SignUpData signUpData = userBuilder.build();

            ResultCode resultCode = userService.signUp(signUpData);

            switch (resultCode) {
                case RESULT_ERROR_DUPLICATE_LOGIN:
                    req.setAttribute(ATTRIBUTE_MESSAGE, MESSAGE_DUPLICATE_LOGIN_LOCALE);
                    req.getRequestDispatcher(SIGN_UP_PAGE_URL).forward(req, resp);
                    break;
                case RESULT_SUCCESS:
                    resp.sendRedirect(SUCCESS_PAGE_REDIRECT_URL);
                    break;
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
