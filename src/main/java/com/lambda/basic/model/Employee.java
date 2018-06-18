package com.lambda.basic.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

	@JsonProperty
	public int empId;
	@JsonProperty
	public String empName;
	@JsonProperty
	public String department;
	@JsonProperty
	public String designation;
	@JsonProperty
	public double minsalary;
	@JsonProperty
	public double maxsalary;
	
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
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public double getMinsalary() {
		return minsalary;
	}
	public void setMinsalary(double minsalary) {
		this.minsalary = minsalary;
	}
	public double getMaxsalary() {
		return maxsalary;
	}
	public void setMaxsalary(double maxsalary) {
		this.maxsalary = maxsalary;
	}
	
}
