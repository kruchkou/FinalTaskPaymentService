package util;

import dao.entity.User;
import util.exception.BuildException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserBuilder {

    User user = new User();

    public void setLogin(String login) {
        user.setLogin(login);
    }

    public void setPassword(String password) {
        user.setPassword(password); //заменить на хэш
    }

    public void setName(String name) {
        user.setName(name);
    }

    public void setSurname(String surname) {
        user.setSurname(surname);
    }

    public void setPatronymic(String patronymic) {
        user.setPatronymic(patronymic);
    }

    public void setId(int id) {
        user.setId(id);
    }

    public void setBirthDate(String birthDate) throws BuildException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            user.setBirthDate(df.parse(birthDate));
        } catch (ParseException e) {
            throw new BuildException("Cant parse birthdate", e);
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        user.setPhoneNumber(phoneNumber);
    }

    public User build() {
        return user;
    }

}
