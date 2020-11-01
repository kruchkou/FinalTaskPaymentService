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
import java.util.List;

public class GoToAccountCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToAccountsCommand.class);

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountService accountService = AccountService.getInstance();
        CardService cardService = CardService.getInstance();

        Account account = null;
        List<Card> cards = null;
        final int accountID = Integer.parseInt(req.getParameter("accountID"));
        try {
            account = accountService.getAccount(accountID);
            cards = cardService.getCardListByAccountID(accountID);
        } catch (DAOException e) {
            logger.error("Error at GoToAccountCommand", e);
            CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req, resp);
        }

        req.setAttribute("account", account);
        req.setAttribute("cards",cards);
        req.setAttribute("payments_content", "payments_content/account_page.jsp");
        req.getRequestDispatcher("WEB-INF/payments.jsp").forward(req, resp);
    }
}
