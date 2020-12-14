package by.epamtc.paymentservice.controller.command.impl.admin.impl.go;

import by.epamtc.paymentservice.bean.Organization;
import by.epamtc.paymentservice.controller.command.CommandProvider;
import by.epamtc.paymentservice.controller.command.impl.admin.AdminCommand;
import by.epamtc.paymentservice.service.OrganizationService;
import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToEditOrgCommand extends AdminCommand {

    private static final Logger logger = Logger.getLogger(GoToEditOrgCommand.class);

    private static final String ERROR_MESSAGE = "Error at AddOrgCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "admin_content";
    private static final String ATTRIBUTE_ORG_ID = "orgID";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountID";
    private static final String ATTRIBUTE_NAME = "name";

    private static final String FRAGMENT_ADMIN_EDIT_ORG_URL = "payments_content/admin/admin_edit_org.jsp";
    private static final String ADMIN_PAGE_URL = "WEB-INF/admin.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orgID = Integer.parseInt(req.getParameter(ATTRIBUTE_ORG_ID));

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrganizationService organizationService = serviceProvider.getOrganizationService();
        Organization organization = null;
        try {
            organization = organizationService.getOrg(orgID);

            req.setAttribute(ATTRIBUTE_ORG_ID, orgID);
            req.setAttribute(ATTRIBUTE_ACCOUNT_ID, organization.getAccount());
            req.setAttribute(ATTRIBUTE_NAME, organization.getName());
            req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_ADMIN_EDIT_ORG_URL);
            req.getRequestDispatcher(ADMIN_PAGE_URL).forward(req, resp);

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }
    }
}
