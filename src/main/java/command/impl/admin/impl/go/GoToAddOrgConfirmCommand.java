package command.impl.auth.impl.go;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import dao.entity.AccountInfo;
import service.AccountService;
import service.ServiceProvider;
import dao.exception.DAOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToAddOrgConfirmCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToAddOrgConfirmCommand.class);

    private static final String ERROR_MESSAGE = "Error at goToAddOrgConfirmCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountID";
    private static final String ATTRIBUTE_ORG_NAME = "name";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String ATTRIBUTE_ACCOUNT_INFO = "accountInfo";
    private static final String ATTRIBUTE_ADMIN_FRAGMENT = "admin_content";
    private static final String MESSAGE_WRONG_ACCOUNT_NUMBER = "Неверный номер счета";
    private static final String COMMAND_GO_TO_ADD_ORG = "go_to_add_org_command";
    private static final String FRAGMENT_ADD_ORG_CONFIRM_URL = "payments_content/admin/admin_add_org_confirm.jsp";
    private static final String ADMIN_PAGE_URL = "WEB-INF/admin.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int accountID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_ID));
        final String orgName = req.getParameter(ATTRIBUTE_ORG_NAME);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountService = serviceProvider.getAccountService();
        AccountInfo accountInfo = null;

        try {
            accountInfo = accountService.getAccountInfo(accountID);
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        if (accountInfo == null) {
            req.setAttribute(ATTRIBUTE_MESSAGE, MESSAGE_WRONG_ACCOUNT_NUMBER);
            CommandProvider.getInstance().getCommand(COMMAND_GO_TO_ADD_ORG).execute(req, resp);
        } else {
            req.setAttribute(ATTRIBUTE_ACCOUNT_INFO, accountInfo);
            req.setAttribute(ATTRIBUTE_ORG_NAME, orgName);
            req.setAttribute(ATTRIBUTE_ADMIN_FRAGMENT, FRAGMENT_ADD_ORG_CONFIRM_URL);
            req.getRequestDispatcher(ADMIN_PAGE_URL).forward(req, resp);
        }
    }
}