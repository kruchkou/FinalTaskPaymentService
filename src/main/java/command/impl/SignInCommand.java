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
            CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req,resp);
            //и залогировать
        }

        if (user == null) {
            resp.getWriter().append("Неверный логин или пароль!");
            req.getRequestDispatcher("login.jsp").include(req,resp);
        }
        else {
            ServletContext context = req.getServletContext();
            context.setAttribute("role",user.getRoleName());
            context.setAttribute("login",user.getLogin());
            context.setAttribute("name", user.getName());
            context.setAttribute("surname", user.getSurname());
            context.setAttribute("patronymic", user.getPatronymic());
            context.setAttribute("birth_date", user.getBirthDate());
            context.setAttribute("phone_number", user.getPhoneNumber());

            resp.getWriter().append(user.getName());
            req.getRequestDispatcher("personalarea.jsp").forward(req,resp);
        }
    }
}
