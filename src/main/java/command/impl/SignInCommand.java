package command.impl;

import command.Command;
import command.CommandProvider;
import controller.UserController;
import dao.DAOException;
import dao.UserDAO;
import entity.LoginUser;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInCommand implements Command {


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserController userController = UserController.getInstance();

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        User user = null;
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        LoginUser loginUser = new LoginUser();
        loginUser.setLogin(login);
        loginUser.setPassword(password);


        try {
            user = userController.signIn(loginUser);
        } catch (DAOException e) {
            CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req,resp);
            //и залогировать
        }

        if (user == null) {
            resp.getWriter().append("Неверный логин или пароль!");
            req.getRequestDispatcher("login.jsp").include(req,resp);
        }
        else {
            req.getSession().setAttribute("user", user);
            resp.getWriter().append(user.getLogin());
            resp.sendRedirect("personalarea.jsp");
        }
    }
}
