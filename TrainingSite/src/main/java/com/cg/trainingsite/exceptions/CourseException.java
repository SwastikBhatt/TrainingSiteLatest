package com.cg.trainingsite.exceptions;

public class CourseException extends RuntimeException{

	public CourseException() {
		super();

	}

	public CourseException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);

	}

	public CourseException(String arg0, Throwable arg1) {
		super(arg0, arg1);

	}

	public CourseException(String arg0) {
		super(arg0);
		System.out.println(arg0);
	}

	public CourseException(Throwable arg0) {
		super(arg0);

	}

}
