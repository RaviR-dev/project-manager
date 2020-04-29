package com.cts.fullstack.assignment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cts.fullstack.assignment.dto.ProjectDto;
import com.cts.fullstack.assignment.entities.Project;
import com.cts.fullstack.assignment.exception.DataException;
import com.cts.fullstack.assignment.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    private final UserService userService;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Integer projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        return project.orElse(null);
    }

    @Override
    public void addProject(Project project) throws DataException {
    	validateProjectEndDate(project);
        projectRepository.save(project);
    }

    @Override
    public void updateProject(Project project) throws DataException {
    	validateProjectEndDate(project);
        projectRepository.save(project);
    }

    @Override
    public void suspendProject(Project project) throws DataException {
    	validateProjectEndDate(project);
    	projectRepository.save(project);
    }

	private void validateProjectEndDate(Project project) throws DataException {
		if (project.getStartDate() != null && project.getEndDate() != null
    			&& project.getEndDate().before(project.getStartDate())) {
    		throw new DataException("Project end date cannot be before start date");
    	}
	}
	
	@Override
    public Project getProjectFromProjectDto(ProjectDto projectDto)
    {
        Project project;
        Optional<Project> optionalProject;
        if(projectDto.getProjectId() != null 
        		&& (optionalProject = projectRepository.findById(projectDto.getProjectId())).isPresent()) {
            project = optionalProject.get();
        } else {
            project = new Project();
        }
        project.setProject(projectDto.getProject());
        project.setStartDate(projectDto.getStartDate());
        project.setEndDate(projectDto.getEndDate());
        project.setPriority(projectDto.getPriority());
        project.setUser(userService.getUserById(projectDto.getManagerId()));
        return project;
    }

    @Override
    public ProjectDto getProjectDtoFromProject(Project project)
    {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectId(project.getProjectId());
        projectDto.setProject(project.getProject());
        projectDto.setStartDate(project.getStartDate());
        projectDto.setEndDate(project.getEndDate());
        projectDto.setPriority(project.getPriority());
        projectDto.setManagerId(project.getUser().getUserId());
        projectDto.setManagerName(project.getUser().getFirstName().concat(",").concat(project.getUser().getLastName()));
        projectDto.setManagerEmployeeId(project.getUser().getEmployeeId());
        projectDto.setNoOfTasks(project.getTasks().size());
        projectDto.setNoOfCompletedTasks((int) project.getTasks().stream().filter(task -> !task.isStatus()).count());
        return projectDto;
    }
}
