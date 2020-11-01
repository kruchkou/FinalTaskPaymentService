package command.impl.auth.impl.payment;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import service.CardService;
import dao.exception.DAOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCardCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(DeleteCardCommand.class);

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int STATUS_CLOSED = 2;
        CardService cardService = CardService.getInstance();
        int cardID = (int) req.getAttribute("cardID"); //getParameter, проверить

        try {
            cardService.setStatus(cardID, STATUS_CLOSED);
        } catch (DAOException e) {
            logger.error("Error at DeleteCardCommand", e);
            CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req, resp);
        }
        CommandProvider.getInstance().getCommand("go_to_cards_command").execute(req, resp);
    }
}
