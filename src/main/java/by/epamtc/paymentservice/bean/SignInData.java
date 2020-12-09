package by.epamtc.paymentservice.bean;

import java.io.Serializable;
import java.util.Objects;

public class SignInData implements Serializable {

    private static final long serialVersionUID = 5765928860296812438L;

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
        if (!(o instanceof SignInData)) return false;
        SignInData signInData = (SignInData) o;
        return login.equals(signInData.login) &&
                password.equals(signInData.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
