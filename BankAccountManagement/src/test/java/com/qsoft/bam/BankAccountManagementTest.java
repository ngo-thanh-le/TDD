package com.qsoft.bam;

import com.qsoft.bam.dao.BankAccountDAO;
import com.qsoft.bam.dao.TransactionDAO;
import com.qsoft.bam.model.BankAccount;
import com.qsoft.bam.model.Transaction;
import com.qsoft.bam.service.BankAccountManagement;
import com.qsoft.bam.service.BankAccountManagementImpl;
import com.qsoft.bam.utils.DateUtils;
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
    private BankAccountDAO mockBankAccountDAO = mock(BankAccountDAO.class);

    @Mock
    private TransactionDAO mockTransactionDAO = mock(TransactionDAO.class);

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
                boolean isUpdate = false;
                for (BankAccount bankAccount : availableAccounts)
                {
                    BankAccount updatedBankAccountParam = (BankAccount) invocation.getArguments()[0];
                    if (bankAccount.getAccountNo()
                            .equals(updatedBankAccountParam.getAccountNo()))
                    {
                        bankAccount.setBalance(updatedBankAccountParam.getBalance());
                    }
                    isUpdate = true;
                }
                if (!isUpdate)
                {
                    availableAccounts.add((BankAccount) invocation.getArguments()[0]);
                }
                return null;
            }
        }).when(mockBankAccountDAO).save((BankAccount) anyObject());

//        doAnswer(new Answer<Void>()
//        {
//            @Override
//            public Void answer(InvocationOnMock invocation) throws Throwable
//            {
//                for (BankAccount bankAccount : availableAccounts)
//                {
//                    BankAccount updatedBankAccountParam = (BankAccount) invocation.getArguments()[0];
//                    if (bankAccount.getAccountNo()
//                            .equals(updatedBankAccountParam.getAccountNo()))
//                    {
//                        bankAccount.setBalance(updatedBankAccountParam.getBalance());
//                    }
//                }
//                return null;
//            }
//        }).when(mockBankAccountDAO).save((BankAccount) anyObject());

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
        }).when(mockBankAccountDAO).findByAccountNo(anyString());

        doAnswer(new Answer<Void>()
        {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable
            {
                occurredTransactions.add((Transaction) invocation.getArguments()[0]);
                return null;
            }
        }).when(mockTransactionDAO).save((Transaction) anyObject());

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
        verify(mockBankAccountDAO, times(2)).findByAccountNo("1234567890");
        verify(mockTransactionDAO, times(1)).save((Transaction) anyObject());
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

        // Verify DAO executions
        verify(mockBankAccountDAO, times(2)).findByAccountNo("1234567890");
        verify(mockTransactionDAO, times(1)).save((Transaction) anyObject());
        verify(mockBankAccountDAO, times(1)).save((BankAccount) anyObject());
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

        // Verify DAO executions
        verify(mockBankAccountDAO, times(2)).findByAccountNo("1234567890");
        verify(mockTransactionDAO, times(1)).save((Transaction) anyObject());
        verify(mockBankAccountDAO, times(1)).save((BankAccount) anyObject());
    }

    @Test
    public void testGetTransactionsOccurred()
    {
        bankAccountManagement.openAccount("1234567890", 999999d);
        bankAccountManagement.withdraw("1234567890", 888888d, "Give me 888888$$.");
        bankAccountManagement.deposit("1234567890", 888888d, "Return you 888888$$.");
        BankAccount account = bankAccountManagement.getAccount("1234567890");
        assertNotNull(account);
        assertEquals(999999d, account.getBalance());
        assertEquals("1234567890", account.getAccountNo());
        List<Transaction> transactions = bankAccountManagement.getTransactionsOccurred("1234567890");
        assertEquals(2, transactions.size());
        assertEquals(-888888d, transactions.get(0).getAmount());
        assertEquals(888888d, transactions.get(1).getAmount());

        // Verify DAO executions
        verify(mockBankAccountDAO, times(3)).findByAccountNo("1234567890");
        verify(mockTransactionDAO, times(2)).save((Transaction) anyObject());
        verify(mockBankAccountDAO, times(2)).save((BankAccount) anyObject());
    }

    @Test
    public void testGetTransactionsOccurredFromThisToThat() throws InterruptedException
    {
        GregorianCalendar testingTime = new GregorianCalendar();
        // Reduce time by 1s
        testingTime.add(Calendar.SECOND, -1);
        Date timeBegin = testingTime.getTime();

        bankAccountManagement.openAccount("1234567890", 999999d);
        bankAccountManagement.withdraw("1234567890", 888888d, "Give me 888888$$.");
        bankAccountManagement.deposit("1234567890", 888888d, "Return you 888888$$.");

        Date timeEnd = new Date();
        Thread.sleep(100);

        bankAccountManagement.withdraw("1234567890", 111111d, "Overtime");
        BankAccount account = bankAccountManagement.getAccount("1234567890");
        assertNotNull(account);
        assertEquals(888888d, account.getBalance());
        assertEquals("1234567890", account.getAccountNo());
        List<Transaction> transactions = bankAccountManagement.getTransactionsOccurred("1234567890", timeBegin, timeEnd);
        assertEquals(2, transactions.size());
        assertEquals(-888888d, transactions.get(0).getAmount());
        assertEquals(888888d, transactions.get(1).getAmount());

        // Verify DAO executions
        verify(mockBankAccountDAO, times(4)).findByAccountNo("1234567890");
        verify(mockTransactionDAO, times(3)).save((Transaction) anyObject());
        verify(mockBankAccountDAO, times(3)).save((BankAccount) anyObject());
    }

    @Test
    public void testGetRecentTransactions() throws InterruptedException
    {
        bankAccountManagement.openAccount("1234567890", 999999d);
        bankAccountManagement.withdraw("1234567890", 888888d, "Give me 888888$$.");
        bankAccountManagement.deposit("1234567890", 888888d, "Return you 888888$$.");
        bankAccountManagement.withdraw("1234567890", 111111d, "Overtime Spending");
        bankAccountManagement.withdraw("1234567890", 111111d, "Overtime Spending");
        bankAccountManagement.withdraw("1234567890", 111111d, "Overtime Spending");
        bankAccountManagement.withdraw("1234567890", 111111d, "Overtime Spending");

        List<Transaction> transactions = bankAccountManagement.getRecentTransactions("1234567890", 5);
        assertEquals(5, transactions.size());

        // Verify DAO executions
        verify(mockBankAccountDAO, times(6)).findByAccountNo("1234567890");
        verify(mockTransactionDAO, times(6)).save((Transaction) anyObject());
        verify(mockBankAccountDAO, times(6)).save((BankAccount) anyObject());
    }

    @Test
    public void testOpenAccountStoredMomentCreatedIt() throws InterruptedException
    {
        Date beforeOpen = new Date();
        bankAccountManagement.openAccount("1234567890", 999999d);
        Date afterOpen = new Date();

        BankAccount account = bankAccountManagement.getAccount("1234567890");

        assertNotNull(account.getOpenTimestamp());
        assertTrue(DateUtils.isBeforeOrEquals(beforeOpen, account.getOpenTimestamp()));
        assertTrue(DateUtils.isAfterOrEquals(afterOpen, account.getOpenTimestamp()));

        // Verify DAO executions
        verify(mockBankAccountDAO, times(1)).findByAccountNo("1234567890");
        verify(mockBankAccountDAO, times(0)).save((BankAccount) anyObject());
    }

    @After
    public void tearDown()
    {
        reset(mockBankAccountDAO, mockTransactionDAO);
    }
}
