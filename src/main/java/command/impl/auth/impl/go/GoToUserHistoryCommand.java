package command.impl.auth.impl.go;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import dao.entity.Payment;
import dao.entity.User;
import dao.exception.DAOException;
import org.apache.log4j.Logger;
import service.AccountService;
import service.PaymentService;
import service.ServiceProvider;
import service.impl.PaymentServiceImpl;

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
    private static final String ATTRIBUTE_PAYMENT_VIEW_MESSAGE = "payment_view_scope";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String FRAGMENT_HISTORY_PAGE_URL = "payments_content/history_page.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";


    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);

        final String PAYMENT_VIEW_MESSAGE = "Пользователь : "+user.getName()+" "+user.getSurname();
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final PaymentService paymentService = serviceProvider.getPaymentService();
        List<Payment> inPaymentList = null;
        List<Payment> outPaymentList = null;

        try {
            inPaymentList = paymentService.getInPaymentListByUserID(user.getId());
            outPaymentList = paymentService.getOutPaymentListByUserID(user.getId());

        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        req.setAttribute(ATTRIBUTE_IN_PAYMENTS, inPaymentList);
        req.setAttribute(ATTRIBUTE_OUT_PAYMENTS, outPaymentList);
        req.setAttribute(ATTRIBUTE_PAYMENT_VIEW_MESSAGE,PAYMENT_VIEW_MESSAGE);
        req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT,FRAGMENT_HISTORY_PAGE_URL);
        req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);
    }
}
