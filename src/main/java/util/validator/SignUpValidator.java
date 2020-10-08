package util.validator;

import dao.entity.SignUpUser;

public class SignUpValidator {

    public boolean validate(SignUpUser signUpUser) {
        String name = signUpUser.getName();
        if (!validateName(name)) {
            return false;
        }
        return true;
    }

    private boolean validateName(String name) {
        if(name != null) {
            if (name.length() <= 6 || name.length() > 16) {
                return false;
            }
        }
        return true;
    }

}
