package by.epamtc.PaymentService.controller.command.impl.go;

import by.epamtc.PaymentService.controller.command.Command;
import by.epamtc.PaymentService.util.RegexpPropertyUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToSignInCommand implements Command {

    private static final String SIGN_IN_PAGE_URL = "sign_in.jsp";
    private static final String ATTRIBUTE_REGEXP_LOGIN = "attribute_regexp_login";
    private static final String ATTRIBUTE_REGEXP_PASSWORD = "attribute_regexp_password";
    private static final String REGEXP_PROP_LOGIN = "regexp.login";
    private static final String REGEXP_PROP_PASSWORD = "regexp.password";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

        final String REGEXP_LOGIN = regexpPropertyUtil.getProperty(REGEXP_PROP_LOGIN);
        final String REGEXP_PASSWORD = regexpPropertyUtil.getProperty(REGEXP_PROP_PASSWORD);

        req.setAttribute(ATTRIBUTE_REGEXP_LOGIN, REGEXP_LOGIN);
        req.setAttribute(ATTRIBUTE_REGEXP_PASSWORD, REGEXP_PASSWORD);
        req.getRequestDispatcher(SIGN_IN_PAGE_URL).forward(req, resp);
    }
}
