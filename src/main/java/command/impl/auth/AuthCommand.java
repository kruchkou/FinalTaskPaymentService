package command.impl.auth;

import command.Command;
import dao.exception.DAOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AuthCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkAuthAndProcess(req, resp);
    }

    private void checkAuthAndProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            req.setAttribute("message","Войдите, чтобы продолжить");
            req.getRequestDispatcher("sign_in.jsp").forward(req, resp);

        } else {
            process(req,resp);
        }
    }

    protected abstract void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
