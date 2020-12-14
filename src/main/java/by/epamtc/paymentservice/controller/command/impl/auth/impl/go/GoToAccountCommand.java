package by.epamtc.paymentservice.controller.command.impl.auth.impl.go;

import by.epamtc.paymentservice.bean.Account;
import by.epamtc.paymentservice.bean.Card;
import by.epamtc.paymentservice.controller.command.CommandProvider;
import by.epamtc.paymentservice.controller.command.impl.auth.AuthCommand;
import by.epamtc.paymentservice.service.AccountService;
import by.epamtc.paymentservice.service.CardService;
import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAccountCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToAccountCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAccountCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountID";
    private static final String ATTRIBUTE_ACCOUNT = "account";
    private static final String ATTRIBUTE_CARDS = "cards";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String FRAGMENT_ACCOUNT_URL = "payments_content/account_page.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int accountID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountService = serviceProvider.getAccountService();
        final CardService cardService = serviceProvider.getCardService();
        Account account = null;
        List<Card> cards = null;

        try {
            account = accountService.getAccount(accountID);
            cards = cardService.getCardListByAccountID(accountID);

            req.setAttribute(ATTRIBUTE_ACCOUNT, account);
            req.setAttribute(ATTRIBUTE_CARDS,cards);
            req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_ACCOUNT_URL);
            req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

    }
}
