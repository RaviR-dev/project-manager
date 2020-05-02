package com.cts.fullstack.assignment.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.fullstack.assignment.ProjectManagerApplication;
import com.cts.fullstack.assignment.dto.ProjectDto;
import com.cts.fullstack.assignment.entities.Project;
import com.cts.fullstack.assignment.entities.User;
import com.cts.fullstack.assignment.repository.ProjectRepository;
import com.cts.fullstack.assignment.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ProjectManagerApplication.class)
@ActiveProfiles("test")
public class ProjectControllerTest extends TestCase {

    @Value("${local.server.port}")
    private Integer port;
    private String baseUrl;
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;
    
    private Date startDate;
    
    private Date endDate;
    
    private Date invalidEndDate;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        baseUrl = "http://localhost:".concat(port.toString()).concat("/projectmanager/project");
        testRestTemplate = new TestRestTemplate();
        startDate = new Date();
        Calendar endDateCal = Calendar.getInstance();
        endDateCal.add(Calendar.DATE, 2);
        endDate = endDateCal.getTime();
        Calendar invalidEndDateCal = Calendar.getInstance();
        invalidEndDateCal.add(Calendar.DATE, -2);
        invalidEndDate = invalidEndDateCal.getTime();
    }

    @Test
    public void testAddProject() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);     
        User savedUser = userRepository.save(user);

        ProjectDto projectDto = new ProjectDto();
        projectDto.setProject("Test Project");
        projectDto.setStartDate(startDate);
        projectDto.setEndDate(endDate);
        projectDto.setPriority(5);
        projectDto.setManagerId(savedUser.getUserId());

        ResponseEntity<String> response = testRestTemplate.postForEntity(baseUrl.concat("/add"), projectDto, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }
    
    @Test
    public void testAddProjectInvalidEndDate() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);     
        User savedUser = userRepository.save(user);

        ProjectDto projectDto = new ProjectDto();
        projectDto.setProject("Test Project");
        projectDto.setStartDate(startDate);
        projectDto.setEndDate(invalidEndDate);
        projectDto.setPriority(5);
        projectDto.setManagerId(savedUser.getUserId());

        ResponseEntity<String> response = testRestTemplate.postForEntity(baseUrl.concat("/add"), projectDto, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }


    @Test
    public void testUpdateProject() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        User savedUser = userRepository.save(user);

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(savedUser);
        Project savedProject = projectRepository.save(project);

        ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectId(savedProject.getProjectId());
        projectDto.setProject("Test Project updated");
        projectDto.setStartDate(startDate);
        projectDto.setEndDate(endDate);
        projectDto.setPriority(3);
        projectDto.setManagerId(savedUser.getUserId());

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/update"), HttpMethod.PUT, new HttpEntity<>(projectDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
    
    @Test
    public void testUpdateProjectInvalidEndDate() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        User savedUser = userRepository.save(user);

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(savedUser);
        Project savedProject = projectRepository.save(project);

        ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectId(savedProject.getProjectId());
        projectDto.setProject("Test Project updated");
        projectDto.setStartDate(startDate);
        projectDto.setEndDate(invalidEndDate);
        projectDto.setPriority(3);
        projectDto.setManagerId(savedUser.getUserId());

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/update"), HttpMethod.PUT, new HttpEntity<>(projectDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }
    
    @Test
    public void testUpdateProjectNotFound() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        User savedUser = userRepository.save(user);

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(savedUser);

        ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectId(22222);
        projectDto.setProject("Test Project updated");
        projectDto.setStartDate(startDate);
        projectDto.setEndDate(endDate);
        projectDto.setPriority(3);
        projectDto.setManagerId(savedUser.getUserId());

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/update"), HttpMethod.PUT, new HttpEntity<>(projectDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testSuspendProject() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        User savedUser = userRepository.save(user);

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(savedUser);
        Project savedProject = projectRepository.save(project);

        ProjectDto projectDto = new ProjectDto();
        projectDto.setProject("Test Project");
        projectDto.setStartDate(startDate);
        projectDto.setEndDate(endDate);
        projectDto.setPriority(3);
        projectDto.setManagerId(savedUser.getUserId());
        projectDto.setProjectId(savedProject.getProjectId());

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/suspend"), HttpMethod.PUT, new HttpEntity<>(projectDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
    
    @Test
    public void testSuspendProjectInvalidEndDate() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        User savedUser = userRepository.save(user);

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(savedUser);
        Project savedProject = projectRepository.save(project);

        ProjectDto projectDto = new ProjectDto();
        projectDto.setProject("Test Project");
        projectDto.setStartDate(startDate);
        projectDto.setEndDate(invalidEndDate);
        projectDto.setPriority(3);
        projectDto.setManagerId(savedUser.getUserId());
        projectDto.setProjectId(savedProject.getProjectId());

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/suspend"), HttpMethod.PUT, new HttpEntity<>(projectDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }
    
    @Test
    public void testSuspendProjectNotFound() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        User savedUser = userRepository.save(user);

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(savedUser);

        ProjectDto projectDto = new ProjectDto();
        projectDto.setProject("Test Project");
        projectDto.setStartDate(startDate);
        projectDto.setEndDate(endDate);
        projectDto.setPriority(3);
        projectDto.setManagerId(savedUser.getUserId());
        projectDto.setProjectId(22222);

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/suspend"), HttpMethod.PUT, new HttpEntity<>(projectDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testGetAllProject() throws Exception {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        User savedUser = userRepository.save(user);
        
        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(savedUser);
        projectRepository.save(project);

        ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl.concat("/list"), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        List<ProjectDto> projectDto = convertJsonToProjectDto(response.getBody());
        assertThat(projectDto.size(), equalTo(1));
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        baseUrl = null;
        testRestTemplate = null;
        projectRepository.deleteAll();
        userRepository.deleteAll();
    }

    private List<ProjectDto> convertJsonToProjectDto(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, ProjectDto.class));
    }
}