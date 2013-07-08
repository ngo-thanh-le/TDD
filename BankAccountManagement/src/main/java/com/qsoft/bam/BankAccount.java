package com.qsoft.bam;

import java.util.Date;

/**
 * User: lent
 * Date: 7/8/13
 */
public class BankAccount
{
    private String accountNo;
    private double balance;
    private Date openTimestamp;

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public Date getOpenTimestamp()
    {
        return openTimestamp;
    }

    public void setOpenTimestamp(Date openTimestamp)
    {
        this.openTimestamp = openTimestamp;
    }

    public String getAccountNo()
    {
        return accountNo;
    }

    public void setAccountNo(String accountNo)
    {
        this.accountNo = accountNo;
    }
}
