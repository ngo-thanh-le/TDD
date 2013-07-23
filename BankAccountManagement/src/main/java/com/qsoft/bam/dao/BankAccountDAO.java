package com.qsoft.bam.dao;

import com.qsoft.bam.BankAccount;

/**
 * User: lent
 * Date: 7/12/13
 */
public interface BankAccountDAO
{
    public void create(BankAccount account);

    public BankAccount get(String accountNo);

    public void update(BankAccount updatedAccount);
}
