package com.axiata.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axiata.rest.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
