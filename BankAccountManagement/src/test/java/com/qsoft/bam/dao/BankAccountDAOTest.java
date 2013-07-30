package com.qsoft.bam.dao;

import com.qsoft.bam.model.BankAccount;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * User: lent
 * Date: 7/24/13
 */
public class BankAccountDAOTest extends AbstractDAOTest
{
    @Autowired
    private BankAccountDAO bankAccountDAO;

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
}
