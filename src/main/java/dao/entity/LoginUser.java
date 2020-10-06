package dao.entity;

import java.util.HashMap;

public class LoginUser {

    private HashMap<Integer, String> role;
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { //Должен хешироватся прямо в методе
        this.password = password;
    }

}
