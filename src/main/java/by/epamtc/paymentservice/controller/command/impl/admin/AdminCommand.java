package by.epamtc.paymentservice.controller.command.impl.admin;

import by.epamtc.paymentservice.bean.User;
import by.epamtc.paymentservice.controller.command.Command;
import by.epamtc.paymentservice.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AdminCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String MESSAGE_LOG_IN_TO_CONTINUE_LOCALE = "sign_in_to_continue";
    private static final String NO_RIGHTS_PAGE_URL = "no_rights.jsp";
    private static final String COMMAND_SIGN_IN = "go_to_sign_in_command";

    @Override
    public final void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkAuthAndProcess(req, resp);
    }

    private void checkAuthAndProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);
        if (user != null) {
            if (user.getStatus().getId() == 2) {
                process(req,resp);
            } else {
                req.getRequestDispatcher(NO_RIGHTS_PAGE_URL).forward(req, resp);
            }
        } else {
            req.setAttribute(ATTRIBUTE_MESSAGE, MESSAGE_LOG_IN_TO_CONTINUE_LOCALE);
            CommandProvider.getInstance().getCommand(COMMAND_SIGN_IN).execute(req,resp);
        }
    }

    protected abstract void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
