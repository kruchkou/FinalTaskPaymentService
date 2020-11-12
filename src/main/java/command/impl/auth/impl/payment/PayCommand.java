package command.impl.auth.impl.payment;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import dao.entity.LoginData;
import dao.entity.User;
import dao.exception.DAOException;
import org.apache.log4j.Logger;
import service.PaymentService;
import service.ServiceProvider;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class PayCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(PayCommand.class);

    private static final String ERROR_MESSAGE = "Error at PayCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_AMOUNT = "amount";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_ACCOUNT_FROM_ID = "accountFromID";
    private static final String ATTRIBUTE_ACCOUNT_TO_ID = "accountToID";
    private static final String ATTRIBUTE_COMMENT = "comment";
    private static final String ATTRIBUTE_PASSWORD = "password";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String MESSAGE_WRONG_PASSWORD = "Неверный пароль";
    private static final String COMMAND_GO_TO_TRANSFER_CONFIRM = "go_to_pay_transfer_confirm_command";
    private static final String SUCCESS_PAGE_REDIRECT_URL = "Controller?command=go_to_success_page_command";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //  Заменить получением тех же данных, что кладуться в TranferConfirmCommand, чтобы не делать новый запрос
        //в случае, если был введен неверный пароль.
        //  С другой стороны, данные могут устареть.
        final User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);
        final int accountFromID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_FROM_ID));
        final int accountToID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_TO_ID));
        final BigDecimal amount = new BigDecimal(req.getParameter(ATTRIBUTE_AMOUNT));
        final String comment = req.getParameter(ATTRIBUTE_COMMENT);
        final String password = req.getParameter(ATTRIBUTE_PASSWORD);

        final LoginData loginData = new LoginData();
        loginData.setLogin(user.getLogin());
        loginData.setPassword(password);
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final PaymentService paymentService = serviceProvider.getPaymentService();
        final UserService userService = serviceProvider.getUserService();
        try {
            if (userService.signIn(loginData) == null) {
                //Весь блок заменяется prevRequest'ом из Локализации
                req.setAttribute(ATTRIBUTE_MESSAGE, MESSAGE_WRONG_PASSWORD);
                req.setAttribute(ATTRIBUTE_ACCOUNT_FROM_ID,accountFromID);
                req.setAttribute(ATTRIBUTE_ACCOUNT_TO_ID,accountToID);
                req.setAttribute(ATTRIBUTE_AMOUNT,amount);
                req.setAttribute(ATTRIBUTE_COMMENT,comment);
                CommandProvider.getInstance().getCommand(COMMAND_GO_TO_TRANSFER_CONFIRM).execute(req,resp);
            }

            paymentService.transferMoney(accountFromID,accountToID,amount,comment);
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }
        resp.sendRedirect(SUCCESS_PAGE_REDIRECT_URL);
    }
}
