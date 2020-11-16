package by.epamtc.PaymentService.controller.command.impl.admin.impl.go;

import by.epamtc.PaymentService.controller.command.CommandProvider;
import by.epamtc.PaymentService.controller.command.impl.auth.AuthCommand;
import by.epamtc.PaymentService.bean.Account;
import by.epamtc.PaymentService.dao.exception.DAOException;
import by.epamtc.PaymentService.service.AccountService;
import by.epamtc.PaymentService.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAdminUserAccountsCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToAdminUserAccountsCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAdminUserAccountsCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_ACCOUNTS = "accounts";
    private static final String ATTRIBUTE_USER_ID = "userID";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "admin_content";
    private static final String FRAGMENT_ADMIN_USER_ACCOUNTS_URL = "payments_content/admin/admin_user_accounts.jsp";
    private static final String ADMIN_PAGE_URL = "WEB-INF/admin.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int userID = Integer.parseInt(req.getParameter(ATTRIBUTE_USER_ID));
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountService = serviceProvider.getAccountService();
        List<Account> accountList = null;

        try {
            accountList = accountService.getAccountList(userID);
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        req.setAttribute(ATTRIBUTE_ACCOUNTS, accountList);
        req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_ADMIN_USER_ACCOUNTS_URL);
        req.getRequestDispatcher(ADMIN_PAGE_URL).forward(req, resp);
    }
}
