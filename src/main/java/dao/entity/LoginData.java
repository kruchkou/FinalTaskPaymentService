package dao.entity;

import java.util.Objects;

public class LoginData {

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

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginData)) return false;
        LoginData loginData = (LoginData) o;
        return login.equals(loginData.login) &&
                password.equals(loginData.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}