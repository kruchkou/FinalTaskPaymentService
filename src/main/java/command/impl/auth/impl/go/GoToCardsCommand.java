package command.impl.auth.impl.go;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import service.CardService;
import dao.exception.DAOException;
import dao.entity.Card;
import dao.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToCardsCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToAccountsCommand.class);

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CardService cardService = CardService.getInstance();
        User user = (User) req.getSession().getAttribute("user");
        List<Card> cardList = null;

        try {
            cardList = cardService.getCardListByUserID(user.getId());
        } catch (DAOException e) {
            logger.error("Error at GoToAccountsCommand", e);
            CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req, resp);
        }

        req.setAttribute("cards", cardList);
        req.setAttribute("payments_content","payments_content/user_cards.jsp");
        req.getRequestDispatcher("WEB-INF/payments.jsp").forward(req, resp);
    }
}
