package command.impl;

import command.Command;
import command.CommandProvider;
import controller.UserController;
import dao.DAOException;
import dao.entity.LoginUser;
import dao.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInCommand implements Command {


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
            CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req, resp);
            //и залогировать
        }

        if (user == null) {
            resp.setContentType("text/html");
            resp.getWriter().write("Неверный логин или пароль!");
            System.out.println(req.getCharacterEncoding());
            System.out.println(resp.getCharacterEncoding());
            req.getRequestDispatcher("login.jsp").include(req, resp);
        } else {
            //ServletContext context = req.getServletContext();
            req.setAttribute("role", user.getRoleName());
            req.setAttribute("login", user.getLogin());
            req.setAttribute("name", user.getName());
            req.setAttribute("surname", user.getSurname());
            req.setAttribute("patronymic", user.getPatronymic());
            req.setAttribute("birth_date", user.getBirthDate());
            req.setAttribute("phone_number", user.getPhoneNumber());

            resp.getWriter().append(user.getName());
            req.getRequestDispatcher("WEB-INF/personalarea.jsp").forward(req, resp);
        }
    }
}
