package com.qsoft.bam.dao;

import com.qsoft.bam.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: lent
 * Date: 7/23/13
 */
public interface TransactionDAO extends JpaRepository<Transaction, Long>
{
//    public void save(Transaction transaction);

    public List<Transaction> findAll();

    List<Transaction> findByAccountNo(String accountNo);
}
