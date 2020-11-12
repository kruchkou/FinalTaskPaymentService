package dao.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Account {

    private int id;
    private int user;
    private HashMap<Integer, String> status;
    private BigDecimal balance;
    private Date creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public HashMap<Integer, String> getStatus() {
        return status;
    }

    public void setStatus(HashMap<Integer, String> status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", user=" + user +
                ", status=" + status +
                ", balance=" + balance +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return id == account.id &&
                user == account.user &&
                Objects.equals(status, account.status) &&
                Objects.equals(balance, account.balance) &&
                Objects.equals(creationDate, account.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, status, balance, creationDate);
    }
}
