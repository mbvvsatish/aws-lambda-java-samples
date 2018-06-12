package com.lambda.basic.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

	@JsonProperty
	public int empId;
	@JsonProperty
	public String empName;
	@JsonProperty
	public String department;
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
}
