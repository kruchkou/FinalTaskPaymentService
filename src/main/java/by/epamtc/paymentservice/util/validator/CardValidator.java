package by.epamtc.paymentservice.util.validator;

import by.epamtc.paymentservice.bean.Card;
import by.epamtc.paymentservice.util.RegexpPropertyUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardValidator {

    private static final CardValidator instance = new CardValidator();
    private final RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

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
        final String REGEXP_CARD_NUMBER = "regexp.card_number";

        Pattern pattern = Pattern.compile(regexpPropertyUtil.getProperty(REGEXP_CARD_NUMBER));
        Matcher matcher = pattern.matcher(number);

        return matcher.find();
    }

    private boolean validateOwnerName(String ownerName) {
        final String REGEXP_OWNER_NAME = "regexp.card_ownername";

        Pattern pattern = Pattern.compile(regexpPropertyUtil.getProperty(REGEXP_OWNER_NAME));
        Matcher matcher = pattern.matcher(ownerName);

        return matcher.find();
    }

    private boolean validateCvv(String cvv) {
        final String REGEXP_CARD_CVV = "regexp.card_cvv";

        Pattern pattern = Pattern.compile(regexpPropertyUtil.getProperty(REGEXP_CARD_CVV));
        Matcher matcher = pattern.matcher(cvv);

        return matcher.find();
    }
}