package com.qsoft.bam.service;

import com.qsoft.bam.dao.BankAccountDAO;
import com.qsoft.bam.dao.TransactionDAO;
import com.qsoft.bam.model.BankAccount;
import com.qsoft.bam.model.Transaction;
import com.qsoft.bam.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: lent
 * Date: 7/8/13
 */
public class BankAccountManagementImpl implements BankAccountManagement
{
    private BankAccountDAO bankAccountDAO;

    private TransactionDAO transactionDAO;

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
        account.setOpenTimestamp(new Date());
        bankAccountDAO.save(account);
    }

    @Override
    public BankAccount getAccount(String accountNo)
    {
        return bankAccountDAO.findByAccountNo(accountNo);
    }

    @Override
    public void deposit(String accountNo, double amount, String description)
    {
        modifyAccountAmount(accountNo, amount, description);
    }

    @Override
    public List<Transaction> getTransactionsOccurred(String accountNo, Date from, Date to)
    {
        // Simple implement, with retrieve all and filter
        List<Transaction> transactions = transactionDAO.findAll();
        List<Transaction> results = new ArrayList<Transaction>();
        for (Transaction transaction : transactions)
        {
            if (transaction.getAccountNo().equals(accountNo)
                    && DateUtils.isAfterOrEquals(transaction.getTransactionTime(), from)
                    && DateUtils.isBeforeOrEquals(transaction.getTransactionTime(), to))
            {
                results.add(transaction);
            }
        }
        return results;
    }


    @Override
    public void withdraw(String accountNo, double amount, String description)
    {
        modifyAccountAmount(accountNo, -amount, description);
    }

    private void modifyAccountAmount(String accountNo, double amount, String description)
    {
        BankAccount account = bankAccountDAO.findByAccountNo(accountNo);
        account.setBalance(account.getBalance() + amount);
        bankAccountDAO.save(account);
        Transaction transaction = new Transaction();
        transaction.setAccountNo(accountNo);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setTransactionTime(new Date());
        transactionDAO.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsOccurred(String accountNo)
    {
        return getTransactionsOccurred(accountNo, null, null);
    }

    @Override
    public List<Transaction> getRecentTransactions(String accountNo, int numberOfTransaction)
    {
        // Simple implement, with retrieve all and filter
        List<Transaction> transactions = transactionDAO.findAll();
        List<Transaction> results = new ArrayList<Transaction>();
        for (Transaction transaction : transactions)
        {
            if (transaction.getAccountNo().equals(accountNo))
            {
                if (results.size() > (numberOfTransaction - 1))
                {
                    results.remove(0);
                }
                results.add(transaction);
            }
        }
        return results;
    }
}
