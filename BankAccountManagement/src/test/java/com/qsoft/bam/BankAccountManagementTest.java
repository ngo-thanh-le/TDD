package com.qsoft.bam;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import static junit.framework.Assert.*;
import static junit.framework.TestCase.assertNull;

/**
 * User: lent
 * Date: 7/8/13
 */
public class BankAccountManagementTest
{
    BankAccountManagement bankAccountManagement;

    @Test
    public void testRetrieveNotExist()
    {
        BankAccount account = bankAccountManagement.getAccount("THIS_NOT_E");
        assertNull(account);
    }

    @Test
    public void testOpenAccount()
    {
        bankAccountManagement.openAccount("1234567890", 0d);
        BankAccount account = bankAccountManagement.getAccount("01234567890");
        assertNotNull(account);
        assertEquals("01234567890", account.getAccountNo());
        assertEquals(0d, account.getBalance());
    }

    @Test
    public void testDeposit()
    {
        bankAccountManagement.openAccount("1234567890", 0d);
        bankAccountManagement.deposit("1234567890", 1000000d, "Give me a million.");
        BankAccount account = bankAccountManagement.getAccount("01234567890");
        assertNotNull(account);
        assertEquals(1000000d, account.getBalance());
        assertEquals("01234567890", account.getAccountNo());
    }

    @Test
    public void testVerifyTransaction()
    {
        bankAccountManagement.openAccount("1234567890", 0d);
        bankAccountManagement.deposit("1234567890", 1000000d, "Give me a million.");
        BankAccount account = bankAccountManagement.getAccount("01234567890");
        assertNotNull(account);
        assertEquals(1000000d, account.getBalance());
        assertEquals("01234567890", account.getAccountNo());

        List<Transaction> transactions = bankAccountManagement.getTransaction("1234567890", new Date(), new Date());
        assertTrue(transactions.size() == 1);
        assertEquals(transactions.get(0).getAccountNo(), 1000000d);
        assertEquals(transactions.get(0).getAccountNo(), "01234567890");
    }

    @Test
    public void testWithdraw()
    {
        bankAccountManagement.openAccount("1234567890", 999999d);
        bankAccountManagement.withdraw("1234567890", 888888d, "Give me 888888$.");
        BankAccount account = bankAccountManagement.getAccount("01234567890");
        assertNotNull(account);
        assertEquals(111111d, account.getBalance());
        assertEquals("01234567890", account.getAccountNo());
    }

    @Test
    public void testGetTransactionsOccurred()
    {
        bankAccountManagement.openAccount("1234567890", 999999d);
        bankAccountManagement.withdraw("1234567890", 888888d, "Give me 888888$$.");
        bankAccountManagement.deposit("1234567890", 888888d, "Return you 888888$$.");
        BankAccount account = bankAccountManagement.getAccount("01234567890");
        assertNotNull(account);
        assertEquals(1000000d, account.getBalance());
        assertEquals("01234567890", account.getAccountNo());
        List<Transaction> transactions = bankAccountManagement.getTransactionsOccurred("01234567890");
        assertEquals(2, transactions.size());
        assertEquals(-888888d, transactions.get(0).getAmount());
        assertEquals(888888d, transactions.get(1).getAmount());
    }
}
