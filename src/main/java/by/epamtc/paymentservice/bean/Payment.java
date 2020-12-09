package by.epamtc.paymentservice.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Payment implements Serializable {

    private static final long serialVersionUID = -1923777591758885963L;

    private int id;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;
    private Date datetime;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
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
                accountTo == payment.accountTo &&
                accountFrom == payment.accountFrom &&
                Objects.equals(datetime, payment.datetime) &&
                Objects.equals(amount, payment.amount) &&
                Objects.equals(comment, payment.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datetime, accountFrom, accountTo, amount, comment);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", date=" + datetime +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                '}';
    }
}
