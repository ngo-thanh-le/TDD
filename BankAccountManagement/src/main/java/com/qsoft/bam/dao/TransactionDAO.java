package com.qsoft.bam.dao;

import com.qsoft.bam.Transaction;

import java.util.List;

/**
 * User: lent
 * Date: 7/23/13
 */
public interface TransactionDAO
{
    public void create(Transaction transaction);

    public List<Transaction> findAll();
}
