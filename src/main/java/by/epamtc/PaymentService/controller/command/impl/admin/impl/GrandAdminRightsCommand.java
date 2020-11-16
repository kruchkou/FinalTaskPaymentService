package by.epamtc.PaymentService.controller.command.impl.admin.impl;

import by.epamtc.PaymentService.controller.command.CommandProvider;
import by.epamtc.PaymentService.dao.exception.DAOException;
import by.epamtc.PaymentService.service.ServiceProvider;
import by.epamtc.PaymentService.service.UserService;
import by.epamtc.PaymentService.controller.command.impl.admin.AdminCommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GrandAdminRightsCommand extends AdminCommand {
    private static final Logger logger = Logger.getLogger(GrandAdminRightsCommand.class);

    private static final String ERROR_MESSAGE = "Error at GrandAdminRightsCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_USER_ID = "userID";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String SUCCESS_PAGE_REDIRECT_URL = "Controller?command=go_to_success_page_command";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int userID = Integer.parseInt(req.getParameter(ATTRIBUTE_USER_ID));

        final int STATUS_ADMIN = 2;
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();

        try {
            userService.setStatus(userID, STATUS_ADMIN);
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }
        resp.sendRedirect(SUCCESS_PAGE_REDIRECT_URL);
    }

}
