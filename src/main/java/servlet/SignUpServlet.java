package servlet;

import command.CommandProvider;
import controller.UserController;
import dao.DAOException;
import dao.entity.SignUpUser;
import org.apache.log4j.Logger;
import util.SignUpUserBuilder;
import util.exception.BuildException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SignUpServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int DUBLICATE_LOGIN_ERROR_CODE = 1062;

        UserController userController = UserController.getInstance();

        SignUpUserBuilder signUpUserBuilder = new SignUpUserBuilder();
        signUpUserBuilder.setLogin(req.getParameter("login"));
        signUpUserBuilder.setPassword(req.getParameter("password"));
        signUpUserBuilder.setName(req.getParameter("name"));
        signUpUserBuilder.setSurname(req.getParameter("surname"));
        signUpUserBuilder.setPatronymic(req.getParameter("patronymic"));
        signUpUserBuilder.setPhoneNumber(req.getParameter("phone_number"));
        try {
            signUpUserBuilder.setBirthDate(req.getParameter("birthdate"));
        } catch (BuildException e) {
            logger.error(e.getMessage(), e);
            CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req, resp);
        }

        SignUpUser signUpUser = signUpUserBuilder.build();

        try {
            userController.signUp(signUpUser);
            resp.sendRedirect("login.jsp"); //уведомить пользователя об успешной регистрации!

        } catch (DAOException e) {
            switch (e.getErrorCode()) {
                case DUBLICATE_LOGIN_ERROR_CODE:

                    logger.error(e.getMessage(), e);
                    resp.setContentType("text/html");
                    resp.getWriter().write("К сожалению, кто-то зарегистрировался под тем же логином перед вами. Пожалуйста, выберите другой логин");
                    req.getRequestDispatcher("registration.jsp").include(req, resp);
                    break;

                default:

                    CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req, resp);
                    break;
            }
        }

    }
}
