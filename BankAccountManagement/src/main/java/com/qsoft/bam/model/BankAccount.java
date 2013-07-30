package com.qsoft.bam.model;

import javax.persistence.*;
import java.util.Date;

/**
 * User: lent
 * Date: 7/8/13
 */
@Entity
@Table(name = "bank_account")
public class BankAccount
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String accountNo;
    @Column
    private double balance;
    @Column
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

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
