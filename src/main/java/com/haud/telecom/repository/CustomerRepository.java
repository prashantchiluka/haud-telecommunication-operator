package com.haud.telecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haud.telecom.entity.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
