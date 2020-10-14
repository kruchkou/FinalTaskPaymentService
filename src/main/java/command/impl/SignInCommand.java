package command.impl;

import command.Command;
import command.CommandProvider;
import controller.UserController;
import dao.DAOException;
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
        UserController userController = UserController.getInstance();
        User user = null;
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        LoginUser loginUser = new LoginUser();
        loginUser.setLogin(login);
        loginUser.setPassword(password);

        try {
            user = userController.signIn(loginUser);
        } catch (DAOException e) {
            logger.error("Error at SignInCommand",e);
            CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req, resp);
        }

        if (user == null) {
            resp.setContentType("text/html");
            resp.getWriter().write("Неверный логин или пароль!");
            req.getRequestDispatcher("signin.jsp").include(req, resp);
        } else {
            req.getSession().setAttribute("user", user);
            req.getRequestDispatcher("WEB-INF/personalarea.jsp").forward(req, resp);
        }
    }
}
