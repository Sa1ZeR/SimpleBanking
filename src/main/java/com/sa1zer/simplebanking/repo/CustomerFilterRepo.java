package com.sa1zer.simplebanking.repo;

import com.sa1zer.simplebanking.entity.Customer;
import com.sa1zer.simplebanking.payload.request.CustomerSearchFilter;

import java.util.List;

public interface CustomerFilterRepo {

    List<Customer> findAllByFilter(CustomerSearchFilter filter);
}
