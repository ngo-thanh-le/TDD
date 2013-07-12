package com.qsoft.bam.service;

import com.qsoft.bam.BankAccount;
import com.qsoft.bam.Transaction;
import com.qsoft.bam.dao.BankAccountDAO;

import java.util.Date;
import java.util.List;

/**
 * User: lent
 * Date: 7/8/13
 */
public interface BankAccountManagement
{
    public void setBankAccountDAO(BankAccountDAO bankAccountDAO);

    void openAccount(String accountNo, double balance);

    BankAccount getAccount(String accountNo);

    void deposit(String accountNo, double amount, String description);

    List<Transaction> getTransactionsOccurred(String accountNo, Date from, Date to);

    void withdraw(String accountNo, double amount, String description);

    List<Transaction> getTransactionsOccurred(String accountNo);
}
