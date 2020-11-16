package by.epamtc.PaymentService.controller;

import by.epamtc.PaymentService.controller.command.Command;
import by.epamtc.PaymentService.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
public class Controller extends HttpServlet {

    private final CommandProvider commandProvider = CommandProvider.getInstance();

    private static final String ATTRIBUTE_COMMAND = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter(ATTRIBUTE_COMMAND);
        Command command = commandProvider.getCommand(commandName);
        command.execute(req, resp);

    }
}
