package by.epamtc.PaymentService.controller.command.impl.auth.impl.go;

import by.epamtc.PaymentService.controller.command.impl.auth.AuthCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToPayTransferToCommand extends AuthCommand {

    private static final String ATTRIBUTE_ACCOUNT_FROM_ID = "accountFromID";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String FRAGMENT_TRANSFER_TO_URL = "payments_content/pay_transfer_to.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int accountFromID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_FROM_ID));

        req.setAttribute(ATTRIBUTE_ACCOUNT_FROM_ID,accountFromID);
        req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_TRANSFER_TO_URL);
        req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);
    }
}
