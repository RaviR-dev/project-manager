package com.cts.fullstack.assignment.service;

import java.util.List;

import com.cts.fullstack.assignment.dto.TaskDto;
import com.cts.fullstack.assignment.entities.Task;
import com.cts.fullstack.assignment.exception.DataException;

public interface TaskService {
	List<Task> getAllTasks();
    Task getTaskById(Integer taskId);
    void addTask(Task task) throws DataException;
    void updateTask(Task task) throws DataException;
    void endTask(Task task) throws DataException;
    List<Task> getTasksByProjectId(Integer projectId);
	Task getTaskFromTaskDto(TaskDto taskDto);
	TaskDto getTaskDtoFromTask(Task task);
}
