package servlet;

import command.CommandProvider;
import controller.UserController;
import dao.DAOException;
import dao.entity.SignUpUser;
import util.exception.BuildException;
import util.SignUpUserBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserController userController = UserController.getInstance();

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        SignUpUserBuilder signUpUserBuilder = new SignUpUserBuilder();
        signUpUserBuilder.setLogin(req.getParameter("login"));
        signUpUserBuilder.setPassword(req.getParameter("password")); //спросить, где хранить соли для хэша (в бд поход)
        signUpUserBuilder.setName(req.getParameter("name"));
        signUpUserBuilder.setSurname(req.getParameter("surname"));
        signUpUserBuilder.setPatronymic(req.getParameter("patronymic"));
        signUpUserBuilder.setPhoneNumber(req.getParameter("phone_number"));
        try {
            signUpUserBuilder.setBirthDate(req.getParameter("birthdate"));
        } catch (BuildException e) {
            CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req,resp);
            //и залогировать
        }

        SignUpUser signUpUser = signUpUserBuilder.build();

        try {
            userController.signUp(signUpUser);
        } catch (DAOException e) {
            CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req,resp);
            //и залогировать
        }
        if (!resp.isCommitted()) {
            resp.sendRedirect("/personalarea.jsp");
        }
    }
}
