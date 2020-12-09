package by.epamtc.paymentservice.controller.command.impl.auth.impl.go;

import by.epamtc.paymentservice.controller.command.CommandProvider;
import by.epamtc.paymentservice.controller.command.impl.auth.AuthCommand;
import by.epamtc.paymentservice.bean.Account;
import by.epamtc.paymentservice.bean.AccountInfo;
import by.epamtc.paymentservice.service.AccountService;
import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.bean.User;
import by.epamtc.paymentservice.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class GoToPayTransferConfirmCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToPayTransferConfirmCommand.class);

    private static final String ERROR_MESSAGE = "Error at goToTransferConfirmCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_ACCOUNT_FROM_ID = "accountFromID";
    private static final String ATTRIBUTE_ACCOUNT_TO_ID = "accountToID";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String ATTRIBUTE_ACCOUNT_INFO = "accountInfo";
    private static final String ATTRIBUTE_ACCOUNT = "account";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String MESSAGE_WRONG_ACCOUNT_NUMBER = "Неверный номер счета";
    private static final String COMMAND_GO_TO_TRANSFER_TO = "go_to_pay_transfer_to_command";
    private static final String FRAGMENT_TRANSFER_CONFIRM_URL = "payments_content/pay_transfer_confirm.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int STATUS_OPEN = 1;
        final int accountFromID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_FROM_ID));
        final int accountToID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_TO_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountService = serviceProvider.getAccountService();

        try {
            Account account = accountService.getAccount(accountFromID);
            AccountInfo accountInfo = accountService.getAccountInfo(accountToID);

            if (accountInfo == null || accountInfo.getStatus().getId() != STATUS_OPEN) {

                req.setAttribute(ATTRIBUTE_MESSAGE, MESSAGE_WRONG_ACCOUNT_NUMBER);
                CommandProvider.getInstance().getCommand(COMMAND_GO_TO_TRANSFER_TO).execute(req, resp);

            } else {

                req.setAttribute(ATTRIBUTE_ACCOUNT, account);
                req.setAttribute(ATTRIBUTE_ACCOUNT_INFO, accountInfo);
                req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_TRANSFER_CONFIRM_URL);
                req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);
            }

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

    }
}
