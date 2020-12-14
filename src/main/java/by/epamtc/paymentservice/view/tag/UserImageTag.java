package by.epamtc.paymentservice.view.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class UserImageTag extends TagSupport {

    private static final String DEFAULT_IMG_URL = "img/users/default.jpg";
    private static final String MESSAGE_CANT_WRITE_EXCEPTION = "Can't write to JspWriter";
    private static final String EMPTY_STRING = "";

    private String imageURL;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            if (imageURL == null || imageURL.equals(EMPTY_STRING)) {
                out.write(DEFAULT_IMG_URL);
            } else {
                out.write(imageURL);
            }
        } catch (IOException e) {
            throw new JspException(MESSAGE_CANT_WRITE_EXCEPTION,e);
        }
        return SKIP_BODY;
    }

}
