package com.qsoft.bam;

import com.qsoft.bam.dao.BankAccountDAO;
import com.qsoft.bam.dao.TransactionDAO;
import com.qsoft.bam.service.BankAccountManagement;
import com.qsoft.bam.service.BankAccountManagementImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;

import static junit.framework.Assert.*;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.*;

/**
 * User: lent
 * Date: 7/8/13
 */
public class BankAccountManagementTest
{
    // This is the first phase, big RED, all requirements are converted to test case
    // This way is better than create one by one test failed
    // After the whole set of tests are created, we follow up the process of RED > GREEN > BLUE (Clean)
    // But the approach is a little difference, as we try to green one by one tests.
    @Mock
    private BankAccountDAO mockBankAccountDAO;

    @Mock
    private TransactionDAO mockTransactionDAO;

    @InjectMocks
    BankAccountManagement bankAccountManagement = new BankAccountManagementImpl();

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        // Testing in this focus on the value one - which is BankAccountManagement, verify DAO is called in valid
        // aspects. DAO is mocked by a mock framework with a fixed set of data.

        // Better if we could create a dump implement class, but as requirement, I provide a dynamic mock that truly
        // represent a working context.
        final List<BankAccount> availableAccounts = new ArrayList<BankAccount>();
        final List<Transaction> occurredTransactions = new ArrayList<Transaction>();

        doAnswer(new Answer<Void>()
        {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable
            {
                availableAccounts.add((BankAccount) invocation.getArguments()[0]);
                return null;
            }
        }).when(mockBankAccountDAO).create((BankAccount) anyObject());

        doAnswer(new Answer<BankAccount>()
        {
            @Override
            public BankAccount answer(InvocationOnMock invocation) throws Throwable
            {
                for (BankAccount bankAccount : availableAccounts)
                {
                    if (bankAccount.getAccountNo().equals(invocation.getArguments()[0]))
                    {
                        return bankAccount;
                    }
                }
                return null;
            }
        }).when(mockBankAccountDAO).get(anyString());

        doAnswer(new Answer<Void>()
        {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable
            {
                occurredTransactions.add((Transaction) invocation.getArguments()[0]);
                return null;
            }
        }).when(mockTransactionDAO).create((Transaction) anyObject());

        doAnswer(new Answer<List<Transaction>>()
        {
            @Override
            public List<Transaction> answer(InvocationOnMock invocation) throws Throwable
            {
                return occurredTransactions;
            }
        }).when(mockTransactionDAO).findAll();
    }

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
        BankAccount account = bankAccountManagement.getAccount("1234567890");
        assertNotNull(account);
        assertEquals("1234567890", account.getAccountNo());
        assertEquals(0d, account.getBalance());
    }

    @Test
    public void testDeposit()
    {
        bankAccountManagement.openAccount("1234567890", 0d);
        bankAccountManagement.deposit("1234567890", 1000000d, "Give me a million.");
        BankAccount account = bankAccountManagement.getAccount("1234567890");
        assertNotNull(account);
        assertEquals(1000000d, account.getBalance());
        assertEquals("1234567890", account.getAccountNo());

        // Verify DAO executions
        verify(mockBankAccountDAO, times(2)).get("1234567890");
        verify(mockTransactionDAO, only()).create((Transaction) anyObject());
    }

    @Test
    public void testVerifyTransaction()
    {
        GregorianCalendar testingTime = new GregorianCalendar();
        // Reduce time by 1s
        testingTime.add(Calendar.SECOND, -1);
        Date from = testingTime.getTime();

        bankAccountManagement.openAccount("1234567890", 0d);
        bankAccountManagement.deposit("1234567890", 1000000d, "Give me a million.");
        BankAccount account = bankAccountManagement.getAccount("1234567890");
        assertNotNull(account);
        assertEquals(1000000d, account.getBalance());
        assertEquals("1234567890", account.getAccountNo());

        // Increase time by 1s, ensure the transaction happened
        testingTime.add(Calendar.SECOND, 2);
        Date to = testingTime.getTime();
        List<Transaction> transactions = bankAccountManagement.getTransactionsOccurred("1234567890", from, to);
        assertTrue(transactions.size() == 1);
        assertEquals(transactions.get(0).getAmount(), 1000000d);
        assertEquals(transactions.get(0).getAccountNo(), "1234567890");
    }

    @Test
    public void testWithdraw()
    {
        bankAccountManagement.openAccount("1234567890", 999999d);
        bankAccountManagement.withdraw("1234567890", 888888d, "Give me 888888$.");
        BankAccount account = bankAccountManagement.getAccount("1234567890");
        assertNotNull(account);
        assertEquals(111111d, account.getBalance());
        assertEquals("1234567890", account.getAccountNo());
    }

    @Test
    public void testGetTransactionsOccurred()
    {
        bankAccountManagement.openAccount("1234567890", 999999d);
        bankAccountManagement.withdraw("1234567890", 888888d, "Give me 888888$$.");
        bankAccountManagement.deposit("1234567890", 888888d, "Return you 888888$$.");
        BankAccount account = bankAccountManagement.getAccount("1234567890");
        assertNotNull(account);
        assertEquals(1000000d, account.getBalance());
        assertEquals("1234567890", account.getAccountNo());
        List<Transaction> transactions = bankAccountManagement.getTransactionsOccurred("1234567890");
        assertEquals(2, transactions.size());
        assertEquals(-888888d, transactions.get(0).getAmount());
        assertEquals(888888d, transactions.get(1).getAmount());
    }

    @Test
    public void testGetTransactionsOccurredFromThisToThat()
    {
        bankAccountManagement.openAccount("1234567890", 999999d);
        Date timeBegin = new Date();
        bankAccountManagement.withdraw("1234567890", 888888d, "Give me 888888$$.");
        bankAccountManagement.deposit("1234567890", 888888d, "Return you 888888$$.");
        Date timeEnd = new Date();
        bankAccountManagement.withdraw("1234567890", 111111d, "Overtime");
        BankAccount account = bankAccountManagement.getAccount("1234567890");
        assertNotNull(account);
        assertEquals(1000000d, account.getBalance());
        assertEquals("1234567890", account.getAccountNo());
        List<Transaction> transactions = bankAccountManagement.getTransactionsOccurred("1234567890", timeBegin, timeEnd);
        assertEquals(2, transactions.size());
        assertEquals(-888888d, transactions.get(0).getAmount());
        assertEquals(888888d, transactions.get(1).getAmount());
    }

    @After
    public void tearDown()
    {
        reset(mockBankAccountDAO, mockTransactionDAO);
    }
}
