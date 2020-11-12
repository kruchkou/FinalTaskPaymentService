package command.impl.auth.impl.payment;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import dao.entity.Card;
import dao.exception.DAOException;
import org.apache.log4j.Logger;
import service.AccountService;
import service.CardService;
import service.ServiceProvider;
import service.impl.CardServiceImpl;
import util.DateParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class AddCardCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(AddCardCommand.class);

    private static final String ERROR_MESSAGE = "Error at AddCardCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountID";
    private static final String ATTRIBUTE_NUMBER = "number";
    private static final String ATTRIBUTE_CVV = "cvv";
    private static final String ATTRIBUTE_OWNER_NAME = "ownerName";
    private static final String ATTRIBUTE_EXP_DATE = "expDate";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String SUCCESS_PAGE_REDIRECT_URL = "Controller?command=go_to_success_page_command";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int accountID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_ID));
        DateParser dateParser = DateParser.getInstance();
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final CardService cardService = serviceProvider.getCardService();
        Card card = new Card();

        card.setNumber(req.getParameter(ATTRIBUTE_NUMBER));
        card.setAccount(accountID);
        card.setCvv(Integer.parseInt(req.getParameter(ATTRIBUTE_CVV)));
        card.setOwnerName(req.getParameter(ATTRIBUTE_OWNER_NAME));

        try {
            card.setExpDate(dateParser.parse(req.getParameter(ATTRIBUTE_EXP_DATE)));
            cardService.addCard(card);
        } catch (ParseException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req,resp);
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req,resp);
        }
        resp.sendRedirect(SUCCESS_PAGE_REDIRECT_URL);
    }
}
