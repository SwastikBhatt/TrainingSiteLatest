package com.cg.trainingsite.services;

import java.util.List;

import com.cg.trainingsite.entity.Customer;
import com.cg.trainingsite.exceptions.CustomerException;

public interface CustomerServices {
	Customer createCustomer(Customer customer);

	Customer findOneCustomer(int customerId)throws CustomerException;

	String customerLogin(String username, String password, int tokenVerifier)throws CustomerException;

	List<Customer> sameCoursesSubscribed(int courseId);
	
	String getStatus(int customerNo)throws CustomerException;
}
