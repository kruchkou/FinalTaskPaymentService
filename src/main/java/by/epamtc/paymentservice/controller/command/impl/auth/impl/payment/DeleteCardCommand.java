package by.epamtc.paymentservice.controller.command.impl.auth.impl.payment;

import by.epamtc.paymentservice.controller.command.CommandProvider;
import by.epamtc.paymentservice.controller.command.impl.auth.AuthCommand;
import by.epamtc.paymentservice.service.CardService;
import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCardCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(DeleteCardCommand.class);

    private static final String ERROR_MESSAGE = "Error at DeleteCardCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_CARD_ID = "cardID";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String SUCCESS_PAGE_REDIRECT_URL = "Controller?command=go_to_success_page_command";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int cardID = Integer.parseInt(req.getParameter(ATTRIBUTE_CARD_ID));

        final int STATUS_DELETED = 2;
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final CardService cardService = serviceProvider.getCardService();

        try {
            cardService.setStatus(cardID, STATUS_DELETED);
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }
        resp.sendRedirect(SUCCESS_PAGE_REDIRECT_URL);
    }
}
