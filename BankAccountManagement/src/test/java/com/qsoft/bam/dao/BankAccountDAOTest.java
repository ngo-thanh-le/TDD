package com.qsoft.bam.dao;

import com.qsoft.bam.BankAccount;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        bankAccountDAO.save(new BankAccount());
    }
}
