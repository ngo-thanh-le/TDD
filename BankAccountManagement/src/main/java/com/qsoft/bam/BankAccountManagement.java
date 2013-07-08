package com.qsoft.bam;

import java.util.Date;
import java.util.List;

/**
 * User: lent
 * Date: 7/8/13
 */
public interface BankAccountManagement
{
    void openAccount(String accountNo, double balance);

    BankAccount getAccount(String accountNo);

    void deposit(String accountNo, double amount, String description);

    List<Transaction> getTransaction(String accountNo, Date from, Date to);
}
