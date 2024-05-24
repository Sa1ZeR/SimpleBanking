package com.sa1zer.simplebanking.repo;

import com.sa1zer.simplebanking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c left join c.email m where m.email = :email or c.login = :login")
    Optional<Customer> findByLoginOrEmail(@Param("login") String login, @Param("email") String email);

    @Query("select c from Customer c join c.email m where m.email = :email")
    Optional<Customer> findByEmail(@Param("email") String email);

    @Query("select c from Customer c join c.phones p where p.phone = :phone")
    Optional<Customer> findByPhone(@Param("phone") String phone);

    Optional<Customer> findByLogin(String login);
}
