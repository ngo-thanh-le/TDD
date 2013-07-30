package com.qsoft.bam.dao;

import com.qsoft.bam.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: lent
 * Date: 7/12/13
 */
public interface BankAccountDAO extends JpaRepository<BankAccount, Long>
{
//    public void save(BankAccount account);

    public BankAccount findByAccountNo(String accountNo);

//    public void update(BankAccount updatedAccount);
}
