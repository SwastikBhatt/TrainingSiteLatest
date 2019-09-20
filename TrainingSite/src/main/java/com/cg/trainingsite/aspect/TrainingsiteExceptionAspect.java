package com.cg.trainingsite.aspect;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.cg.trainingsite.exceptions.AdminExceptions;
import com.cg.trainingsite.exceptions.CourseException;
import com.cg.trainingsite.exceptions.CustomerException;

@ControllerAdvice
public class TrainingsiteExceptionAspect {

	@ExceptionHandler(CourseException.class)
	public ModelAndView handleCourseNotFoundException(Exception e) {
		return new ModelAndView("http://localhost:8090/adminService/acceptCustomerRequests", "errorMessage", e.getMessage());
	}
	
	@ExceptionHandler(AdminExceptions.class)
	public ModelAndView handleAdminNotFoundException(Exception e) {
		return new ModelAndView("http://localhost:8090/adminService/loginAdmin", "errorMessage", e.getMessage());
	}
	
	@ExceptionHandler(CustomerException.class)
	public ModelAndView handleCustomerNotFoundException(Exception e) {
		return new ModelAndView("http://localhost:8090/adminService/acceptCustomerRequests", "errorMessage", e.getMessage());
	}
}
