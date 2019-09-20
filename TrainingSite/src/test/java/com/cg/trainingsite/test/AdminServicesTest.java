package com.cg.trainingsite.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.trainingsite.entity.Administrator;
import com.cg.trainingsite.entity.Customer;
import com.cg.trainingsite.exceptions.AdminExceptions;
import com.cg.trainingsite.exceptions.CustomerException;
import com.cg.trainingsite.repository.CustomerDao;
import com.cg.trainingsite.services.AdminServicesImpl;
import java.util.Optional;

public class AdminServicesTest {

	@InjectMocks
	AdminServicesImpl adminServices;

	@Mock
	CustomerDao customerDao;
	
	@Before
	public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		Administrator administrator=new Administrator("swas", "abcd");
		
		
		
		Customer customer1=new Customer(1, "Swastik", "Bhattacharya", 123456778, "swastik@abc.com", "swas", "abcd", 1);
		Customer customer2=new Customer(2, "Sayan", "Bhattacharya", 123456123, "sayan@abc.com", "saya", "abcd", 0);
		Customer customer3=new Customer(3, "Saiyam", "Bhattacharya", 123453231, "saiyam@abc.com", "saiy", "abcd", -1);
		
		List<Customer> customers=new ArrayList<Customer>();
		customers.add(customer1);
		customers.add(customer2);
		customers.add(customer3);
		when(customerDao.findById(1)).thenReturn(Optional.of(customer1));
		when(customerDao.findById(2)).thenReturn(Optional.of(customer2));
		when(customerDao.findById(3)).thenReturn(Optional.of(customer3));
		when(customerDao.findAll()).thenReturn(customers);
		
	}
	
	@Test(expected = AdminExceptions.class)
	public void testIsAdminAuthenticInvalidCredentials() {
		Administrator administrator1=new Administrator("wrong", "wrong");
		adminServices.isAdminAunthentic(administrator1);
	}
	
	@Test
	public void testIsAdminAuthenticSuccessfully() {
		Administrator administrator1=new Administrator("abcd", "1234");
		assertEquals(true, adminServices.isAdminAunthentic(administrator1));
	}
	
	@Test(expected = CustomerException.class)
	public void testBlockAccountWithInvalidUsername() {
		adminServices.blockAccount("hahahaha");
	}
	
	@Test(expected = CustomerException.class)
	public void testBlockAccountWithAlreadyBlockedAccount() {
		adminServices.blockAccount("saiy");
	}
	
	@Test(expected = CustomerException.class)
	public void testBlockAccountWithNotAddedStatus() {
		adminServices.blockAccount("saya");
	}
	
	@Test
	public void testBlockAccountSuccessfully() {
		String s=adminServices.blockAccount("swas");
		assertEquals("Account has been blocked.", s);
	}
	
	@Test(expected = CustomerException.class)
	public void testPasswordRequestAcceptWithWrongCustomer() {
		adminServices.passwordResetRequestAccept("hahaha", "wrong");
	}
	
	@Test
	public void testPasswordRequestAcceptSuccessfully() {
		String s=adminServices.passwordResetRequestAccept("swas", "abcd");
		assertEquals("Password has been successfully reset", s);
	}
	
	@Test(expected = CustomerException.class)
	public void testUnblockCustomerWithWrongUsername() {
		adminServices.unblockAccount("hahaha");
	}
	
	@Test(expected = CustomerException.class)
	public void testUnblockCustomerWithUnblockedAccount() {
		adminServices.unblockAccount("swas");
	}
	
	@Test(expected = CustomerException.class)
	public void testUnblockCustomerWithNotVerifiedCustomer() {
		adminServices.unblockAccount("saya");
	}
	
	@Test
	public void testUnBlockAccountSuccessfully() {
		String s=adminServices.unblockAccount("saiy");
		assertEquals("Account has been unblocked.", s);
	}
	
	@Test(expected = CustomerException.class)
	public void testRejectCustomerRequestWithWrongUsername() {
		adminServices.rejectCustomerRequest(123);
	}
	
	@Test
	public void testRejectCustomerRequestSuccessfully() {
		assertEquals(null, adminServices.rejectCustomerRequest(1));
	}
	
	@Test(expected = CustomerException.class)
	public void testAcceptCustomerRequestWithWrongUsername() {
		adminServices.acceptCustomerRequest(123);
	}
	
	@Test
	public void testAcceptCustomerRequestSuccessfully() {
		Customer customer=adminServices.acceptCustomerRequest(2);
		assertEquals(0,0);
	}

	@Test
	public void testViewPendingCustomers() {
		Customer customer2=new Customer(2, "Sayan", "Bhattacharya", 123456123, "sayan@abc.com", "saya", "abcd", 0);
		List<Customer> customers=new ArrayList<Customer>();
		customers.add(customer2);
		assertEquals(customers, adminServices.viewAllPendingCustomers());
	}
	
	@Test
	public void testViewValidCustomers() {
		Customer customer1=new Customer(1, "Swastik", "Bhattacharya", 123456778, "swastik@abc.com", "swas", "abcd", 1);
		List<Customer> customers=new ArrayList<Customer>();
		customers.add(customer1);
		assertEquals(customers, adminServices.viewAllValidCustomers());
	}
	
	@Test
	public void testViewBlockedCustomers() {
		Customer customer3=new Customer(3, "Saiyam", "Bhattacharya", 123453231, "saiyam@abc.com", "saiy", "abcd", -1);
		List<Customer> customers=new ArrayList<Customer>();
		customers.add(customer3);
		assertEquals(customers, adminServices.viewAllBlockedCustomers());
	}
	
}
