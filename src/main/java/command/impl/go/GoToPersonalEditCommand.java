package command.impl.go;

import command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToPersonalEditCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            req.setAttribute("message","Войдите, чтобы продолжить");
            req.getRequestDispatcher("sign_in.jsp").forward(req, resp);

        } else {
            req.getRequestDispatcher("WEB-INF/personal_edit.jsp").forward(req, resp);
        }
    }
}
