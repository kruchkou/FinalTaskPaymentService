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

public class GoToCardCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToCardCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToCardCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_CARD_ID = "cardID";
    private static final String ATTRIBUTE_CARD = "card";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String FRAGMENT_CARD_PAGE_URL = "payments_content/card_page.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int cardID = Integer.parseInt(req.getParameter(ATTRIBUTE_CARD_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final CardService cardService = serviceProvider.getCardService();
        Card card = null;

        try {
            card = cardService.getCard(cardID);
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        req.setAttribute(ATTRIBUTE_CARD, card);
        req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_CARD_PAGE_URL);
        req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);
    }
}
