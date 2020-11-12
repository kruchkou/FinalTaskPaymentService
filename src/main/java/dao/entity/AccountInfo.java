package dao.entity;

import java.util.Objects;

public class AccountInfo {

    private int id;
    private Status status;
    private String userName;
    private String userSurname;
    private String userPatronymic;

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserPatronymic() {
        return userPatronymic;
    }

    public void setUserPatronymic(String userPatronymic) {
        this.userPatronymic = userPatronymic;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "accountID=" + id +
                ", type=" + status +
                ", userName='" + userName + '\'' +
                ", userSurname='" + userSurname + '\'' +
                ", userPatronymic='" + userPatronymic + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountInfo)) return false;
        AccountInfo that = (AccountInfo) o;
        return id == that.id &&
                Objects.equals(status, that.status) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(userSurname, that.userSurname) &&
                Objects.equals(userPatronymic, that.userPatronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, userName, userSurname, userPatronymic);
    }
}
