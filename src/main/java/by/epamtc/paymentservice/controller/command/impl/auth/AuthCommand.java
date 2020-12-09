package by.epamtc.paymentservice.controller.command.impl.auth;

import by.epamtc.paymentservice.controller.command.Command;
import by.epamtc.paymentservice.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AuthCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String MESSAGE_LOG_IN_TO_CONTINUE = "Войдите, чтобы продолжить";
    private static final String COMMAND_SIGN_IN = "go_to_sign_in_command";

    @Override
    public final void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkAuthAndProcess(req, resp);
    }

    private void checkAuthAndProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute(ATTRIBUTE_USER) == null) {
            req.setAttribute(ATTRIBUTE_MESSAGE,MESSAGE_LOG_IN_TO_CONTINUE);
            CommandProvider.getInstance().getCommand(COMMAND_SIGN_IN).execute(req,resp);

        } else {
            process(req,resp);
        }
    }

    protected abstract void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
