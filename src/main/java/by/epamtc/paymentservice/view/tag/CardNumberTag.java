package by.epamtc.paymentservice.view.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CardNumberTag extends TagSupport {

    private static final String STRING_REPLACE_NUMS = "●●●● ●●●●";
    private static final String MESSAGE_CANT_WRITE_EXCEPTION = "Can't write to JspWriter";

    private String cardNumber;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();

        String FIRST_FOUR_NUMS;
        String LAST_FOUR_NUMS;

        try {
            if (cardNumber == null || cardNumber.length() != 16) {
                out.write("---- ---- ---- ----");
            } else {
                FIRST_FOUR_NUMS = cardNumber.substring(0, 4);
                LAST_FOUR_NUMS = cardNumber.substring(12, 16);

                out.write(FIRST_FOUR_NUMS + " " + STRING_REPLACE_NUMS + " " + LAST_FOUR_NUMS);
            }
        } catch (IOException e) {
            throw new JspException(MESSAGE_CANT_WRITE_EXCEPTION,e);
        }
        return SKIP_BODY;
    }

}
