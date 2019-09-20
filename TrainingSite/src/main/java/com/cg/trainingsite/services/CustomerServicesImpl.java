package com.cg.trainingsite.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.trainingsite.entity.Customer;
import com.cg.trainingsite.exceptions.CustomerException;
import com.cg.trainingsite.repository.CustomerDao;

@Component("customerServices")
public class CustomerServicesImpl implements CustomerServices {
	@Autowired
	CustomerDao customerDao;

	@Override
	public Customer createCustomer(Customer customer) {
		List<Customer> allCustomers = customerDao.findAll();
		for (Customer customerObject : allCustomers) {
			if (customer.getEmailId().equals(customerObject.getEmailId()))
				throw new CustomerException("Customer with similar Email ID already exists");
			if (customer.getUsername().equals(customerObject.getUsername()))
				throw new CustomerException("Customer with similar username already exists");
			if (customer.getMobileNo()==customerObject.getMobileNo())
				throw new CustomerException("Customer with similar Mobile Number already exists");
		}
		customer = customerDao.save(customer);
		return customer;
	}

	@Override
	public Customer findOneCustomer(int customerId)throws CustomerException {
		return customerDao.findById(customerId).orElseThrow(() -> new CustomerException("Invalid customer ID"));
	}

	@Override
	public String customerLogin(String username, String password, int tokenVerifier)throws CustomerException {
		List<Customer> allCustomers = customerDao.findAll();
		String s="";
		Customer loginCustomer = null;
		for (Customer customer : allCustomers) {
			if (customer.getUsername().equalsIgnoreCase(username))
				loginCustomer = customer;
		}
		if(loginCustomer==null)
			throw new CustomerException("Credentials are wrong");
		if (loginCustomer.getValidAccount() == 0) {
			throw new CustomerException("Sorry this account is pending authorisation from the admin.");
		}
		if (loginCustomer.getValidAccount() == -1 && loginCustomer.getLoginAttempts() > 0)
			throw new CustomerException("Sorry. This account has been blocked by administrator.");
		if (loginCustomer.getUsername().equalsIgnoreCase(username)) {
			if (loginCustomer.getPassword().contentEquals(password)) {
				if (loginCustomer.getLoginAttempts() == 0) {
					loginCustomer.setLoginAttempts(loginCustomer.getLoginAttempts() + 1);
					customerDao.save(loginCustomer);
					return "Login is successful. Since this is yout first login please remember the OTP "
							+ loginCustomer.getTokenVerifier();

				} else {
					if (loginCustomer.getTokenVerifier() == tokenVerifier) {
						loginCustomer.setLoginAttempts(loginCustomer.getLoginAttempts() + 1);
						customerDao.save(loginCustomer);
						return "Login is successful";

					}
				}
			}
		}
		throw new CustomerException("Any credentials provided is wrong. Please Enter correct credentials");
	}

	@Override
	public List<Customer> sameCoursesSubscribed(int courseId) {
		List<Customer> allCustomers = customerDao.findAll();
		List<Customer> sameCourse = new ArrayList<Customer>();
		for (Customer customer : allCustomers) {
			if (customer.getContent().indexOf((char) courseId)>0)
				sameCourse.add(customer);
		}
		return sameCourse;
	}
	

	@Override
	public String getStatus(int customerNo) throws CustomerException{
		Customer customer=customerDao.findById(customerNo).orElseThrow(() -> new CustomerException("Invalid customer ID"));
		if(customer.getLoginAttempts()==0 && customer.getValidAccount()==1) {
			return "Since this is your first time your token is "+customer.getTokenVerifier();
		}
		else if(customer.getLoginAttempts()==0 && customer.getValidAccount()==0) {
			return "Account is yet to be verified by admin so token has not been generated";
		}
		return "Token has already been shown ";
	}
}
