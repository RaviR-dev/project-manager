package com.cts.fullstack.assignment.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.cts.fullstack.assignment.entities.Project;
import com.cts.fullstack.assignment.entities.User;
import com.cts.fullstack.assignment.exception.DataException;
import com.cts.fullstack.assignment.repository.ProjectRepository;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest extends TestCase {

    @Mock
    private ProjectRepository mockProjectRepository;
    
    private ProjectService projectService;
    
    private Date startDate;
    
    private Date endDate;
    
    private Date invalidEndDate;

    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
    	projectService = new ProjectServiceImpl(mockProjectRepository, null);
        startDate = new Date();
        Calendar endDateCal = Calendar.getInstance();
        endDateCal.add(Calendar.DATE, 2);
        endDate = endDateCal.getTime();
        Calendar invalidEndDateCal = Calendar.getInstance();
        invalidEndDateCal.add(Calendar.DATE, -2);
        invalidEndDate = invalidEndDateCal.getTime();
    }

    @Test
    public void testAddProject() throws DataException {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);     
        user.setUserId(1);

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(user);
        
        projectService.addProject(project);
        verify(mockProjectRepository).save(project);
    }
    
    @Test(expected = DataException.class)
    public void testAddProjectInvalidEndDate() throws DataException {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);     
        user.setUserId(1);

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(invalidEndDate);
        project.setPriority(5);
        project.setUser(user);

        projectService.addProject(project);
    }


    @Test
    public void testUpdateProject() throws DataException {
    	User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);     
        user.setUserId(1);

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(8);
        project.setUser(user);
        
        projectService.updateProject(project);
        verify(mockProjectRepository).save(project);
    }
    
    @Test(expected = DataException.class)
    public void testUpdateProjectInvalidEndDate() throws DataException {
    	User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);     
        user.setUserId(1);

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(invalidEndDate);
        project.setPriority(5);
        project.setUser(user);
        
        projectService.updateProject(project);
    }

    @Test
    public void testSuspendProject() throws DataException {
    	User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);     
        user.setUserId(1);

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(user);
        
        projectService.suspendProject(project);
        verify(mockProjectRepository).save(project);
    }
    
    @Test(expected = DataException.class)
    public void testSuspendProjectInvalidEndDate() throws DataException {
    	User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);     
        user.setUserId(1);

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(invalidEndDate);
        project.setPriority(5);
        project.setUser(user);
        
        projectService.suspendProject(project);
        verify(mockProjectRepository).save(project);
    }
    
    @Test
    public void testGetAllProject() throws Exception {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        user.setUserId(1);
        
        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(user);
        
        List<Project> mockProjectList = new ArrayList<>();
        mockProjectList.add(project);
        
        when(mockProjectRepository.findAll()).thenReturn(mockProjectList);
        
        List<Project> projectList = projectService.getAllProjects();
        assertThat(mockProjectList.size(), equalTo(projectList.size()));
    }

}