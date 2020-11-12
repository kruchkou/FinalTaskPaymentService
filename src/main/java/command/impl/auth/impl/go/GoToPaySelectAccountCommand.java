package command.impl.auth.impl.go;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import dao.entity.Account;
import service.AccountService;
import service.ServiceProvider;
import dao.exception.DAOException;
import dao.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToPaySelectAccountCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToAccountsCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToPaySelectAccountCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_NEXT_COMMAND = "next_command";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_ACCOUNTS = "accounts";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String FRAGMENT_SELECT_ACCOUNT_URL = "payments_content/pay_select_account.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String nextCommand = req.getParameter(ATTRIBUTE_NEXT_COMMAND);
        User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountService = serviceProvider.getAccountService();
        List<Account> accountList = null;

        try {
            accountList = accountService.getActiveAccountList(user.getId());
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        req.setAttribute(ATTRIBUTE_ACCOUNTS, accountList);
        req.setAttribute(ATTRIBUTE_NEXT_COMMAND,nextCommand);
        req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_SELECT_ACCOUNT_URL);
        req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);
    }
}
