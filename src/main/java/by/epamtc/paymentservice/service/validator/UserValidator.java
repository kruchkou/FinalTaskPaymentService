package by.epamtc.paymentservice.service.validator;

import by.epamtc.paymentservice.bean.SignUpData;
import by.epamtc.paymentservice.bean.User;
import by.epamtc.paymentservice.util.RegexpPropertyUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String REGEXP_PHONE_NUM = "regexp.phone_number";
    private static final String REGEXP_USER_FIO = "regexp.user_fio";
    private static final String REGEXP_LOGIN = "regexp.login";

    private static final UserValidator instance = new UserValidator();
    private static final RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

    private UserValidator() {
    }

    public static UserValidator getInstance() {
        return instance;
    }

    public boolean validate(SignUpData signUpData) {
        return validateData(signUpData.getLogin(), signUpData.getName(), signUpData.getSurname(), signUpData.getPatronymic(), signUpData.getPhoneNumber());
    }

    public boolean validate(User user) {
        return validateData(user.getLogin(), user.getName(), user.getSurname(), user.getPatronymic(), user.getPhoneNumber());
    }

    private boolean validateData(String login, String name, String surname, String patronymic, String phoneNumber) {
        if (!validateLogin(login)) {
            return false;
        }
        if (!validateFIO(name)) {
            return false;
        }
        if (!validateFIO(surname)) {
            return false;
        }
        if (!validateFIO(patronymic)) {
            return false;
        }
        return validatePhoneNumber(phoneNumber);
    }

    private boolean validateLogin(String login) {
        return isMatchFounded(login, regexpPropertyUtil.getProperty(REGEXP_LOGIN));
    }

    private boolean validateFIO(String fio) {
        return isMatchFounded(fio, regexpPropertyUtil.getProperty(REGEXP_USER_FIO));
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        return isMatchFounded(phoneNumber, regexpPropertyUtil.getProperty(REGEXP_PHONE_NUM));
    }

    private boolean isMatchFounded(String text, String regex) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        return matcher.find();
    }
}