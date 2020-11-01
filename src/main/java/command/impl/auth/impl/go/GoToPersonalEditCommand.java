package command.impl.auth.impl.go;

import command.Command;
import command.impl.auth.AuthCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToPersonalEditCommand extends AuthCommand {
    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/personal_edit.jsp").forward(req, resp);
    }
}
