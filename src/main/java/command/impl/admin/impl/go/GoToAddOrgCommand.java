package command.impl.admin.impl.go;

import command.CommandProvider;
import command.impl.admin.AdminCommand;
import dao.entity.Account;
import dao.entity.Card;
import dao.exception.DAOException;
import org.apache.log4j.Logger;
import service.AccountService;
import service.CardService;
import service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAddOrgCommand extends AdminCommand {

    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "admin_content";
    private static final String FRAGMENT_ADMIN_ADD_ORG_URL = "payments_content/admin/admin_add_org.jsp";
    private static final String ADMIN_PAGE_URL = "WEB-INF/admin.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_ADMIN_ADD_ORG_URL);
        req.getRequestDispatcher(ADMIN_PAGE_URL).forward(req, resp);
    }
}
