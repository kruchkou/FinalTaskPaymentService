package by.epamtc.paymentservice.controller;

import by.epamtc.paymentservice.controller.command.Command;
import by.epamtc.paymentservice.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@MultipartConfig
public class Controller extends HttpServlet {

    private final CommandProvider COMMAND_PROVIDER = CommandProvider.getInstance();

    private static final String ATTRIBUTE_COMMAND = "command";
    private static final String ATTRIBUTE_LOCALE = "locale";
    private static final String ATTRIBUTE_PREV_REQUEST = "prev_request";
    private static final String CONTROLLER_URL = "Controller?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession SESSION = req.getSession();
        final String COMMAND_NAME = req.getParameter(ATTRIBUTE_COMMAND);
        final Command COMMAND = COMMAND_PROVIDER.getCommand(COMMAND_NAME);
        final String QUERY_STRING = req.getQueryString();
        final String PREV_REQUEST = CONTROLLER_URL + QUERY_STRING;

        if (SESSION.getAttribute(ATTRIBUTE_LOCALE) == null) {
            SESSION.setAttribute(ATTRIBUTE_LOCALE, Locale.getDefault());
        }

        if (COMMAND == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            COMMAND.execute(req, resp);

            if (QUERY_STRING != null) {
                SESSION.setAttribute(ATTRIBUTE_PREV_REQUEST, PREV_REQUEST);
            }

        }

    }
}
