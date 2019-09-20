package com.cg.trainingsite.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.trainingsite.entity.Courses;
import com.cg.trainingsite.entity.Customer;
import com.cg.trainingsite.services.CourseServices;

@RequestMapping("/course")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CourseServiceController {

	@Autowired
	CourseServices courseServices;

	@PostMapping(value = {
			"/newCourseCreator" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> newCourseCreation(@RequestBody Courses course) {

		course = courseServices.createCourse(course);
		return new ResponseEntity<String>("Course has been created successfully " + course.getCourseName(),
				HttpStatus.OK);
	}

	@PostMapping(value = {
			"/updateCourseCreator" },consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateCourseCreation(@RequestBody Courses course) {

		course = courseServices.updateACourse(course);
		return new ResponseEntity<String>("Course has been updated successfully " + course.getCourseName(),
				HttpStatus.OK);
	}

	@GetMapping(value = {
			"/subscribeForCourse" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> subscribeACourse(@RequestParam int customerNo, @RequestParam int courseId) {

		courseServices.courseSub(courseId, customerNo);
		return new ResponseEntity<String>("Course has been added successfully ", HttpStatus.OK);
	}

	@GetMapping(value = {
			"/getAllCoursesDetails" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<List<Courses>> getCoursesDetails() {
		return new ResponseEntity<List<Courses>>(courseServices.viewAllCourses(), HttpStatus.OK);
	}

	@GetMapping(value = {
			"/getRegisteredCoursesDetails" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<List<Courses>> getRegisteredCoursesDetails(@RequestParam int customerNo) {
		return new ResponseEntity<List<Courses>>(courseServices.viewAllRegisteredCourses(customerNo), HttpStatus.OK);
	}

	@GetMapping(value = {
			"/getCustomerDetailsForACourse" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<List<Customer>> getCustomerDetailsForACourse(@RequestParam int courseId) {
		return new ResponseEntity<List<Customer>>(
				courseServices.viewAllRegisteredCustomersForAParticularCourse(courseId), HttpStatus.OK);
	}

	@GetMapping(value = {
			"/getAllArchieveCoursesDetails" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<List<Courses>> getArchiieveCoursesDetails() {
		return new ResponseEntity<List<Courses>>(courseServices.viewArchieve(), HttpStatus.OK);
	}

	@PostMapping(value = {
			"/moveArchieveCourses" }, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseEntity<Courses> moveToArchiieveCourses(@RequestParam int courseId) {
		return new ResponseEntity<Courses>(courseServices.moveToArchieve(courseId), HttpStatus.OK);
	}

}
