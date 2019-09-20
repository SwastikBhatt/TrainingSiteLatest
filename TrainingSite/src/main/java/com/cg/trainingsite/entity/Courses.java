package com.cg.trainingsite.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Courses {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int courseId;
	private String courseName;
	private String platformm;
	private double price;
	@ApiModelProperty(hidden = true)
	private int validCourse=1;
	private String courseDescription;
	@ManyToOne
	@ApiModelProperty(hidden = true)
	private Customer customer;

	public Courses() {
	}

	public Courses(int courseId, String courseName, String platformm, double price, int validCourse,
			String courseDescription, Customer customer) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.platformm = platformm;
		this.price = price;
		this.validCourse = validCourse;
		this.courseDescription = courseDescription;
		this.customer = customer;
	}

	public Courses(int courseId, String courseName, String platformm, double price, int validCourse,
			Customer customer) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.platformm = platformm;
		this.price = price;
		this.validCourse = validCourse;
		this.customer = customer;
	}

	public Courses(int courseId, String courseName, String platformm, double price, int validCourse) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.platformm = platformm;
		this.price = price;
		this.validCourse = validCourse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courseId;
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + ((platformm == null) ? 0 : platformm.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + validCourse;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Courses other = (Courses) obj;
		if (courseId != other.courseId)
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (platformm == null) {
			if (other.platformm != null)
				return false;
		} else if (!platformm.equals(other.platformm))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (validCourse != other.validCourse)
			return false;
		return true;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getPlatformm() {
		return platformm;
	}

	public void setPlatformm(String platformm) {
		this.platformm = platformm;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getValidCourse() {
		return validCourse;
	}

	public void setValidCourse(int validCourse) {
		this.validCourse = validCourse;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + courseId + ", courseName=" + courseName + ", platformm=" + platformm
				+ ", price=" + price + ", validCourse=" + validCourse + "]";
	}

}
