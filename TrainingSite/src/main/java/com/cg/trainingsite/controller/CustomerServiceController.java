package com.cg.trainingsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.trainingsite.entity.Customer;
import com.cg.trainingsite.services.CustomerServices;

@RequestMapping("/customer")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CustomerServiceController {

	@Autowired
	CustomerServices customerServices;

	@PostMapping(value = {
			"/acceptCustomerDetails" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> acceptAssociateDetails(@RequestBody Customer customer) {

		customer = customerServices.createCustomer(customer);
		System.err.println(customer);
		return new ResponseEntity<String>("Customer details has been sent to Admin for verification", HttpStatus.OK);
	}

	@PostMapping(value = {
			"/loginCustomer" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<String> loginCustomer(@RequestParam String username, @RequestParam String password,
			@RequestParam int tokenVerifier) {

		return new ResponseEntity<String>(customerServices.customerLogin(username, password, tokenVerifier),
				HttpStatus.OK);

	}

	@GetMapping(value = {
			"/getSameCoursesCustomersDetails" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<List<Customer>> getRegisteredCoursesDetails(@RequestParam int courseId) {
		return new ResponseEntity<List<Customer>>(customerServices.sameCoursesSubscribed(courseId), HttpStatus.OK);
	}

	@GetMapping(value = {
			"/getCustomerStatus" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<String> getCustomerStatus(@RequestParam int customerNo) {
		return new ResponseEntity<String>(customerServices.getStatus(customerNo), HttpStatus.OK);
	}

}
