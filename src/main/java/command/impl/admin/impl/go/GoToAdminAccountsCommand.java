package command.impl.admin.impl.go;

import command.CommandProvider;
import command.impl.admin.AdminCommand;
import dao.entity.AccountInfo;
import dao.exception.DAOException;
import org.apache.log4j.Logger;
import service.AccountService;
import service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAdminAccountsCommand extends AdminCommand {

    private static final Logger logger = Logger.getLogger(GoToAdminAccountsCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAdminAccountsCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_SEARCH_ID = "searchID";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_ACCOUNT_INFO_LIST = "accountInfoList";
    private static final String ATTRIBUTE_ADMIN_FRAGMENT = "admin_content";
    private static final String FRAGMENT_ADMIN_ACCOUNTS_URL = "payments_content/admin/admin_accounts.jsp";
    private static final String ADMIN_PAGE_URL = "WEB-INF/admin.jsp";


    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchAccountID = req.getParameter(ATTRIBUTE_SEARCH_ID);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountService = serviceProvider.getAccountService();
        List<AccountInfo> accountInfoList = null;

        try {
            if(searchAccountID != null) {
                searchAccountID = searchAccountID.trim();
                int searchID = Integer.parseInt(searchAccountID);
                accountInfoList = accountService.getAccountInfoList(searchID);

                req.setAttribute(ATTRIBUTE_SEARCH_ID, searchAccountID);
            }
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        req.setAttribute(ATTRIBUTE_ACCOUNT_INFO_LIST, accountInfoList);
        req.setAttribute(ATTRIBUTE_ADMIN_FRAGMENT, FRAGMENT_ADMIN_ACCOUNTS_URL);
        req.getRequestDispatcher(ADMIN_PAGE_URL).forward(req, resp);
    }
}
