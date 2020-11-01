package command.impl;

import command.Command;
import command.CommandProvider;
import service.UserService;
import dao.exception.DAOException;
import dao.entity.LoginUser;
import dao.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInCommand implements Command {

    private static final Logger logger = Logger.getLogger(SignInCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = UserService.getInstance();
        User user = null;
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        LoginUser loginUser = new LoginUser();
        loginUser.setLogin(login);
        loginUser.setPassword(password);

        try {
            user = userService.signIn(loginUser);
        } catch (DAOException e) {
            logger.error("Error at SignInCommand",e);
            CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req, resp);
        }

        if (user == null) {
            resp.setContentType("text/html");
            req.setAttribute("message","Неверный логин или пароль!");
            req.getRequestDispatcher("sign_in.jsp").forward(req, resp);
        } else {
            req.getSession().setAttribute("user", user);
            CommandProvider.getInstance().getCommand("go_to_accounts_command").execute(req,resp);
        }
    }
}
