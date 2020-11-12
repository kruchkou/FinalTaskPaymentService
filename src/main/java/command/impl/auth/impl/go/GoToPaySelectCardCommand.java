package command.impl.auth.impl.go;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import dao.entity.Card;
import dao.exception.DAOException;
import org.apache.log4j.Logger;
import service.CardService;
import service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToPaySelectCardCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToPaySelectCardCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToPaySelectCardCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountID";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_CARDS = "cards";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String ADD_CARD_COMMAND = "go_to_add_card_command";
    private static final String FRAGMENT_SELECT_CARD_URL = "payments_content/pay_select_card.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int accountID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final CardService cardService = serviceProvider.getCardService();
        List<Card> cardList = null;

        try {
            cardList = cardService.getCardListByAccountID(accountID);
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        req.setAttribute(ATTRIBUTE_ACCOUNT_ID, accountID);
        req.setAttribute(ATTRIBUTE_CARDS, cardList);

        if (cardList.size() == 0) {
            CommandProvider.getInstance().getCommand(ADD_CARD_COMMAND).execute(req, resp);
        } else {
            req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_SELECT_CARD_URL);
            req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);
        }
    }
}
