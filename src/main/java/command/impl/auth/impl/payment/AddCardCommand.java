package command.impl.auth.impl.payment;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import dao.entity.Card;
import dao.exception.DAOException;
import service.CardService;
import util.DateParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class AddCardCommand extends AuthCommand {
    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DateParser dateParser = DateParser.getInstance();
        CardService cardService = CardService.getInstance();
        Card card = new Card();
        final int accountID = Integer.parseInt(req.getParameter("accountID"));
        card.setNumber(req.getParameter("number"));
        card.setAccount(accountID);
        card.setCvv(Integer.parseInt(req.getParameter("cvv")));
        card.setOwnerName(req.getParameter("ownerName"));

        try {
            card.setExpDate(dateParser.parse(req.getParameter("expDate")));
        } catch (ParseException e) {
            req.setAttribute("exception",e);
            CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req,resp);
        }
        try {
            cardService.addCard(card);
        } catch (DAOException e) {
            req.setAttribute("exception",e);
            CommandProvider.getInstance().getCommand("go_to_error_page_command").execute(req,resp);
        }
        req.setAttribute("accountID",accountID);
        CommandProvider.getInstance().getCommand("go_to_account_command").execute(req,resp);
    }
}
