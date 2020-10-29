package command.impl;

import command.Command;
import command.CommandProvider;
import controller.UserController;
import dao.DAOException;
import dao.entity.SignUpUser;
import dao.entity.User;
import org.apache.log4j.Logger;
import servlet.SignUpServlet;
import util.UserBuilder;
import util.exception.BuildException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PersonalEditCommand implements Command {

    private static final Logger logger = Logger.getLogger(PersonalEditCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        UserBuilder userBuilder = new UserBuilder();
        UserController userController = UserController.getInstance();

        if (currentUser == null) {
            req.setAttribute("message", "Войдите, чтобы продолжить");
            req.getRequestDispatcher("sign_in.jsp").forward(req, resp);
        } else {
            final int DUBLICATE_LOGIN_ERROR_CODE = 1062;

            userBuilder.setId(currentUser.getId());
            userBuilder.setLogin(req.getParameter("login"));
            userBuilder.setPassword(req.getParameter("password"));
            userBuilder.setName(req.getParameter("name"));
            userBuilder.setSurname(req.getParameter("surname"));
            userBuilder.setPatronymic(req.getParameter("patronymic"));
            userBuilder.setPhoneNumber(req.getParameter("phone_number"));
            try {
                userBuilder.setBirthDate(req.getParameter("birthdate"));
            } catch (BuildException e) {
                logger.error("Can't parse Date in PersonalEditCommand", e);
                CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req, resp);
            }

            User updatedUser = userBuilder.build();

            try {
                if (userController.updateUser(updatedUser) == null) {
                    req.setAttribute("message", "Неверный пароль для обновления данных!");
                    req.getRequestDispatcher("WEB-INF/personal_edit.jsp").forward(req, resp);
                } else {
                    req.getSession().setAttribute("user", updatedUser);
                    resp.sendRedirect("WEB-INF/personal_page.jsp");
                }

            } catch (DAOException e) {
                switch (e.getErrorCode()) {
                    case DUBLICATE_LOGIN_ERROR_CODE:

                        //logger.error(e.getMessage(), e);
                        resp.setContentType("text/html");
                        resp.getWriter().write("К сожалению, кто-то занял этот логин прямо перед вами. Пожалуйста, выберите другой логин");
                        req.getRequestDispatcher("WEB-INF/personal_edit.jsp").include(req, resp);
                        break;

                    default:
                        logger.error(e.getMessage(), e);
                        CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req, resp);
                        break;
                }
            }
        }
    }
}

