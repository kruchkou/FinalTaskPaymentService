package by.epamtc.paymentservice.controller.command.impl.auth.impl.go;

import by.epamtc.paymentservice.bean.AccountInfo;
import by.epamtc.paymentservice.controller.command.CommandProvider;
import by.epamtc.paymentservice.controller.command.impl.auth.AuthCommand;
import by.epamtc.paymentservice.service.AccountService;
import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToPayTransferToCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToPayTransferToCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAdminAccountsCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_SEARCH_ID = "searchID";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_ACCOUNT_INFO_LIST = "accountInfoList";
    private static final String ATTRIBUTE_ACCOUNT_FROM_ID = "accountFromID";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String FRAGMENT_TRANSFER_TO_URL = "payments_content/pay_transfer_to.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchAccountID = req.getParameter(ATTRIBUTE_SEARCH_ID);
        int accountFromID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_FROM_ID));
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountService = serviceProvider.getAccountService();

        try {
            if(searchAccountID != null) {
                searchAccountID = searchAccountID.trim();

                int searchID = Integer.parseInt(searchAccountID);
                List<AccountInfo> accountInfoList = accountService.getActiveAccountInfoList(searchID);

                req.setAttribute(ATTRIBUTE_ACCOUNT_INFO_LIST, accountInfoList);
            }


            req.setAttribute(ATTRIBUTE_ACCOUNT_FROM_ID,accountFromID);
            req.setAttribute(ATTRIBUTE_SEARCH_ID, searchAccountID);
            req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_TRANSFER_TO_URL);
            req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }
    }
}
