package by.epamtc.PaymentService.controller.command.impl;

import by.epamtc.PaymentService.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutCommand implements Command {

    private static final String INDEX_PAGE_URL = "index.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(INDEX_PAGE_URL);
    }
}
