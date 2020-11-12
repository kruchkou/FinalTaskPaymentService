package command.impl.auth.impl.go;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import service.AccountService;
import service.ServiceProvider;
import dao.exception.DAOException;
import dao.entity.Account;
import dao.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAccountsCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToAccountsCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAccountsCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_ACCOUNTS = "accounts";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_PAYMENT_VIEW_MESSAGE = "payment_view_scope";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String FRAGMENT_USER_ACCOUNTS_URL = "payments_content/user_accounts.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);
        final String PAYMENT_MESSAGE = "Пользователь : "+user.getName()+" "+user.getSurname();

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountService = serviceProvider.getAccountService();
        List<Account> accountList = null;

        try {
            accountList = accountService.getAccountList(user.getId());
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        req.setAttribute(ATTRIBUTE_PAYMENT_VIEW_MESSAGE,PAYMENT_MESSAGE);
        req.setAttribute(ATTRIBUTE_ACCOUNTS, accountList);
        req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_USER_ACCOUNTS_URL);
        req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);
    }
}
