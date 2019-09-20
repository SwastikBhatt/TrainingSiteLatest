package com.cg.trainingsite.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.trainingsite.entity.Courses;
import com.cg.trainingsite.entity.Customer;
import com.cg.trainingsite.exceptions.CourseException;
import com.cg.trainingsite.exceptions.CustomerException;
import com.cg.trainingsite.repository.CoursesDao;
import com.cg.trainingsite.repository.CustomerDao;

@Component("courseServices")
public class CourseServicesImpl implements CourseServices {
	@Autowired
	CoursesDao courseDao;
	@Autowired
	CustomerDao customerDao;

	@Override
	public Courses findOneCourse(int courseId) throws CourseException{
		return courseDao.findById(courseId).orElseThrow(() -> new CourseException("Invalid course ID"));
	}

	@Override
	public Courses createCourse(Courses course) {
		course = courseDao.save(course);
		return course;
	}

	@Override
	public Courses updateACourse(Courses courseUpdate) throws CourseException{
		List<Courses> allCourse = viewAllCourses();
		int c=0;
		for (Courses courses : allCourse) {
			if (courses.getCourseName().equalsIgnoreCase(courseUpdate.getCourseName())) {
				moveToArchieve(courses.getCourseId());
				c=1;
			}
		}
		if(c==0)
			throw new CourseException("No course by this name exists so we couldnt update the course");
		return createCourse(courseUpdate);
	}

	@Override
	public Courses moveToArchieve(int courseId) throws CourseException{
		Courses course = findOneCourse(courseId);
		course.setValidCourse(0);
		courseDao.save(course);
		return findOneCourse(courseId);
	}

	@Override
	public List<Courses> viewAllCourses() {
		return courseDao.findAll();
	}

	@Override
	public List<Courses> viewArchieve() throws CourseException{
		List<Courses> allCourse = viewAllCourses();
		List<Courses> archieveCourses = new ArrayList<Courses>();
		for (Courses courses : allCourse) {
			if (courses.getValidCourse() == 0)
				archieveCourses.add(courses);
		}
		return archieveCourses;
	}

	@Override
	public void courseSub(int courseId, int customerNo) throws CourseException{
		Courses course = findOneCourse(courseId);
		if (course.getValidCourse() == 0)
			throw new CourseException("Course is Invalid. It has been removed by administrator");
		Customer customer = customerDao.findById(customerNo)
				.orElseThrow(() -> new CustomerException("Invalid customer ID"));
		if (customer.getContent() == null)
			customer.setContent(course.getCourseId() + " ");
		else
			customer.setContent(customer.getContent() + course.getCourseId() + " ");
		customerDao.save(customer);
	}

	@Override
	public List<Courses> viewAllRegisteredCourses(int customerNo) throws CustomerException{
		List<Courses> courses = new ArrayList<Courses>();
		String eachCourse = "";
		Customer customer = customerDao.findById(customerNo)
				.orElseThrow(() -> new CustomerException("Invalid customer ID"));
		String custCourse = customer.getContent();
		for (int i = 0; i < custCourse.length(); i++) {
			if (custCourse.charAt(i) != ' ')
				eachCourse += custCourse.charAt(i);
			else {
				int courseNo = Integer.parseInt(eachCourse);
				System.out.println(courseNo);
				Courses course = findOneCourse(courseNo);
				System.out.println(course);
				courses.add(course);
				eachCourse = "";
			}
		}
		return courses;
	}

	@Override
	public List<Customer> viewAllRegisteredCustomersForAParticularCourse(int courseId) throws CourseException{
		List<Customer> allCustomers = customerDao.findAll();
		Courses courses=courseDao.findById(courseId).orElseThrow(()->new CourseException("No Courses by this name present"));
		List<Customer> registeredForACourse = new ArrayList<Customer>();
		for (Customer customer : allCustomers) {
			if (customer.getContent().indexOf((char) courseId) > 0)
				registeredForACourse.add(customer);
		}
		return registeredForACourse;
	}
}
