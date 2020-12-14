package by.epamtc.paymentservice.controller.command.impl.auth.impl.go;

import by.epamtc.paymentservice.bean.Organization;
import by.epamtc.paymentservice.controller.command.CommandProvider;
import by.epamtc.paymentservice.controller.command.impl.auth.AuthCommand;
import by.epamtc.paymentservice.service.OrganizationService;
import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToPaySelectOrgCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToPaySelectOrgCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToPaySelectOrgCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_SEARCH_NAME = "searchName";
    private static final String ATTRIBUTE_ACCOUNT_FROM_ID = "accountFromID";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_ORGANIZATIONS = "organizations";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String FRAGMENT_SELECT_ORG_URL = "payments_content/pay_select_org.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";


    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int accountFromID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_FROM_ID));
        String searchOrgName = req.getParameter(ATTRIBUTE_SEARCH_NAME);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrganizationService organizationService = serviceProvider.getOrganizationService();
        List<Organization> orgList = null;

        try {
            if(searchOrgName != null) {
                searchOrgName = searchOrgName.trim();
                orgList = organizationService.getActiveOrgList(searchOrgName);

                req.setAttribute(ATTRIBUTE_SEARCH_NAME, searchOrgName);
            } else {
                orgList = organizationService.getActiveOrgList();
            }

            req.setAttribute(ATTRIBUTE_ACCOUNT_FROM_ID,accountFromID);
            req.setAttribute(ATTRIBUTE_ORGANIZATIONS, orgList);
            req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT,FRAGMENT_SELECT_ORG_URL);
            req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }
    }
}
