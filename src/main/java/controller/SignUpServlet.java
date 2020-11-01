package controller;

import command.CommandProvider;
import service.UserService;
import dao.exception.DAOException;
import dao.entity.SignUpUser;
import org.apache.log4j.Logger;
import util.UserBuilder;
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

        UserService userService = UserService.getInstance();

        UserBuilder userBuilder = new UserBuilder();
        userBuilder.setLogin(req.getParameter("login"));
        userBuilder.setPassword(req.getParameter("password"));
        userBuilder.setName(req.getParameter("name"));
        userBuilder.setSurname(req.getParameter("surname"));
        userBuilder.setPatronymic(req.getParameter("patronymic"));
        userBuilder.setPhoneNumber(req.getParameter("phone_number"));
        try {
            userBuilder.setBirthDate(req.getParameter("birthdate"));
        } catch (BuildException e) {
            logger.error(e.getMessage(), e);
            CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req, resp);
        }

        SignUpUser signUpUser = userBuilder.build();

        try {
            userService.signUp(signUpUser);
            resp.sendRedirect("sign_in.jsp"); //уведомить пользователя об успешной регистрации!

        } catch (DAOException e) {
            switch (e.getErrorCode()) {
                case DUBLICATE_LOGIN_ERROR_CODE:

                    logger.error(e.getMessage(), e);
                    resp.setContentType("text/html");
                    resp.getWriter().write("К сожалению, кто-то занял этот логин прямо перед вами. Пожалуйста, выберите другой логин");
                    req.getRequestDispatcher("sign_up.jsp").include(req, resp);
                    break;

                default:
                    logger.error(e.getMessage(),e);
                    CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req, resp);
                    break;
            }
        }

    }
}
