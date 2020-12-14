package by.epamtc.paymentservice.controller.command.impl.admin.impl.go;

import by.epamtc.paymentservice.bean.AccountInfo;
import by.epamtc.paymentservice.bean.Organization;
import by.epamtc.paymentservice.controller.command.CommandProvider;
import by.epamtc.paymentservice.controller.command.impl.admin.AdminCommand;
import by.epamtc.paymentservice.service.AccountService;
import by.epamtc.paymentservice.service.OrganizationService;
import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToAdminOrgCommand extends AdminCommand {
    private static final Logger logger = Logger.getLogger(GoToAdminOrgCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAdminOrgCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_ORGANIZATION = "organization";
    private static final String ATTRIBUTE_ORGANIZATION_ID = "orgID";
    private static final String ATTRIBUTE_ACCOUNT_INFO = "accountInfo";
    private static final String ATTRIBUTE_ADMIN_FRAGMENT = "admin_content";
    private static final String FRAGMENT_ADMIN_ORG_URL = "payments_content/admin/admin_org.jsp";
    private static final String ADMIN_PAGE_URL = "WEB-INF/admin.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int orgID = Integer.parseInt(req.getParameter(ATTRIBUTE_ORGANIZATION_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrganizationService orgService = serviceProvider.getOrganizationService();
        final AccountService accountService = serviceProvider.getAccountService();
        AccountInfo accountInfo = null;
        Organization organization = null;

        try {
            organization = orgService.getOrg(orgID);
            accountInfo = accountService.getAccountInfo(organization.getAccount());
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        req.setAttribute(ATTRIBUTE_ORGANIZATION, organization);
        req.setAttribute(ATTRIBUTE_ACCOUNT_INFO, accountInfo);
        req.setAttribute(ATTRIBUTE_ADMIN_FRAGMENT, FRAGMENT_ADMIN_ORG_URL);
        req.getRequestDispatcher(ADMIN_PAGE_URL).forward(req, resp);
    }
}