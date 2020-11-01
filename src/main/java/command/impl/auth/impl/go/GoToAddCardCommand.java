package command.impl.auth.impl.go;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import dao.entity.Card;
import service.CardService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToAddCardCommand extends AuthCommand {
    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int accountID = Integer.parseInt(req.getParameter("accountID"));
        req.setAttribute("accountID",accountID);
        req.setAttribute("payments_content", "payments_content/card_add.jsp");
        req.getRequestDispatcher("WEB-INF/payments.jsp").forward(req, resp);
    }
}
