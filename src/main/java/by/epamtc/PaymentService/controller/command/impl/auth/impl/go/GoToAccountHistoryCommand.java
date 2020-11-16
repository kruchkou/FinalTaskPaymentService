package by.epamtc.PaymentService.controller.command.impl.auth.impl.go;

import by.epamtc.PaymentService.controller.command.CommandProvider;
import by.epamtc.PaymentService.controller.command.impl.auth.AuthCommand;
import by.epamtc.PaymentService.bean.Payment;
import by.epamtc.PaymentService.dao.exception.DAOException;
import org.apache.log4j.Logger;
import by.epamtc.PaymentService.service.PaymentService;
import by.epamtc.PaymentService.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAccountHistoryCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToAccountHistoryCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAccountHistoryCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountID";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_IN_PAYMENTS = "inPayments";
    private static final String ATTRIBUTE_OUT_PAYMENTS = "outPayments";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String ATTRIBUTE_PAYMENT_VIEW_MESSAGE = "payment_view_scope";
    private static final String FRAGMENT_HISTORY_URL = "payments_content/history_page.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int accountID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_ID));
        final String PAYMENT_VIEW_MESSAGE = "Счет №: " + accountID;

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final PaymentService paymentService = serviceProvider.getPaymentService();
        List<Payment> inPaymentList = null;
        List<Payment> outPaymentList = null;

        try {
        inPaymentList = paymentService.getInPaymentListByAccountID(accountID);
        outPaymentList = paymentService.getOutPaymentListByAccountID(accountID);

        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        req.setAttribute(ATTRIBUTE_IN_PAYMENTS, inPaymentList);
        req.setAttribute(ATTRIBUTE_OUT_PAYMENTS, outPaymentList);
        req.setAttribute(ATTRIBUTE_PAYMENT_VIEW_MESSAGE,PAYMENT_VIEW_MESSAGE);
        req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_HISTORY_URL);
        req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);
    }
}
