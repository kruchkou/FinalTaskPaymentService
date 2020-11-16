package by.epamtc.PaymentService.controller.command.impl.auth.impl.go;

import by.epamtc.PaymentService.controller.command.impl.auth.AuthCommand;
import by.epamtc.PaymentService.util.RegexpPropertyUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToPersonalEditCommand extends AuthCommand {

    private static final String PERSONAL_EDIT_PAGE_URL = "WEB-INF/personal_edit.jsp";
    private static final String ATTRIBUTE_REGEXP_LOGIN = "attribute_regexp_login";
    private static final String ATTRIBUTE_REGEXP_PASSWORD = "attribute_regexp_password";
    private static final String ATTRIBUTE_REGEXP_FIO = "attribute_regexp_fio";
    private static final String ATTRIBUTE_REGEXP_PHONE_NUMBER = "attribute_regexp_phone_number";
    private static final String REGEXP_PROP_LOGIN = "regexp.login";
    private static final String REGEXP_PROP_PASSWORD = "regexp.password";
    private static final String REGEXP_PROP_FIO = "regexp.user_fio";
    private static final String REGEXP_PROP_PHONE_NUMBER = "regexp.phone_number";


    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

        final String REGEXP_LOGIN = regexpPropertyUtil.getProperty(REGEXP_PROP_LOGIN);
        final String REGEXP_PASSWORD = regexpPropertyUtil.getProperty(REGEXP_PROP_PASSWORD);
        final String REGEXP_FIO = regexpPropertyUtil.getProperty(REGEXP_PROP_FIO);
        final String REGEXP_PHONE_NUMBER = regexpPropertyUtil.getProperty(REGEXP_PROP_PHONE_NUMBER);

        req.setAttribute(ATTRIBUTE_REGEXP_LOGIN, REGEXP_LOGIN);
        req.setAttribute(ATTRIBUTE_REGEXP_PASSWORD, REGEXP_PASSWORD);
        req.setAttribute(ATTRIBUTE_REGEXP_FIO, REGEXP_FIO);
        req.setAttribute(ATTRIBUTE_REGEXP_PHONE_NUMBER, REGEXP_PHONE_NUMBER);

        req.getRequestDispatcher(PERSONAL_EDIT_PAGE_URL).forward(req, resp);
    }
}
