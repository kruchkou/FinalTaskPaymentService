package by.epamtc.paymentservice.controller.command.impl.auth.impl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import by.epamtc.paymentservice.controller.command.CommandProvider;
import by.epamtc.paymentservice.controller.command.impl.auth.AuthCommand;
import by.epamtc.paymentservice.bean.User;
import by.epamtc.paymentservice.dao.exception.DAOException;
import by.epamtc.paymentservice.service.ServiceProvider;
import by.epamtc.paymentservice.service.UserService;
import by.epamtc.paymentservice.service.exception.ServiceException;
import org.apache.log4j.Logger;

public class UploadUserImageCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(UploadUserImageCommand.class);

//    private static final String PROJECT_PATH = "/home/kruchkou/Документы/PaymentService/web/";
private static final String PROJECT_PATH = "/opt/tomcat/webapps/web_war_exploded/";
    private static final String IMG_FOLDER_PATH = "img/users/";
    private static final String FILENAME_EXTENSION = ".jpg";
    private static final String ATTRIBUTE_PART = "file";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_EXCEPTION = "exception";
    private static final String ERROR_MESSAGE = "Error at UploadUserImageCommand";
    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";
    private static final String SUCCESS_PAGE_REDIRECT_URL = "Controller?command=go_to_success_page_command";


    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        final String IMAGE_LOCATION = IMG_FOLDER_PATH+user.getLogin()+FILENAME_EXTENSION;

        Part inputFile;
        InputStream inputStream = null;
        OutputStream outStream = null;

        try {
            inputFile = req.getPart(ATTRIBUTE_PART);
            inputStream = inputFile.getInputStream();

            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            File imageFile = new File(PROJECT_PATH + IMAGE_LOCATION);

            if (!imageFile.exists()) {
                imageFile.createNewFile();
            }

            outStream = new FileOutputStream(imageFile);
            outStream.write(buffer);


            userService.setImage(IMAGE_LOCATION,user.getId());
            user.setImageSrc(IMAGE_LOCATION);
            resp.sendRedirect(SUCCESS_PAGE_REDIRECT_URL);

        } catch (IOException e) {
            logger.error(ERROR_MESSAGE,e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req,resp);
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE,e);
            req.setAttribute(ATTRIBUTE_EXCEPTION, e);
            CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req,resp);
        }
    }
}
