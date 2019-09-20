package com.cg.trainingsite.services;

import java.util.List;

import com.cg.trainingsite.entity.Administrator;
import com.cg.trainingsite.entity.Courses;
import com.cg.trainingsite.entity.Customer;
import com.cg.trainingsite.exceptions.AdminExceptions;
import com.cg.trainingsite.exceptions.CustomerException;

public interface AdminServices {
	boolean isAdminAunthentic(Administrator administrator) throws AdminExceptions;

	String blockAccount(String username)throws CustomerException;

	String passwordResetRequestAccept(String username, String password)throws CustomerException;

	String unblockAccount(String username)throws CustomerException;

	Customer rejectCustomerRequest(int customerNo)throws CustomerException;

	Customer acceptCustomerRequest(int customerNo)throws CustomerException;

	List<Customer> viewAllPendingCustomers()throws CustomerException;

	List<Customer> viewAllValidCustomers()throws CustomerException;

	List<Customer> viewAllBlockedCustomers()throws CustomerException;
}
