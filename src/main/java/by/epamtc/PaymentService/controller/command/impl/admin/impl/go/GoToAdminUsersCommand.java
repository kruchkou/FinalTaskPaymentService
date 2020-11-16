package by.epamtc.PaymentService.controller.command.impl.admin.impl.go;

import by.epamtc.PaymentService.controller.command.CommandProvider;
import by.epamtc.PaymentService.bean.User;
import by.epamtc.PaymentService.dao.exception.DAOException;
import by.epamtc.PaymentService.service.ServiceProvider;
import by.epamtc.PaymentService.service.UserService;
import by.epamtc.PaymentService.controller.command.impl.admin.AdminCommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAdminUsersCommand extends AdminCommand {

    private static final Logger logger = Logger.getLogger(GoToAdminAccountsCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAdminUsersCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String ATTRIBUTE_SEARCH_NAME = "searchName";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ATTRIBUTE_USER_LIST = "userList";
    private static final String ATTRIBUTE_ADMIN_FRAGMENT = "admin_content";
    private static final String FRAGMENT_ADMIN_USERS_URL = "payments_content/admin/admin_users.jsp";
    private static final String ADMIN_PAGE_URL = "WEB-INF/admin.jsp";


    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchUserName = req.getParameter(ATTRIBUTE_SEARCH_NAME);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();
        List<User> userList = null;

        try {
            if(searchUserName != null) {
                searchUserName = searchUserName.trim();
                userList = userService.getUserList(searchUserName);

                req.setAttribute(ATTRIBUTE_SEARCH_NAME, searchUserName);
            }
        } catch (DAOException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(ATTRIBUTE_EXCEPTION,e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
        }

        req.setAttribute(ATTRIBUTE_USER_LIST, userList);
        req.setAttribute(ATTRIBUTE_ADMIN_FRAGMENT, FRAGMENT_ADMIN_USERS_URL);
        req.getRequestDispatcher(ADMIN_PAGE_URL).forward(req, resp);
    }
}
