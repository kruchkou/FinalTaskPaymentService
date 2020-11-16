package by.epamtc.PaymentService.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Card {

    private int id;
    private int account;
    private String number;
    private String ownerName;
    private Date expDate;
    private int cvv;
    private HashMap<Integer, String> status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public HashMap<Integer, String> getStatus() {
        return status;
    }

    public void setStatus(HashMap<Integer, String> status) {
        this.status = status;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return id == card.id &&
                account == card.account &&
                number == card.number &&
                cvv == card.cvv &&
                Objects.equals(ownerName, card.ownerName) &&
                Objects.equals(expDate, card.expDate) &&
                Objects.equals(status, card.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, number, ownerName, expDate, cvv, status);
    }

    @Override
    public String
    toString() {
        return "Card{" +
                "id=" + id +
                ", account=" + account +
                ", number=" + number +
                ", ownerName='" + ownerName + '\'' +
                ", expDate=" + expDate +
                ", cvv=" + cvv +
                ", status=" + status +
                '}';
    }
}
