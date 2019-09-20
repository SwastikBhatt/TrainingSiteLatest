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

import com.cg.trainingsite.entity.Courses;
import com.cg.trainingsite.entity.Customer;
import com.cg.trainingsite.exceptions.CourseException;
import com.cg.trainingsite.exceptions.CustomerException;
import com.cg.trainingsite.repository.CoursesDao;
import com.cg.trainingsite.repository.CustomerDao;
import com.cg.trainingsite.services.CourseServicesImpl;

public class CourseServicesTest {
	@InjectMocks
	CourseServicesImpl courseServices;

	@Mock
	CoursesDao coursesDao;

	@Mock
	CustomerDao customerDao;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		Courses courses = new Courses();
		courses.setCourseId(1);
		courses.setCourseName("JAVA");
		courses.setPlatformm("Online");
		courses.setPrice(100);
		courses.setValidCourse(1);
		
		List<Courses> courseList=new ArrayList<Courses>();
		courseList.add(courses);
		
		List<Courses> archieveCourseList=new ArrayList<Courses>();
		courses.setCourseId(2);
		courses.setCourseName("Python");
		courses.setPlatformm("Online");
		courses.setPrice(100);
		courses.setValidCourse(0);
		archieveCourseList.add(courses);
		Customer customer=new Customer(1, "Swastik", "Bhattacharya", 12345678, "swastik@abc.com", "swas", "abc", 1);
		customer.setContent("3");
		List<Customer> customerList=new ArrayList<Customer>();
		customerList.add(customer);
		
		when(coursesDao.findById(1)).thenReturn(Optional.of(courses));
		when(coursesDao.findById(3)).thenReturn(Optional.of(new Courses(3, "C++", "Online", 100, 1)));
		when(coursesDao.findAll()).thenReturn(courseList);
		when(courseServices.viewArchieve()).thenReturn(archieveCourseList);
		
		when(customerDao.findById(1)).thenReturn(Optional.of(customer));
		when(customerDao.findAll()).thenReturn(customerList);
	}

	@Test
	public void testcourseIsPresent() {
		Courses course = courseServices.findOneCourse(1);
		String actualName = course.getCourseName();
		assertEquals("Python", actualName);
	}

	@Test(expected = CourseException.class)
	public void testcourseIsNotPresent() {
		Courses course=courseServices.findOneCourse(123);
	}
	
	@Test
	public void testCreateACourse() {
		Courses course=new Courses(1, "JAVA", "Online", 100, 1);
		Courses courseActual=courseServices.createCourse(course);
		assertEquals(1, course.getCourseId());
	}
	
	@Test(expected = CourseException.class)
	public void testUpdateACourseUnsuccessfully() {
		Courses updatedCourse=new Courses(123, "hahaha", "Offline", 200, 1);
		courseServices.updateACourse(updatedCourse);
	}
	
	@Test
	public void testUpdateACourseSuccessfully() {
		Courses updatedCourse=new Courses(3, "C++", "Offline", 200, 1);
		Courses courseActual=courseServices.updateACourse(updatedCourse);
		assertEquals(1, updatedCourse.getCourseId());
	}
	
	@Test(expected = CourseException.class)
	public void testMoveCourseToArchieveUnsuccessfully() {
		courseServices.moveToArchieve(123);
	}
	
	@Test
	public void testMoveCourseToArchieveSuccessfully() {
		Courses course=courseServices.moveToArchieve(1);
		assertEquals(0, course.getValidCourse());
	}
	
	@Test
	public void testViewAllArchieveCourses() {
		List<Courses> courses=new ArrayList<Courses>();
		courses.add(new Courses(2, "Python", "Online", 100, 0));
		assertEquals(courses, courseServices.viewArchieve());
	}
	
	@Test
	public void testViewAllCourses() {
		List<Courses> courses=new ArrayList<Courses>();
		courses.add(new Courses(1, "JAVA", "Online", 100, 1));
		assertEquals(courses.size(), courseServices.viewAllCourses().size());
	}
	
	@Test(expected = CourseException.class)
	public void testCourseSubCourseDoesntExist() {
		courseServices.courseSub(123, 123);
	}
	
	@Test(expected = CustomerException.class)
	public void testCourseSubCustomerDoesntExist() {
		courseServices.courseSub(3, 123);
	}
	
	@Test
	public void testCourseSubSuccessfully() {
		courseServices.courseSub(3, 1);
		assertEquals(3, courseServices.findOneCourse(3).getCourseId());
	}
	
	@Test(expected = CustomerException.class)
	public void testViewRegisteredCoursesWhenCustomerIsInvalid() {
		courseServices.viewAllRegisteredCourses(123);
	}
	
	@Test
	public void testViewRegisteredCoursesSuccessfully() {
		List<Courses> courseList=courseServices.viewAllRegisteredCourses(1);
		assertEquals(0, courseList.size());
	}
	
	@Test(expected = CourseException.class)
	public void testviewRegisteredCustomersWhenCourseIsInvalid() {
		courseServices.viewAllRegisteredCustomersForAParticularCourse(123);
	}
	
	@Test
	public void testViewRegisteredCustomerWhenCourseIsValid() {
		List<Customer> customers=courseServices.viewAllRegisteredCustomersForAParticularCourse(1);
		assertEquals(0, customers.size());
	}
}
