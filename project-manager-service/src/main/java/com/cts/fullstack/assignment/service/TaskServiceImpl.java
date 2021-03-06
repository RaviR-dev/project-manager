package com.cts.fullstack.assignment.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cts.fullstack.assignment.dto.TaskDto;
import com.cts.fullstack.assignment.entities.Task;
import com.cts.fullstack.assignment.exception.DataException;
import com.cts.fullstack.assignment.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
	private final TaskRepository taskRepository;

    private final ParentTaskService parentTaskService;

    private final ProjectService projectService;

    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, ParentTaskService parentTaskService, ProjectService projectService, UserService userService) {
        this.taskRepository = taskRepository;
        this.parentTaskService = parentTaskService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Integer taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.orElse(null);
    }

    @Override
    public void addTask(Task task) throws DataException {
    	validateTaskEndDate(task);
        taskRepository.save(task);
    }

    @Override
    public void updateTask(Task task) throws DataException {
    	validateTaskEndDate(task);
        taskRepository.save(task);
    }

    @Override
    public void endTask(Task task) throws DataException {
    	validateTaskEndDate(task);
        taskRepository.save(task);
    }

    @Override
    public List<Task> getTasksByProjectId(Integer projectId)
    {
        return taskRepository.findAll().stream().filter(task -> task.getProject().getProjectId().equals(projectId)).collect(Collectors.toList());
    }

	private void validateTaskEndDate(Task task) throws DataException {
		if (task.getEndDate() != null && task.getStartDate() != null
    			&& task.getEndDate().before(task.getStartDate())) {
    		throw new DataException("End date cannot be before start date");
    	}
	}
	
	@Override
    public Task getTaskFromTaskDto(TaskDto taskDto) {
        Task task;
        Optional<Task> optionalTask;
        if (taskDto.getTaskId() != null && (optionalTask = taskRepository.findById(taskDto.getTaskId())).isPresent()) {
            task = optionalTask.get();
        } else {
            task = new Task();
        }
        task.setStatus(taskDto.isStatus());
        task.setEndDate(taskDto.getEndDate());
        task.setParentTask(parentTaskService.getParentTaskById(taskDto.getParentId()));
        task.setPriority(taskDto.getPriority());
        task.setStartDate(taskDto.getStartDate());
        task.setTask(taskDto.getTask());
        task.setUser(userService.getUserById(taskDto.getUserId()));
        task.setProject(projectService.getProjectById(taskDto.getProjectId()));
        return task;
    }

    @Override
    public TaskDto getTaskDtoFromTask(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setStatus(task.isStatus());
        taskDto.setEndDate(task.getEndDate());
        taskDto.setPriority(task.getPriority());
        taskDto.setStartDate(task.getStartDate());
        taskDto.setTask(task.getTask());
        taskDto.setTaskId(task.getTaskId());
        if (task.getParentTask() != null) {
            taskDto.setParentId(task.getParentTask().getParentId());
            taskDto.setParentTask(task.getParentTask().getParentTask());
        }
        taskDto.setProjectId(task.getProject().getProjectId());
        taskDto.setProject(task.getProject().getProject());
        taskDto.setUserId(task.getUser().getUserId());
        taskDto.setUserEmployeeId(task.getUser().getEmployeeId());
        taskDto.setUserName(task.getUser().getFirstName().concat(",").concat(task.getUser().getLastName()));
        return taskDto;
    }
}
