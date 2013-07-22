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
public class BankAccountManagementImpl implements BankAccountManagement
{
    private BankAccountDAO bankAccountDAO;

    @Override
    public void setBankAccountDAO(BankAccountDAO bankAccountDAO)
    {
        this.bankAccountDAO = bankAccountDAO;
    }

    @Override
    public void openAccount(String accountNo, double balance)
    {
        BankAccount account = new BankAccount();
        account.setAccountNo(accountNo);
        account.setBalance(balance);
        bankAccountDAO.create(account);
    }

    @Override
    public BankAccount getAccount(String accountNo)
    {
        return bankAccountDAO.get(accountNo);
    }

    @Override
    public void deposit(String accountNo, double amount, String description)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Transaction> getTransactionsOccurred(String accountNo, Date from, Date to)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void withdraw(String accountNo, double amount, String description)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Transaction> getTransactionsOccurred(String accountNo)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
