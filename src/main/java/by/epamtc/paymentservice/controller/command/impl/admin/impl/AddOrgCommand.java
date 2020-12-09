package by.epamtc.paymentservice.controller.command.impl.admin.impl;

import by.epamtc.paymentservice.controller.command.impl.auth.AuthCommand;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.controller.command.CommandProvider;
import by.epamtc.paymentservice.service.OrganizationService;
import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddOrgCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(AddOrgCommand.class);

    private static final String ERROR_MESSAGE = "Error at AddOrgCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_ORG_NAME = "name";
    private static final String ATTRIBUTE_ORG_ACCOUNT = "accountID";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String SUCCESS_PAGE_REDIRECT_URL = "Controller?command=go_to_success_page_command";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String orgName = req.getParameter(ATTRIBUTE_ORG_NAME);
        final int orgAccount = Integer.parseInt(req.getParameter(ATTRIBUTE_ORG_ACCOUNT));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrganizationService orgService = serviceProvider.getOrganizationService();

        try {
            orgService.addOrganization(orgName, orgAccount);
            resp.sendRedirect(SUCCESS_PAGE_REDIRECT_URL);

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }
    }
}
