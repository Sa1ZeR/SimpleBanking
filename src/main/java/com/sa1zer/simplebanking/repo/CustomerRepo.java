package com.sa1zer.simplebanking.repo;

import com.sa1zer.simplebanking.entity.Customer;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long>, CustomerFilterRepo {

    @Query("select c from Customer c left join fetch c.email m where m.email = :email or c.login = :login")
    Optional<Customer> findByLoginOrEmail(@Param("login") String login, @Param("email") String email);

    @Query("select c from Customer c left join fetch c.email m where m.email = :email or c.login = :login")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Customer> findByLoginOrEmailLock(@Param("login") String login, @Param("email") String email);

    @Query("select c from Customer c join fetch c.email m where m.email = :email")
    Optional<Customer> findByEmail(@Param("email") String email);

    @Query("select c from Customer c join fetch c.phones p where p.phone = :phone")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Customer> findByPhone(@Param("phone") String phone);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Customer> findByLogin(String login);
}
