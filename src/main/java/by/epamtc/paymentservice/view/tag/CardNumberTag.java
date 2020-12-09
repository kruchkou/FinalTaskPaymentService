package by.epamtc.paymentservice.view.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CardNumberTag extends TagSupport {

    private String cardNumber;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public int doStartTag() throws JspException {
        final String STRING_REPLACE_NUMS = "●●●● ●●●●";
        JspWriter out = pageContext.getOut();

        String FIRST_FOUR_NUMS = null;
        String LAST_FOUR_NUMS = null;
        try {
            if (cardNumber == null || cardNumber.length() != 16) {
                out.write("---- ---- ---- ----");
            } else {
                FIRST_FOUR_NUMS = cardNumber.substring(0, 4);
                LAST_FOUR_NUMS = cardNumber.substring(12, 16);

                out.write(FIRST_FOUR_NUMS + " " + STRING_REPLACE_NUMS + " " + LAST_FOUR_NUMS);
            }
        } catch (IOException e) {
            throw new JspException("Can't write to JspWriter",e);
        }
        return SKIP_BODY;
    }

}
