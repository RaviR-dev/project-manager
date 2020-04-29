package com.cts.fullstack.assignment.dto;

public class ParentTaskDto {
    private Integer parentId;
    private String parentTask;
    
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getParentTask() {
		return parentTask;
	}
	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}
}
