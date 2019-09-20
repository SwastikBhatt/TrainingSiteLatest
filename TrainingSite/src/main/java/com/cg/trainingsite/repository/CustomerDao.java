package com.cg.trainingsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.trainingsite.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer> {

}
