package com.cts.fullstack.assignment.controller;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.fullstack.assignment.dto.ProjectDto;
import com.cts.fullstack.assignment.entities.Project;
import com.cts.fullstack.assignment.exception.DataException;
import com.cts.fullstack.assignment.service.ProjectService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/projectmanager/project")
public class ProjectController {

	private final ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping(path = "/list")
	public ResponseEntity<Object> getAllProjects() {
		return new ResponseEntity<>(projectService.getAllProjects().stream().map(project -> projectService.getProjectDtoFromProject(project)).collect(Collectors.toList()), HttpStatus.OK);
	}

	@PostMapping(path = "/add")
	public ResponseEntity<Object> addProject(@RequestBody ProjectDto projectDto) {
		Project project = projectService.getProjectFromProjectDto(projectDto);
		try {
			projectService.addProject(project);
		} catch (DataException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping(path = "/update")
	public ResponseEntity<Object> updateProject(@RequestBody ProjectDto projectDto) {
		Project project = projectService.getProjectFromProjectDto(projectDto);
		if (project.getProjectId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Project not found");
		}
		try {
			projectService.updateProject(project);
		} catch (DataException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(path = "/suspend")
	public ResponseEntity<Object> suspendProject(@RequestBody ProjectDto projectDto) {
		Project project = projectService.getProjectFromProjectDto(projectDto);
		if (project.getProjectId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Project not found");
		}
		try {
			projectService.suspendProject(project);
		} catch (DataException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}