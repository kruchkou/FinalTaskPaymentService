package by.epamtc.PaymentService.controller.command.impl.go;

import by.epamtc.PaymentService.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToSuccessPageCommand implements Command {

    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String FRAGMENT_SUCCESS_URL = "payments_content/success_page.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_SUCCESS_URL);
        req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);
    }
}
