package com.cg.trainingsite.services;

import java.util.List;
import java.util.Map;

import com.cg.trainingsite.entity.Courses;
import com.cg.trainingsite.entity.Customer;
import com.cg.trainingsite.exceptions.CourseException;
import com.cg.trainingsite.exceptions.CustomerException;

public interface CourseServices {
	Courses createCourse(Courses course);

	Courses findOneCourse(int courseId)throws CourseException;

	Courses updateACourse(Courses course)throws CourseException;

	Courses moveToArchieve(int courseId)throws CourseException;

	List<Courses> viewAllCourses()throws CourseException;

	List<Courses> viewArchieve()throws CourseException;

	void courseSub(int courseId, int customerNo)throws CourseException;

	List<Courses> viewAllRegisteredCourses(int customerNo)throws CourseException;

	List<Customer> viewAllRegisteredCustomersForAParticularCourse(int courseId)throws CourseException;
}
