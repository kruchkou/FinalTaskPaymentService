package command.impl.auth.impl.go;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import dao.entity.Card;
import service.AccountService;
import dao.exception.DAOException;
import dao.entity.Account;
import org.apache.log4j.Logger;
import service.CardService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToCardCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToCardCommand.class);

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CardService cardService = CardService.getInstance();
        Card card = null;

        final int cardID = Integer.parseInt(req.getParameter("cardID"));
        try {
            card = cardService.getCard(cardID);
        } catch (DAOException e) {
            logger.error("Error at GoToCardCommand", e);
            //CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req, resp);
            throw new ServletException(e);
        }

        req.setAttribute("card", card);
        req.setAttribute("payments_content", "payments_content/card_page.jsp");
        req.getRequestDispatcher("WEB-INF/payments.jsp").forward(req, resp);
    }
}
