package com.sa1zer.simplebanking.repo;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.sa1zer.simplebanking.entity.Customer;
import com.sa1zer.simplebanking.entity.QCustomer;
import com.sa1zer.simplebanking.payload.request.CustomerSearchFilter;
import com.sa1zer.simplebanking.utils.QPredicates;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sa1zer.simplebanking.entity.QCustomer.customer;

@RequiredArgsConstructor
public class CustomerFilterRepoImpl implements CustomerFilterRepo {

    private final EntityManager entityManager;

    @Override
    public List<Customer> findAllByFilter(CustomerSearchFilter filter) {
        Predicate predicate = QPredicates.builder()
                .add(filter.birthday(), customer.birthday::after)
                .add(filter.name(), customer.name::startsWith)
                .build();

        JPAQuery<Customer> query = new JPAQuery<>(entityManager)
                .select(customer)
                .from(customer);

        if(StringUtils.hasText(filter.phone()))
            query.join(customer.phones)
                    .where(customer.phones.any().phone.eq(filter.phone()));

        if(StringUtils.hasText(filter.email()))
            query.join(customer.email)
                    .where(customer.email.any().email.eq(filter.email()));

        int size = filter.size() == null ? 25 : filter.size();
        if(size > 100) size = 100;

        int page = filter.page() == null ? 0 : filter.page();

        return query.where(predicate)
                .limit(size)
                .offset(Math.max(0, page))
                .fetch();
    }
}
