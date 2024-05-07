package com.micro.ui.model;

import java.util.List;

public class RegisterBean {

	private Long phoneNumber;
	private String username;
	private String password;
	private String email;
	private String planId;	
	private List<PlanDTO> plansList;

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public List<PlanDTO> getPlansList() {
		return plansList;
	}

	public void setPlansList(List<PlanDTO> plansList) {
		this.plansList = plansList;
	}
	
}
