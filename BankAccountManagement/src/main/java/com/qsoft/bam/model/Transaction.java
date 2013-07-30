package com.qsoft.bam.model;

import javax.persistence.*;
import java.util.Date;

/**
 * User: lent
 * Date: 7/8/13
 */
@Entity
@Table(name = "transaction")
public class Transaction
{
    public String getAccountNo()
    {
        return accountNo;
    }

    public void setAccountNo(String accountNo)
    {
        this.accountNo = accountNo;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getTransactionTime()
    {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime)
    {
        this.transactionTime = transactionTime;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String accountNo;
    @Column
    private double amount;
    @Column
    private String description;
    @Column
    private Date transactionTime;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
