package com.cts.fullstack.assignment.dto;

import java.util.Date;

public class ProjectDto {
    private Integer projectId;
    private String project;
    private Date startDate;
    private Date endDate;
    private int priority;
    private Integer managerId;
    private String managerName;
    private Integer managerEmployeeId;
    private int noOfTasks;
    private int noOfCompletedTasks;

	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public Integer getManagerEmployeeId() {
		return managerEmployeeId;
	}
	public void setManagerEmployeeId(Integer managerEmployeeId) {
		this.managerEmployeeId = managerEmployeeId;
	}
	public int getNoOfTasks() {
		return noOfTasks;
	}
	public void setNoOfTasks(int noOfTasks) {
		this.noOfTasks = noOfTasks;
	}
	public int getNoOfCompletedTasks() {
		return noOfCompletedTasks;
	}
	public void setNoOfCompletedTasks(int noOfCompletedTasks) {
		this.noOfCompletedTasks = noOfCompletedTasks;
	}
}
