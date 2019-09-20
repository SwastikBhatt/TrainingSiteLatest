package com.cg.trainingsite.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.trainingsite.entity.Administrator;
import com.cg.trainingsite.entity.Customer;
import com.cg.trainingsite.exceptions.AdminExceptions;
import com.cg.trainingsite.exceptions.CustomerException;
import com.cg.trainingsite.repository.CustomerDao;

@Component("adminServices")
public class AdminServicesImpl implements AdminServices {
	@Autowired
	CustomerDao customerDao;
	

	public AdminServicesImpl(CustomerDao mockCustomerDao) {
		this.customerDao=mockCustomerDao;
	}

	@Override
	public boolean isAdminAunthentic(Administrator administrator) throws AdminExceptions {
		if (administrator.getUserName().equals("abcd") && administrator.getPassword().equals("1234"))
			return true;
		else
			throw new AdminExceptions("Admin has provided wrong credentials or admin is not authentic");
	}

	@Override
	public String blockAccount(String username) throws CustomerException{
		List<Customer> allCustomers = customerDao.findAll();
		Customer loginCustomer = new Customer();
		for (Customer customer : allCustomers) {
			if (customer.getUsername().equalsIgnoreCase(username))
				loginCustomer = customer;
		}
		if(loginCustomer==null)
			throw new CustomerException("Customer doesnt exist for the username");
		if(loginCustomer.getValidAccount()==-1)
			throw new CustomerException("Customer account has already been blocked");
		if(loginCustomer.getValidAccount()==0)
			throw new CustomerException("Customer hasnt been added by administrator");
		loginCustomer.setValidAccount(-1);
		customerDao.save(loginCustomer);
		return "Account has been blocked.";
	}
 
	@Override
	public String passwordResetRequestAccept(String username, String password) throws CustomerException{
		List<Customer> allCustomers = customerDao.findAll();
		Customer loginCustomer = null;
		for (Customer customer : allCustomers) {
			if (customer.getUsername().equalsIgnoreCase(username))
				loginCustomer = customer;
		}
		if(loginCustomer==null)
			throw new CustomerException("Customer doesnt exist for the username");
		loginCustomer.setValidAccount(1);
		loginCustomer.setPassword(password);
		customerDao.save(loginCustomer);
		return "Password has been successfully reset";
	}

	@Override
	public String unblockAccount(String username) throws CustomerException{
		List<Customer> allCustomers = customerDao.findAll();
		Customer loginCustomer = null;
		for (Customer customer : allCustomers) {
			if (customer.getUsername().equalsIgnoreCase(username))
				loginCustomer = customer;
		}
		if(loginCustomer==null)
			throw new CustomerException("Customer doesnt exist for the username");
		if(loginCustomer.getValidAccount()==1)
			throw new CustomerException("Customer is already unblocked");
		if(loginCustomer.getValidAccount()==0)
			throw new CustomerException("Customer requires to be activated by user first");
		loginCustomer.setValidAccount(1);
		customerDao.save(loginCustomer);
		return "Account has been unblocked.";
	}

	@Override
	public Customer rejectCustomerRequest(int customerNo) throws CustomerException{
		Customer customer = customerDao.findById(customerNo)
				.orElseThrow(() -> new CustomerException("Invalid customer ID"));
		customerDao.delete(customer);
		return null;
	}

	@Override
	public Customer acceptCustomerRequest(int customerNo) throws CustomerException{
		Customer customer = customerDao.findById(customerNo)
				.orElseThrow(() -> new CustomerException("Invalid customer ID"));
		customer.setValidAccount(1);
		customer.setTokenVerifier((int) (Math.random() * 1000));
		return customerDao.save(customer);
	}

	@Override
	public List<Customer> viewAllPendingCustomers() throws CustomerException{
		List<Customer> allCustomers = customerDao.findAll();
		List<Customer> pendingCustomer = new ArrayList<Customer>();
		for (Customer customer : allCustomers) {
			if (customer.getValidAccount() == 0)
				pendingCustomer.add(customer);
		}
		return pendingCustomer;
	}

	@Override
	public List<Customer> viewAllValidCustomers() throws CustomerException{
		List<Customer> allCustomers = customerDao.findAll();
		List<Customer> pendingCustomer = new ArrayList<Customer>();
		for (Customer customer : allCustomers) {
			if (customer.getValidAccount() == 1)
				pendingCustomer.add(customer);
		}
		return pendingCustomer;
	}

	@Override
	public List<Customer> viewAllBlockedCustomers() throws CustomerException{
		List<Customer> allCustomers = customerDao.findAll();
		List<Customer> pendingCustomer = new ArrayList<Customer>();
		for (Customer customer : allCustomers) {
			if (customer.getValidAccount() == -1)
				pendingCustomer.add(customer);
		}
		return pendingCustomer;
	}
}
