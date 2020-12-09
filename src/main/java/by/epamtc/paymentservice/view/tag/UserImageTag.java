package by.epamtc.paymentservice.view.tag;

import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.service.UserService;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class UserImageTag extends TagSupport {

    final String DEFAULT_IMG_URL = "img/users/default.jpg";
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
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();

        try {
            if (imageURL == null || imageURL.equals("")) {
                out.write(DEFAULT_IMG_URL);
            } else {
                out.write(imageURL);
            }
        } catch (IOException e) {
            throw new JspException("Can't write to JspWriter",e);
        }
        return SKIP_BODY;
    }

}
