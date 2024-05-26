package com.sa1zer.simplebanking.repo;

import com.sa1zer.simplebanking.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BankAccountRepo extends JpaRepository<BankAccount, Long> {
    @Query("UPDATE BankAccount e SET e.balance = " +
            "CASE " +
            "WHEN e.balance + (e.balance * 0.05) <= e.firstDeposit * 2.07 THEN e.balance + (e.balance * 0.05) " +
            "ELSE e.firstDeposit * 2.07 " +
            "END " +
            "WHERE e.balance + (e.balance * 0.05) <= e.firstDeposit * 2.07")
    @Modifying
    void updateAllBalance();
}
