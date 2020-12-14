package by.epamtc.paymentservice.controller.command.impl.admin.impl;

import by.epamtc.paymentservice.bean.Status;
import by.epamtc.paymentservice.controller.command.CommandProvider;
import by.epamtc.paymentservice.controller.command.impl.auth.AuthCommand;
import by.epamtc.paymentservice.service.AccountService;
import by.epamtc.paymentservice.service.OrganizationService;
import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnlockOrgCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(UnlockOrgCommand.class);

    private static final String ERROR_MESSAGE = "Error at UnlockOrgCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_ORG_ID = "orgID";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountID";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String COMMAND_GO_TO_ADMIN_ORG = "GO_TO_ADMIN_ORG_COMMAND";
    private static final String SUCCESS_PAGE_REDIRECT_URL = "Controller?command=go_to_success_page_command";
    private static final String MESSAGE_ACCOUNT_BLOCKED = "Для разблокировки организации необходимо, чтобы привязанный счёт находился в статусе 'Активен'";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int STATUS_OPEN = 1;
        final int orgID = Integer.parseInt(req.getParameter(ATTRIBUTE_ORG_ID));
        final int accountID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrganizationService orgService = serviceProvider.getOrganizationService();
        final AccountService accountService = serviceProvider.getAccountService();

        try {
            Status accountStatus = accountService.getAccountInfo(accountID).getStatus();

            if (accountStatus.getId() != STATUS_OPEN) {
                req.setAttribute(ATTRIBUTE_ORG_ID, orgID);
                req.setAttribute(ATTRIBUTE_MESSAGE, MESSAGE_ACCOUNT_BLOCKED);
                CommandProvider.getInstance().getCommand(COMMAND_GO_TO_ADMIN_ORG).execute(req, resp);
            }
            else {
                orgService.setStatus(orgID, STATUS_OPEN);
                resp.sendRedirect(SUCCESS_PAGE_REDIRECT_URL);
            }

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }
    }
}
