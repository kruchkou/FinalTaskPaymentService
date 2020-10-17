package command.impl;

import command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToPersonalPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            req.setAttribute("message","Войдите, чтобы продолжить");
            req.getRequestDispatcher("signin.jsp").forward(req, resp);

        } else {
            req.getRequestDispatcher("WEB-INF/personalarea.jsp").forward(req, resp);
        }
    }
}
