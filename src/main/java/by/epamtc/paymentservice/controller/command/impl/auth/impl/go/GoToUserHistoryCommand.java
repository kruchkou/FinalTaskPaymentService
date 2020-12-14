package by.epamtc.paymentservice.controller.command.impl.auth.impl.go;

import by.epamtc.paymentservice.bean.Payment;
import by.epamtc.paymentservice.bean.User;
import by.epamtc.paymentservice.controller.command.CommandProvider;
import by.epamtc.paymentservice.controller.command.impl.auth.AuthCommand;
import by.epamtc.paymentservice.service.PaymentService;
import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToUserHistoryCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToUserHistoryCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToUserHistoryCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_IN_PAYMENTS = "inPayments";
    private static final String ATTRIBUTE_OUT_PAYMENTS = "outPayments";
    private static final String ATTRIBUTE_HISTORY_SCOPE_VALUE = "history_scope_value";
    private static final String ATTRIBUTE_HISTORY_SCOPE = "history_scope";
    private static final String HISTORY_SCOPE = "user_history";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String FRAGMENT_HISTORY_PAGE_URL = "payments_content/history_page.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";


    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);

        final String USER_CREDENTIALS = user.getName()+" "+user.getSurname();
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final PaymentService paymentService = serviceProvider.getPaymentService();
        List<Payment> inPaymentList = null;
        List<Payment> outPaymentList = null;

        try {
            inPaymentList = paymentService.getInPaymentListByUserID(user.getId());
            outPaymentList = paymentService.getOutPaymentListByUserID(user.getId());

            req.setAttribute(ATTRIBUTE_IN_PAYMENTS, inPaymentList);
            req.setAttribute(ATTRIBUTE_OUT_PAYMENTS, outPaymentList);
            req.setAttribute(ATTRIBUTE_HISTORY_SCOPE,HISTORY_SCOPE);
            req.setAttribute(ATTRIBUTE_HISTORY_SCOPE_VALUE,USER_CREDENTIALS);
            req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT,FRAGMENT_HISTORY_PAGE_URL);
            req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }
    }
}
