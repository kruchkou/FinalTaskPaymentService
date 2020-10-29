package dao.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Payment {

    private int id;
    private int cardFrom;
    private int accountTo;
    private BigDecimal amount;
    private Date datetime;
    private HashMap<Integer, String> type;
    private String comment;

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

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getCardFrom() {
        return cardFrom;
    }

    public void setCardFrom(int cardFrom) {
        this.cardFrom = cardFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return id == payment.id &&
                cardFrom == payment.cardFrom &&
                accountTo == payment.accountTo &&
                Objects.equals(type, payment.type) &&
                Objects.equals(datetime, payment.datetime) &&
                Objects.equals(amount, payment.amount) &&
                Objects.equals(comment, payment.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, datetime, cardFrom, accountTo, amount, comment);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", type=" + type +
                ", date=" + datetime +
                ", cardFrom=" + cardFrom +
                ", accountTo=" + accountTo +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                '}';
    }
}
