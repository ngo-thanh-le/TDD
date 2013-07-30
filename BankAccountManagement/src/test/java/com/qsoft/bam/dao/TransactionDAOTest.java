package com.qsoft.bam.dao;

import com.qsoft.bam.model.Transaction;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: lent
 * Date: 7/30/13
 */
public class TransactionDAOTest extends AbstractDAOTest
{
    @Autowired
    private TransactionDAO transactionDAO;

    @Test
    public void testRetrieveExistedTransaction()
    {
        List<Transaction> transactions = transactionDAO.findAll();
        assertTrue(transactions.size() > 0);

        Transaction firstTransaction = transactions.get(0);
        assertEquals("9876543210", firstTransaction.getAccountNo());
        assertEquals(500d, firstTransaction.getAmount(), 0.01d);
        assertEquals("setup data 1", firstTransaction.getDescription());
    }

    @Test
    public void testDeleteTransaction()
    {
        List<Transaction> transactions = transactionDAO.findAll();
        for (Transaction transaction : transactions)
        {
            transactionDAO.delete(transaction);
        }
        transactions = transactionDAO.findAll();
        assertTrue(transactions.size() == 0);
    }

    @Test
    public void testCreateTransaction()
    {
        List<Transaction> transactions = transactionDAO.findByAccountNo("9876543211");
        assertTrue(transactions.size() == 0);
        Transaction newTransaction = new Transaction();
        newTransaction.setAccountNo("9876543211");
        newTransaction.setAmount(1234d);
        newTransaction.setDescription("Unknown reason");
        newTransaction.setTransactionTime(new Date());
        transactionDAO.save(newTransaction);

        transactions = transactionDAO.findByAccountNo("9876543211");
        assertTrue(transactions.size() == 1);
    }
}
