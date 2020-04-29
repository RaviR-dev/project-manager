package com.cts.fullstack.assignment.service;

import java.util.List;

import com.cts.fullstack.assignment.dto.ParentTaskDto;
import com.cts.fullstack.assignment.entities.ParentTask;

public interface ParentTaskService {
	List<ParentTask> getAllParentTasks();
    ParentTask getParentTaskById(Integer parentId);
    void addParentTask(ParentTask parentTask);
	ParentTaskDto getParentTaskDtoFromParentTask(ParentTask parentTask);
	ParentTask getParentTaskFromParentTaskDto(ParentTaskDto parentTaskDto);
}
