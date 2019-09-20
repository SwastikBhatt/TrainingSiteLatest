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

import com.cg.trainingsite.entity.Administrator;
import com.cg.trainingsite.entity.Customer;
import com.cg.trainingsite.services.AdminServices;

@RequestMapping("/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AdminServiceController {

	@Autowired
	AdminServices adminServices;

	@PostMapping(value = {
			"/acceptCustomerRequests" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<String> acceptCustomerRequests(@RequestParam int customerNo) {
		Customer customer = adminServices.acceptCustomerRequest(customerNo);

		return new ResponseEntity<String>("Customer has been successfully added and verified by Administrator",
				HttpStatus.OK);
	}

	@PostMapping(value = {
			"/rejectCustomerRequests" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<String> rejectCustomerRequests(@RequestParam int customerNo) {
		Customer customer = adminServices.rejectCustomerRequest(customerNo);

		return new ResponseEntity<String>(
				"Customer has been rejected by Administrator.Sorry for the inconvenience caused.", HttpStatus.OK);
	}

	@PostMapping(value = {
			"/blockCustomer" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<String> blockCustomer(@RequestParam String username) {
		return new ResponseEntity<String>(adminServices.blockAccount(username), HttpStatus.OK);
	}

	@PostMapping(value = {
			"/unblockCustomer" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<String> unblockCustomer(@RequestParam String username) {
		return new ResponseEntity<String>(adminServices.unblockAccount(username), HttpStatus.OK);
	}

	@GetMapping(value = {
			"/showPendingCustomersDetails" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<List<Customer>> getPendingCustomerDetails() {
		return new ResponseEntity<List<Customer>>(adminServices.viewAllPendingCustomers(), HttpStatus.OK);
	}

	@GetMapping(value = {
			"/showValidCustomersDetails" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<List<Customer>> getValidCustomerDetails() {
		return new ResponseEntity<List<Customer>>(adminServices.viewAllValidCustomers(), HttpStatus.OK);
	}

	@GetMapping(value = {
			"/showBlockedCustomersDetails" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<List<Customer>> getBlockedCustomerDetails() {
		return new ResponseEntity<List<Customer>>(adminServices.viewAllBlockedCustomers(), HttpStatus.OK);
	}

	@PostMapping(value = {
			"/loginAdmin" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> loginAdmin(@RequestBody Administrator administrator) {

		boolean b = adminServices.isAdminAunthentic(administrator);
		if (b)
			return new ResponseEntity<String>("Welcome " + administrator.getUserName(), HttpStatus.OK);
		else
			return new ResponseEntity<String>(
					"Seems you are not an Administrator or you have entered invalid credentials ", HttpStatus.OK);
	}
}
