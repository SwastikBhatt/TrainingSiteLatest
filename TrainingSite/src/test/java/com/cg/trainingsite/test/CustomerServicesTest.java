package com.cg.trainingsite.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.trainingsite.entity.Administrator;
import com.cg.trainingsite.entity.Customer;
import com.cg.trainingsite.exceptions.CustomerException;
import com.cg.trainingsite.repository.CustomerDao;
import com.cg.trainingsite.services.CustomerServicesImpl;

public class CustomerServicesTest {
	@InjectMocks
	CustomerServicesImpl customerServices;
	
	@Mock
	CustomerDao customerDao;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Administrator administrator=new Administrator("swas", "abcd");
		
		
		
		Customer customer1=new Customer(1, "Swastik", "Bhattacharya", 123456778, "swastik@abc.com", "swas", "abcd", 1);
		Customer customer2=new Customer(2, "Sayan", "Bhattacharya", 123456123, "sayan@abc.com", "saya", "abcd", 0);
		Customer customer3=new Customer(3, "Saiyam", "Bhattacharya", 123453231, "saiyam@abc.com", "saiy", "abcd", -1);
		Customer customer4=new Customer(4, "Saptarshi", "Bhattacharya", 123456779, "saptarshi@abc.com", "sapt", "abcd", 1);
		customer1.setContent("1");
		customer2.setContent("");
		customer3.setContent("");
		customer4.setContent("");
		customer3.setLoginAttempts(1);
		customer4.setLoginAttempts(1);
		List<Customer> customers=new ArrayList<Customer>();
		customers.add(customer1);
		customers.add(customer2);
		customers.add(customer3);
		customers.add(customer4);
		when(customerDao.findById(1)).thenReturn(Optional.of(customer1));
		when(customerDao.findById(2)).thenReturn(Optional.of(customer2));
		when(customerDao.findById(3)).thenReturn(Optional.of(customer3));
		when(customerDao.findById(4)).thenReturn(Optional.of(customer4));
		when(customerDao.findAll()).thenReturn(customers);
	}
	
	@Test(expected = CustomerException.class)
	public void testFindOneCustomerUnsuccessfully() {
		customerServices.findOneCustomer(123);
	}
	
	@Test
	public void testFindOneCustomerSuccessfully() {
		Customer customer=customerServices.findOneCustomer(1);
		assertEquals(1, customer.getCustomerNo());
	}
	
	@Test(expected = CustomerException.class)
	public void testCreateCustomerButUsernameAlreadyExists() {
		Customer customerExpected=new Customer(1, "Swastik", "Bhattacharya", 123456778, "swas@abc.com", "swas", "abcd", 1);
		Customer customerActual=customerServices.createCustomer(customerExpected);
		assertEquals(customerExpected, customerActual);
	}
	
	@Test(expected = CustomerException.class)
	public void testCreateCustomerButEmailAlreadyExists() {
		Customer customerExpected=new Customer(1, "Swastik", "Bhattacharya", 123456778, "swastik@abc.com", "swasa", "abcd", 1);
		Customer customerActual=customerServices.createCustomer(customerExpected);
		assertEquals(customerExpected, customerActual);
	}
	
	@Test(expected = CustomerException.class)
	public void testCreateCustomerButMobileAlreadyExists() {
		Customer customerExpected=new Customer(1, "Swastik", "Bhattacharya", 123456778, "swasa@abc.com", "swasas", "abcd", 1);
		Customer customerActual=customerServices.createCustomer(customerExpected);
		assertEquals(customerExpected, customerActual);
	}
	
	@Test
	public void testCreateCustomerSuccessfully() {
		Customer customerExpected=new Customer(4, "Saptarshi", "Bhattacharya", 12345, "saptarshi@gmail.com", "sapt", "1234", 1);
		Customer customerActual=customerServices.createCustomer(customerExpected);
		assertEquals(4,customerExpected.getCustomerNo());
	}
	
	@Test(expected = CustomerException.class)
	public void testLoginCustomersForNotAuthorisedAccount() {
		Customer customer2=new Customer(2, "Sayan", "Bhattacharya", 123456123, "sayan@abc.com", "saya", "abcd", 0);
		customerServices.customerLogin(customer2.getUsername(), customer2.getPassword(), customer2.getTokenVerifier());
	}
	
	@Test(expected = CustomerException.class)
	public void testLoginCustomersForBlockedAccount() {
		Customer customer2=customerServices.findOneCustomer(3);
		customerServices.customerLogin(customer2.getUsername(), customer2.getPassword(), customer2.getTokenVerifier());
	}
	
	@Test
	public void testLoginCustomerForFirsTTime() {
		Customer customer2=customerServices.findOneCustomer(1);
		String s=customerServices.customerLogin(customer2.getUsername(), customer2.getPassword(), customer2.getTokenVerifier());
		assertEquals("Login is successful. Since this is yout first login please remember the OTP "+customer2.getTokenVerifier(), s);
	}
	
	@Test
	public void testLoginCustomerForMultipleTime() {
		Customer customer2=customerServices.findOneCustomer(4);
		String s=customerServices.customerLogin(customer2.getUsername(), customer2.getPassword(), customer2.getTokenVerifier());
		assertEquals("Login is successful", s);
	}
	
	@Test(expected = CustomerException.class)
	public void testLoginCustomerFoWringCredentials() {
		customerServices.customerLogin("hahaha","hahaha",0);
	}
	
	@Test
	public void testSameCourseSubscribed() {
		Customer customer1=new Customer(1, "Swastik", "Bhattacharya", 123456778, "swastik@abc.com", "swas", "abcd", 1);
		List<Customer> customers=new ArrayList<Customer>();
		customers.add(customer1);
		assertEquals(0, customerServices.sameCoursesSubscribed(1).size());
	}
	
	@Test(expected = CustomerException.class)
	public void testGetStatusOfCustomerFOrInvalidCustomer() {
		customerServices.getStatus(123);
	}
	
	@Test
	public void testGetStatusOfCustomerForFirstTime() {
		Customer customer=customerServices.findOneCustomer(1);
		String s=customerServices.getStatus(1);
		assertEquals("Since this is your first time your token is "+customer.getTokenVerifier(), s);
	}
	
	@Test
	public void testGetStatusOfCustomerForNotVerifiedAccount() {
		Customer customer=customerServices.findOneCustomer(2);
		String s=customerServices.getStatus(2);
		assertEquals("Account is yet to be verified by admin so token has not been generated", s);
	}
	
	@Test
	public void testGetStatusOfCustomerForAlreadyShown() {
		Customer customer=customerServices.findOneCustomer(4);
		String s=customerServices.getStatus(4);
		assertEquals("Token has already been shown ", s);
	}
}
