package dao.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

public class Account {

    private int id;
    private HashMap<Integer, String> type;
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

    public HashMap<Integer, String> getType() {
        return type;
    }

    public void setType(HashMap<Integer, String> type) {
        this.type = type;
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
}
