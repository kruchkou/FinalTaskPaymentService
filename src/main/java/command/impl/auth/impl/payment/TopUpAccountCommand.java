package command.impl.auth.impl.payment;

import command.CommandProvider;
import command.impl.auth.AuthCommand;
import dao.entity.LoginData;
import dao.entity.User;
import service.AccountService;
import service.ServiceProvider;
import service.UserService;
import dao.exception.DAOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class TopUpAccountCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(TopUpAccountCommand.class);

    private static final String ERROR_MESSAGE = "Error at topUpCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_AMOUNT = "amount";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountID";
    private static final String ATTRIBUTE_CARD_ID = "cardID";
    private static final String ATTRIBUTE_PASSWORD = "password";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String MESSAGE_WRONG_PASSWORD = "Неверный пароль";
    private static final String COMMAND_GO_TO_TOP_UP_ACCOUNT_COMMAND = "go_to_top_up_account_command";
    private static final String SUCCESS_PAGE_REDIRECT_URL = "Controller?command=go_to_success_page_command";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Смотреть комментарий payCommand
        final User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);
        final int accountID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_ID));
        final int cardID = Integer.parseInt(req.getParameter(ATTRIBUTE_CARD_ID));
        final BigDecimal amount = new BigDecimal(req.getParameter(ATTRIBUTE_AMOUNT));
        final String password = req.getParameter(ATTRIBUTE_PASSWORD);

        final LoginData loginData = new LoginData();
        loginData.setLogin(user.getLogin());
        loginData.setPassword(password);
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountService = serviceProvider.getAccountService();
        final UserService userService = serviceProvider.getUserService();
        try {
            if (userService.signIn(loginData) == null) {
                //Весь блок заменяется prevRequest'ом из Локализации
                req.setAttribute(ATTRIBUTE_MESSAGE, MESSAGE_WRONG_PASSWORD);
                req.setAttribute(ATTRIBUTE_ACCOUNT_ID,accountID);
                req.setAttribute(ATTRIBUTE_CARD_ID,cardID);
                CommandProvider.getInstance().getCommand(COMMAND_GO_TO_TOP_UP_ACCOUNT_COMMAND).execute(req,resp);
            }
            else {
                accountService.topUp(accountID,amount);
                resp.sendRedirect(SUCCESS_PAGE_REDIRECT_URL);
            }

        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }
    }
}
