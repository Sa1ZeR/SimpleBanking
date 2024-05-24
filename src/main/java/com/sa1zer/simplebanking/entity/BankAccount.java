package com.sa1zer.simplebanking.entity;

import com.sa1zer.simplebanking.payload.exception.EntityException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "api_bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal balance;
    @Column(nullable = false)
    private BigDecimal firstDeposit;

    @PreUpdate
    public void onUpdate() {
        validateEntity();
    }

    @PrePersist
    public void onCreate() {
        validateEntity();
    }

    private void validateEntity() {
        if(balance.compareTo(BigDecimal.ONE) < 0 ||
                firstDeposit.compareTo(BigDecimal.ONE) < 0)
            throw new EntityException("Balance can't be less then 0");
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BankAccount that = (BankAccount) object;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
