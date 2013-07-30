package com.qsoft.bam.dao;

import com.qsoft.bam.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: lent
 * Date: 7/12/13
 */
public interface BankAccountDAO extends JpaRepository<BankAccount, Long>
{
    public void create(BankAccount account);

    public BankAccount get(String accountNo);

    public void update(BankAccount updatedAccount);
}
