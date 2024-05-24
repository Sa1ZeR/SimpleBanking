package com.sa1zer.simplebanking.repo;

import com.sa1zer.simplebanking.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepo extends JpaRepository<BankAccount, Long> {
}
