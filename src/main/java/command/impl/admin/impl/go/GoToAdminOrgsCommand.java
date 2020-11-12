package command.impl.admin.impl.go;

import command.CommandProvider;
import command.impl.admin.AdminCommand;
import dao.entity.Organization;
import dao.exception.DAOException;
import org.apache.log4j.Logger;
import service.OrganizationService;
import service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAdminOrgsCommand extends AdminCommand {

    private static final Logger logger = Logger.getLogger(GoToAdminAccountsCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAdminOrgCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_SEARCH_NAME = "searchName";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_ORG_LIST = "orgList";
    private static final String ATTRIBUTE_ADMIN_FRAGMENT = "admin_content";
    private static final String FRAGMENT_ADMIN_ORG_URL = "payments_content/admin/admin_orgs.jsp";
    private static final String ADMIN_PAGE_URL = "WEB-INF/admin.jsp";


    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchOrgName = req.getParameter(ATTRIBUTE_SEARCH_NAME);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrganizationService organizationService = serviceProvider.getOrganizationService();
        List<Organization> orgList = null;

        try {
            if(searchOrgName != null) {
                searchOrgName = searchOrgName.trim();
                orgList = organizationService.getOrgList(searchOrgName);

                req.setAttribute(ATTRIBUTE_SEARCH_NAME, searchOrgName);
            } else {
                orgList = organizationService.getOrgList();
            }
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        req.setAttribute(ATTRIBUTE_ORG_LIST, orgList);
        req.setAttribute(ATTRIBUTE_ADMIN_FRAGMENT, FRAGMENT_ADMIN_ORG_URL);
        req.getRequestDispatcher(ADMIN_PAGE_URL).forward(req, resp);
    }
}
