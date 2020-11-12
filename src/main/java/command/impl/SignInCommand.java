package command.impl;

import command.Command;
import command.CommandProvider;
import service.ServiceProvider;
import service.UserService;
import dao.exception.DAOException;
import dao.entity.LoginData;
import dao.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInCommand implements Command {

    private static final Logger logger = Logger.getLogger(SignInCommand.class);

    private static final String ERROR_MESSAGE = "Error at SignInCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_LOGIN = "login";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_PASSWORD = "password";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String MESSAGE_WRONG_LOGIN_OR_PASSWORD = "Неверный логин или пароль!";
    private static final String SIGN_IN_PAGE_URL = "sign_in.jsp";
    private static final String COMMAND_GO_TO_ACCOUNTS = "go_to_accounts_command";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(ATTRIBUTE_LOGIN);
        String password = req.getParameter(ATTRIBUTE_PASSWORD);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();
        User user = null;

        LoginData loginData = new LoginData();
        loginData.setLogin(login);
        loginData.setPassword(password);

        try {
            user = userService.signIn(loginData);
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE,e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        if (user == null) {
            req.setAttribute(ATTRIBUTE_MESSAGE,MESSAGE_WRONG_LOGIN_OR_PASSWORD);
            req.getRequestDispatcher(SIGN_IN_PAGE_URL).forward(req, resp);
        } else {
            req.getSession().setAttribute(ATTRIBUTE_USER, user);
            CommandProvider.getInstance().getCommand(COMMAND_GO_TO_ACCOUNTS).execute(req,resp);
        }
    }
}
