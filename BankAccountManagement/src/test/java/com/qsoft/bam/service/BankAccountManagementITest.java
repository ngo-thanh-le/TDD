package com.qsoft.bam.service;

import com.qsoft.bam.model.BankAccount;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertNotNull;

/**
 * User: lent
 * Date: 7/30/13
 */
public class BankAccountManagementITest extends AbstractServiceTest
{
    @Autowired
    private BankAccountManagement bankAccountManagement;


    @Test
    public void testRetrieveAnAccount()
    {
        BankAccount account = bankAccountManagement.getAccount("9876543210");
        assertNotNull(account);
    }
}
