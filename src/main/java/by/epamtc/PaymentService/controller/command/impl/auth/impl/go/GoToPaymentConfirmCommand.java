package by.epamtc.PaymentService.controller.command.impl.auth.impl.go;

import by.epamtc.PaymentService.controller.command.CommandProvider;
import by.epamtc.PaymentService.controller.command.impl.auth.AuthCommand;
import by.epamtc.PaymentService.bean.Account;
import by.epamtc.PaymentService.bean.Organization;
import by.epamtc.PaymentService.service.AccountService;
import by.epamtc.PaymentService.service.OrganizationService;
import by.epamtc.PaymentService.service.ServiceProvider;
import by.epamtc.PaymentService.dao.exception.DAOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToPaymentConfirmCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToPayTransferConfirmCommand.class);

    private static final String ERROR_MESSAGE = "Error at goToPaymentConfirmCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_ACCOUNT_FROM_ID = "accountFromID";
    private static final String ATTRIBUTE_ORG_TO_ID = "orgToID";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String ATTRIBUTE_ORGANIZATION = "organization";
    private static final String ATTRIBUTE_ACCOUNT = "account";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String MESSAGE_WRONG_ORGANIZATION_NUMBER = "Неверный номер органнизации";
    private static final String COMMAND_GO_TO_SELECT_ORG_TO = "go_to_pay_select_org_command";
    private static final String FRAGMENT_PAYMENT_CONFIRM_URL = "payments_content/pay_payment_confirm.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int accountFromID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_FROM_ID));
        final int orgToID = Integer.parseInt(req.getParameter(ATTRIBUTE_ORG_TO_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountService = serviceProvider.getAccountService();
        final OrganizationService organizationService = serviceProvider.getOrganizationService();
        Account account = null;
        Organization organization = null;

        try {
            account = accountService.getAccount(accountFromID);
            organization = organizationService.getOrg(orgToID);
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        if (organization == null) {
            req.setAttribute(ATTRIBUTE_MESSAGE, MESSAGE_WRONG_ORGANIZATION_NUMBER);
            CommandProvider.getInstance().getCommand(COMMAND_GO_TO_SELECT_ORG_TO).execute(req, resp);
        } else {
            req.setAttribute(ATTRIBUTE_ACCOUNT, account);
            req.setAttribute(ATTRIBUTE_ORGANIZATION, organization);
            req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_PAYMENT_CONFIRM_URL);
            req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);
        }
    }
}
