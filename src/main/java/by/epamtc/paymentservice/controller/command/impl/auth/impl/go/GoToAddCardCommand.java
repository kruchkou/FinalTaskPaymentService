package by.epamtc.paymentservice.controller.command.impl.auth.impl.go;

import by.epamtc.paymentservice.controller.command.impl.auth.AuthCommand;
import by.epamtc.paymentservice.util.RegexpPropertyUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToAddCardCommand extends AuthCommand {

    private static final String ATTRIBUTE_ACCOUNT_ID = "accountID";
    private static final String ATTRIBUTE_PAYMENT_FRAGMENT = "payments_content";
    private static final String FRAGMENT_ADD_CARD_URL = "payments_content/card_add.jsp";
    private static final String PAYMENT_PAGE_URL = "WEB-INF/payments.jsp";
    private static final String ATTRIBUTE_REGEXP_CARD_NUMBER = "attribute_regexp_card_number";
    private static final String ATTRIBUTE_REGEXP_OWNER_NAME = "attribute_regexp_ownername";
    private static final String ATTRIBUTE_REGEXP_CVV = "attribute_regexp_cvv";
    private static final String REGEXP_PROP_CARD_CVV = "regexp.card_cvv";
    private static final String REGEXP_PROP_CARD_NUMBER = "regexp.card_number";
    private static final String REGEXP_PROP_CARD_OWNERNAME = "regexp.card_ownername";

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int accountID = Integer.parseInt(req.getParameter(ATTRIBUTE_ACCOUNT_ID));
        RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();
        final String REGEXP_CARD_NUMBER = regexpPropertyUtil.getProperty(REGEXP_PROP_CARD_NUMBER);
        final String REGEXP_CARD_OWNERNAME = regexpPropertyUtil.getProperty(REGEXP_PROP_CARD_OWNERNAME);
        final String REGEXP_CARD_CVV = regexpPropertyUtil.getProperty(REGEXP_PROP_CARD_CVV);

        req.setAttribute(ATTRIBUTE_REGEXP_CARD_NUMBER,REGEXP_CARD_NUMBER);
        req.setAttribute(ATTRIBUTE_REGEXP_OWNER_NAME,REGEXP_CARD_OWNERNAME);
        req.setAttribute(ATTRIBUTE_REGEXP_CVV,REGEXP_CARD_CVV);
        req.setAttribute(ATTRIBUTE_ACCOUNT_ID,accountID);
        req.setAttribute(ATTRIBUTE_PAYMENT_FRAGMENT, FRAGMENT_ADD_CARD_URL);
        req.getRequestDispatcher(PAYMENT_PAGE_URL).forward(req, resp);
    }
}
