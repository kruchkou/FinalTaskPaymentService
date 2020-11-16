package by.epamtc.PaymentService.controller.command.impl.admin.impl;

import by.epamtc.PaymentService.controller.command.impl.auth.AuthCommand;
import by.epamtc.PaymentService.dao.exception.DAOException;
import by.epamtc.PaymentService.service.OrganizationService;
import by.epamtc.PaymentService.service.ServiceProvider;
import by.epamtc.PaymentService.controller.command.CommandProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditOrgCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(AddOrgCommand.class);

    private static final String ERROR_MESSAGE = "Error at AddOrgCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_ORG_NAME = "name";
    private static final String ATTRIBUTE_ORG_ID = "orgID";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountID";
    private static final String SUCCESS_PAGE_REDIRECT_URL = "Controller?command=go_to_success_page_command";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String orgName = req.getParameter(ATTRIBUTE_ORG_NAME);
        final int orgID = Integer.parseInt(req.getParameter(ATTRIBUTE_ORG_ID));
        final int accountID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrganizationService orgService = serviceProvider.getOrganizationService();

        try {
            orgService.editOrganization(orgID, orgName, accountID);
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }
        resp.sendRedirect(SUCCESS_PAGE_REDIRECT_URL);
    }
}
