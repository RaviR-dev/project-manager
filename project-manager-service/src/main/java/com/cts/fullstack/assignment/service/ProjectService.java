package com.cts.fullstack.assignment.service;

import java.util.List;

import com.cts.fullstack.assignment.dto.ProjectDto;
import com.cts.fullstack.assignment.entities.Project;
import com.cts.fullstack.assignment.exception.DataException;

public interface ProjectService {
    List<Project> getAllProjects();
    Project getProjectById(Integer projectId);
    void addProject(Project project) throws DataException;
    void updateProject(Project project) throws DataException;
    void suspendProject(Project project) throws DataException;
	Project getProjectFromProjectDto(ProjectDto projectDto);
	ProjectDto getProjectDtoFromProject(Project project);
}
