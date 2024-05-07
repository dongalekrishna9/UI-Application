package com.micro.ui.model;

import java.io.Serializable;

public class PlanDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private  String  planId;
	private  String  planName;
	private  Integer  validity;
	private  String  description;
	
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
