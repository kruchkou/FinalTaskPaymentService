package by.epamtc.paymentservice.controller.command.impl.admin.impl.go;

import by.epamtc.paymentservice.bean.User;
import by.epamtc.paymentservice.controller.command.CommandProvider;
import by.epamtc.paymentservice.controller.command.impl.admin.AdminCommand;
import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.service.UserService;
import by.epamtc.paymentservice.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToAdminUserCommand extends AdminCommand {
    private static final Logger logger = Logger.getLogger(GoToAdminAccountCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAdminUserCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_USER_ID = "userID";
    private static final String ATTRIBUTE_ADMIN_FRAGMENT = "admin_content";
    private static final String FRAGMENT_ADMIN_USER_URL = "payments_content/admin/admin_user.jsp";
    private static final String ADMIN_PAGE_URL = "WEB-INF/admin.jsp";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int userID = Integer.parseInt(req.getParameter(ATTRIBUTE_USER_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();
        User user = null;

        try {
            user = userService.getUser(userID);

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        req.setAttribute(ATTRIBUTE_USER, user);
        req.setAttribute(ATTRIBUTE_ADMIN_FRAGMENT, FRAGMENT_ADMIN_USER_URL);
        req.getRequestDispatcher(ADMIN_PAGE_URL).forward(req, resp);
    }
}