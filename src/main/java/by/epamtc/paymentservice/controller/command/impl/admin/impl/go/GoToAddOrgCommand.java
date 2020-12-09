package by.epamtc.paymentservice.controller.command.impl.admin.impl.go;

import by.epamtc.paymentservice.controller.command.impl.admin.AdminCommand;
import by.epamtc.paymentservice.util.RegexpPropertyUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToAddOrgCommand extends AdminCommand {

    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "admin_content";
    private static final String FRAGMENT_ADMIN_ADD_ORG_URL = "payments_content/admin/admin_add_org.jsp";
    private static final String ADMIN_PAGE_URL = "WEB-INF/admin.jsp";
    private static final String ATTRIBUTE_REGEXP_ORG_NAME = "attribute_regexp_org_name";
    private static final String REGEXP_PROP_ORG_NAME = "regexp.org_name";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();
        final String REGEXP_ORG_NAME = regexpPropertyUtil.getProperty(REGEXP_PROP_ORG_NAME);

        req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_ADMIN_ADD_ORG_URL);
        req.setAttribute(ATTRIBUTE_REGEXP_ORG_NAME, REGEXP_ORG_NAME);
        req.getRequestDispatcher(ADMIN_PAGE_URL).forward(req, resp);
    }
}
