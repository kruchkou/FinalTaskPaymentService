package by.epamtc.PaymentService.controller.command.impl.auth.impl.go;

import by.epamtc.PaymentService.controller.command.impl.auth.AuthCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToPersonalPageCommand extends AuthCommand {

    private static final String PERSONAL_PAGE_URL = "WEB-INF/personal_page.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PERSONAL_PAGE_URL).forward(req, resp);
    }
}
