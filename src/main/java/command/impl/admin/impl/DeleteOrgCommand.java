package command.impl.admin.impl;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import service.AccountService;
import service.OrganizationService;
import service.ServiceProvider;
import dao.exception.DAOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteOrgCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(DeleteOrgCommand.class);

    private static final String ERROR_MESSAGE = "Error at DeleteOrgCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_ORG_ID = "orgID";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String SUCCESS_PAGE_REDIRECT_URL = "Controller?command=go_to_success_page_command";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int accountID = Integer.parseInt(req.getParameter(ATTRIBUTE_ORG_ID));

        final int STATUS_DELETED = 3;
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrganizationService orgService = serviceProvider.getOrganizationService();

        try {
            orgService.setStatus(accountID, STATUS_DELETED);
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }
        resp.sendRedirect(SUCCESS_PAGE_REDIRECT_URL);
    }
}