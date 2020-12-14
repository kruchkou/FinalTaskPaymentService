package by.epamtc.paymentservice.service.validator;

import by.epamtc.paymentservice.bean.Card;
import by.epamtc.paymentservice.util.RegexpPropertyUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardValidator {

    private static final String REGEXP_CARD_NUMBER = "regexp.card_number";
    private static final String REGEXP_OWNER_NAME = "regexp.card_ownername";
    private static final String REGEXP_CARD_CVV = "regexp.card_cvv";

    private static final CardValidator instance = new CardValidator();
    private static final RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

    private CardValidator() {
    }

    public static CardValidator getInstance() {
        return instance;
    }

    public boolean validate(Card card) {
        String number = card.getNumber();
        String ownerName = card.getOwnerName();
        String cvv = String.valueOf(card.getCvv());

        if (!validateNumber(number)) {
            return false;
        }
        if (!validateOwnerName(ownerName)) {
            return false;
        }
        return validateCvv(cvv);
    }

    private boolean validateNumber(String number) {
        return isMatchFounded(number,regexpPropertyUtil.getProperty(REGEXP_CARD_NUMBER));
    }

    private boolean validateOwnerName(String ownerName) {
        return isMatchFounded(ownerName,regexpPropertyUtil.getProperty(REGEXP_OWNER_NAME));
    }

    private boolean validateCvv(String cvv) {
        return isMatchFounded(cvv, regexpPropertyUtil.getProperty(REGEXP_CARD_CVV));
    }

    private boolean isMatchFounded(String text, String regex) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        return matcher.find();
    }

}