package com.qsoft.bam;

import java.util.Date;

/**
 * User: lent
 * Date: 7/8/13
 */
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

    private String accountNo;
    private double amount;
    private String description;
    private Date transactionTime;
}
