package com.cts.fullstack.assignment.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.fullstack.assignment.dto.TaskDto;
import com.cts.fullstack.assignment.entities.Task;
import com.cts.fullstack.assignment.exception.DataException;
import com.cts.fullstack.assignment.service.TaskService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/projectmanager/task")
public class TaskController {
	
	final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping(path="/list")
	public ResponseEntity<Object> getAllTasks(){
		List<Task> taskList = taskService.getAllTasks();
		List<TaskDto> taskDtoList = taskList.stream().map(task -> taskService.getTaskDtoFromTask(task)).collect(Collectors.toList());
		return new ResponseEntity<>(taskDtoList, HttpStatus.OK);
	}
	
	@GetMapping(path="/projecttasks/{projectId}")
	public ResponseEntity<Object> getTaskByProject(@PathVariable("projectId") int projectId){
		List<Task> taskList = taskService.getTasksByProjectId(projectId);
		List<TaskDto> taskDtoList = taskList.stream().map(task -> taskService.getTaskDtoFromTask(task)).collect(Collectors.toList());
		return new ResponseEntity<>(taskDtoList, HttpStatus.OK);
	}
	
	@PostMapping(path="/add")
	public ResponseEntity<Object> addTask(@RequestBody TaskDto taskDto) {
		Task task = taskService.getTaskFromTaskDto(taskDto);
		try {
			taskService.addTask(task);
		} catch (DataException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping(path="/update")
	public ResponseEntity<Object> updateTask(@RequestBody TaskDto taskDto){
		Task task = taskService.getTaskFromTaskDto(taskDto);
		if (task.getTaskId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task not found");
		}
		try {
			taskService.updateTask(task);
		} catch (DataException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(path="/end")
	public ResponseEntity<Object> endTask(@RequestBody TaskDto taskDto){
		Task task = taskService.getTaskFromTaskDto(taskDto);
		if (task.getTaskId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task not found");
		}
		try {
			taskService.endTask(task);
		} catch (DataException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}