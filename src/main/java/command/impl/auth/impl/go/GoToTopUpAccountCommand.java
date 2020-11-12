package command.impl.auth.impl.go;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import dao.entity.Account;
import service.AccountService;
import service.CardService;
import service.ServiceProvider;
import dao.exception.DAOException;
import dao.entity.Card;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToTopUpAccountCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToTopUpAccountCommand.class);

    private static final String ERROR_MESSAGE = "Error at goToTopUpAccountCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_CARD = "card";
    private static final String ATTRIBUTE_ACCOUNT = "account";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_CARD_ID = "cardID";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountID";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String FRAGMENT_TOP_UP_ACCOUNT_URL = "payments_content/top_up_account.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int accountID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_ID));
        final int cardID = Integer.parseInt(req.getParameter(ATTRIBUTE_CARD_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountService = serviceProvider.getAccountService();
        final CardService cardService = serviceProvider.getCardService();
        Card card = null;
        Account account = null;

        try {
            card = cardService.getCard(cardID);
            account = accountService.getAccount(accountID);
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE,e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        req.setAttribute(ATTRIBUTE_ACCOUNT, account);
        req.setAttribute(ATTRIBUTE_CARD, card);
        req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_TOP_UP_ACCOUNT_URL);
        req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);
    }
}

