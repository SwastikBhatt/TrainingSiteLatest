package com.cg.trainingsite.entity;

import java.util.ArrayList;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int customerNo;
	private String firstName;
	private String lastName;
	private long mobileNo;
	private String emailId;
	private String username;
	private String password;
	@ApiModelProperty(hidden = true)
	private int loginAttempts;
	@ApiModelProperty(hidden = true)
	private int tokenVerifier;
	@ApiModelProperty(hidden = true)
	private int validAccount;
	@ApiModelProperty(hidden = true)
	private ArrayList<Integer> subscriptions;
	@OneToMany(mappedBy = "customer")
	@ApiModelProperty(hidden = true)
	private Map<Long, Courses> courses;
	@ApiModelProperty(hidden = true)
	private String content;
	

	public Customer() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courses == null) ? 0 : courses.hashCode());
		result = prime * result + customerNo;
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + loginAttempts;
		result = prime * result + (int) (mobileNo ^ (mobileNo >>> 32));
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + tokenVerifier;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + validAccount;
		return result;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (courses == null) {
			if (other.courses != null)
				return false;
		} else if (!courses.equals(other.courses))
			return false;
		if (customerNo != other.customerNo)
			return false;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (loginAttempts != other.loginAttempts)
			return false;
		if (mobileNo != other.mobileNo)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (tokenVerifier != other.tokenVerifier)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (validAccount != other.validAccount)
			return false;
		return true;
	}

	public Customer(int customerNo, String firstName, String lastName, long mobileNo, String emailId, String username,
			String password, int loginAttempts, int tokenVerifier, int validAccount, ArrayList<Integer> subscriptions,
			Map<Long, Courses> courses) {
		super();
		this.customerNo = customerNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNo = mobileNo;
		this.emailId = emailId;
		this.username = username;
		this.password = password;
		this.loginAttempts = loginAttempts;
		this.tokenVerifier = tokenVerifier;
		this.validAccount = validAccount;
		this.subscriptions = subscriptions;
		this.courses = courses;
	}

	public Customer(String firstName, String lastName, long mobileNo, String emailId, String username, String password,
			int validAccount) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNo = mobileNo;
		this.emailId = emailId;
		this.username = username;
		this.password = password;
		this.validAccount = validAccount;
	}

	public Customer(int customerNo, String firstName, String lastName, long mobileNo, String emailId, String username,
			String password, int validAccount) {
		super();
		this.customerNo = customerNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNo = mobileNo;
		this.emailId = emailId;
		this.username = username;
		this.password = password;
		this.validAccount = validAccount;
	}

	public int getTokenVerifier() {
		return tokenVerifier;
	}

	public void setTokenVerifier(int tokenVerifier) {
		this.tokenVerifier = tokenVerifier;
	}

	public int getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public int getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(int customerNo) {
		this.customerNo = customerNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getValidAccount() {
		return validAccount;
	}

	public void setValidAccount(int validAccount) {
		this.validAccount = validAccount;
	}

	public ArrayList<Integer> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(ArrayList<Integer> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Map<Long, Courses> getCourses() {
		return courses;
	}

	public void setCourses(Map<Long, Courses> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Customer [customerNo=" + customerNo + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", mobileNo=" + mobileNo + ", emailId=" + emailId + ", username=" + username + ", password="
				+ password + ", validAccount=" + validAccount + "]";
	}

}
