package com.qsoft.bam.dao;

import com.qsoft.bam.model.BankAccount;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: lent
 * Date: 7/24/13
 */
public class BankAccountDAOTest extends AbstractDAOTest
{
    @Autowired
    private BankAccountDAO bankAccountDAO;

    @Test
    public void testGetBankAccount()
    {
        BankAccount existedAccount = bankAccountDAO.findByAccountNo("9876543210");

        assertNotNull(existedAccount);
        assertEquals("9876543210", existedAccount.getAccountNo());
        assertEquals(1000d, existedAccount.getBalance(), 0.01d);
        assertEquals("2013-01-01 18:30:30.0", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(existedAccount.getOpenTimestamp()));
    }

    @Test
    public void testSaveBankAccount()
    {
        BankAccount account = new BankAccount();
        account.setAccountNo("0123456789");
        account.setBalance(1000d);
        account.setOpenTimestamp(new Date());
        bankAccountDAO.save(account);

        BankAccount accountRetrievedBack = bankAccountDAO.findByAccountNo("0123456789");
        assertEquals(account.getAccountNo(), accountRetrievedBack.getAccountNo());
        assertEquals(account.getBalance(), accountRetrievedBack.getBalance(), 0.01d);
        assertEquals(account.getOpenTimestamp(), accountRetrievedBack.getOpenTimestamp());
    }

    @Test
    public void testDeleteBankAccount()
    {
        BankAccount account = new BankAccount();
        account.setAccountNo("0123456789");
        account.setBalance(1000d);
        account.setOpenTimestamp(new Date());
        bankAccountDAO.save(account);

        BankAccount accountRetrievedBack = bankAccountDAO.findByAccountNo("0123456789");
        assertEquals(account.getAccountNo(), accountRetrievedBack.getAccountNo());
        assertEquals(account.getBalance(), accountRetrievedBack.getBalance(), 0.01d);
        assertEquals(account.getOpenTimestamp(), accountRetrievedBack.getOpenTimestamp());

        bankAccountDAO.delete(accountRetrievedBack);
        accountRetrievedBack = bankAccountDAO.findByAccountNo("0123456789");
        assertNull(accountRetrievedBack);
    }
}
