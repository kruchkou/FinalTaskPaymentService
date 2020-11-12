package command.impl.admin.impl.go;

import command.CommandProvider;
import command.impl.admin.AdminCommand;
import dao.entity.Organization;
import dao.entity.User;
import dao.exception.DAOException;
import org.apache.log4j.Logger;
import service.OrganizationService;
import service.ServiceProvider;
import service.UserService;

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
    private static final String ATTRIBUTE_ADMIN_FRAGMENT = "admin_content";
    private static final String FRAGMENT_ADMIN_ORG_URL = "payments_content/admin/admin_org.jsp";
    private static final String ADMIN_PAGE_URL = "WEB-INF/admin.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int orgID = Integer.parseInt(req.getParameter(ATTRIBUTE_ORGANIZATION_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrganizationService orgService = serviceProvider.getOrganizationService();
        Organization organization = null;

        try {
            organization = orgService.getOrg(orgID);

        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        req.setAttribute(ATTRIBUTE_ORGANIZATION, organization);
        req.setAttribute(ATTRIBUTE_ADMIN_FRAGMENT, FRAGMENT_ADMIN_ORG_URL);
        req.getRequestDispatcher(ADMIN_PAGE_URL).forward(req, resp);
    }
}