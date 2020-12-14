package by.epamtc.paymentservice.controller.command.impl.go;

import by.epamtc.paymentservice.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToAboutPageCommand implements Command {

    private static final String INDEX_PAGE_URL = "about_page.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(INDEX_PAGE_URL).forward(req,resp);
    }
}
