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
import com.cts.fullstack.assignment.dto.TaskDto;
import com.cts.fullstack.assignment.entities.ParentTask;
import com.cts.fullstack.assignment.entities.Project;
import com.cts.fullstack.assignment.entities.Task;
import com.cts.fullstack.assignment.entities.User;
import com.cts.fullstack.assignment.repository.ParentTaskRepository;
import com.cts.fullstack.assignment.repository.ProjectRepository;
import com.cts.fullstack.assignment.repository.TaskRepository;
import com.cts.fullstack.assignment.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ProjectManagerApplication.class)
@ActiveProfiles("test")
public class TaskControllerTest extends TestCase {

    @Value("${local.server.port}")
    private Integer port;
    private String baseUrl;
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParentTaskRepository parentTaskRepository;

    @Autowired
    private TaskRepository taskRepository;

    private Date startDate;
    
    private Date endDate;
    
    private Date invalidEndDate;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        baseUrl = "http://localhost:".concat(port.toString()).concat("/projectmanager/task");
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
    public void testAddTask() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        User savedUser = userRepository.save(user);

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");
        ParentTask savedParentTask = parentTaskRepository.save(parentTask);

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(savedUser);
        Project savedProject = projectRepository.save(project);

        TaskDto taskDto = new TaskDto();
        taskDto.setTask("Task1");
        taskDto.setStatus(true);
        taskDto.setPriority(2);
        taskDto.setStartDate(startDate);
        taskDto.setEndDate(endDate);
        taskDto.setParentId(savedParentTask.getParentId());
        taskDto.setUserId(savedUser.getUserId());
        taskDto.setProjectId(savedProject.getProjectId());

        ResponseEntity<String> response = testRestTemplate.postForEntity(baseUrl.concat("/add"), taskDto, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }
    
    @Test
    public void testAddTaskInvalidEndDate() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        User savedUser = userRepository.save(user);

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");
        ParentTask savedParentTask = parentTaskRepository.save(parentTask);

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(savedUser);
        Project savedProject = projectRepository.save(project);

        TaskDto taskDto = new TaskDto();
        taskDto.setTask("Task1");
        taskDto.setStatus(true);
        taskDto.setPriority(2);
        taskDto.setStartDate(startDate);
        taskDto.setEndDate(invalidEndDate);
        taskDto.setParentId(savedParentTask.getParentId());
        taskDto.setUserId(savedUser.getUserId());
        taskDto.setProjectId(savedProject.getProjectId());

        ResponseEntity<String> response = testRestTemplate.postForEntity(baseUrl.concat("/add"), taskDto, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testUpdateTask() {
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

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");
        ParentTask savedParentTask = parentTaskRepository.save(parentTask);

        Task task = new Task();
        task.setTask("Task1");
        task.setStatus(true);
        task.setPriority(2);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setParentTask(savedParentTask);
        task.setUser(savedUser);
        task.setProject(savedProject);
        Task savedTask = taskRepository.save(task);

        TaskDto taskDto = new TaskDto();
        taskDto.setTask("Task1");
        taskDto.setTaskId(savedTask.getTaskId());
        taskDto.setStatus(true);
        taskDto.setPriority(8);
        taskDto.setStartDate(startDate);
        taskDto.setEndDate(endDate);
        taskDto.setParentId(savedParentTask.getParentId());
        taskDto.setUserId(savedUser.getUserId());
        taskDto.setProjectId(savedProject.getProjectId());

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/update"), HttpMethod.PUT, new HttpEntity<>(taskDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
    
    @Test
    public void testUpdateTaskNotFound() {
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

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");
        ParentTask savedParentTask = parentTaskRepository.save(parentTask);

        TaskDto taskDto = new TaskDto();
        taskDto.setTask("Task1");
        taskDto.setStatus(true);
        taskDto.setTaskId(888888);
        taskDto.setPriority(8);
        taskDto.setStartDate(startDate);
        taskDto.setEndDate(endDate);
        taskDto.setParentId(savedParentTask.getParentId());
        taskDto.setUserId(savedUser.getUserId());
        taskDto.setProjectId(savedProject.getProjectId());

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/update"), HttpMethod.PUT, new HttpEntity<>(taskDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }
    
    @Test
    public void testUpdateTaskInvalidEndDate() {
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

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");
        ParentTask savedParentTask = parentTaskRepository.save(parentTask);

        Task task = new Task();
        task.setTask("Task1");
        task.setStatus(true);
        task.setPriority(2);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setParentTask(savedParentTask);
        task.setUser(savedUser);
        task.setProject(savedProject);
        Task savedTask = taskRepository.save(task);

        TaskDto taskDto = new TaskDto();
        taskDto.setTask("Task1");
        taskDto.setTaskId(savedTask.getTaskId());
        taskDto.setStatus(true);
        taskDto.setPriority(8);
        taskDto.setStartDate(startDate);
        taskDto.setEndDate(invalidEndDate);
        taskDto.setParentId(savedParentTask.getParentId());
        taskDto.setUserId(savedUser.getUserId());
        taskDto.setProjectId(savedProject.getProjectId());

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/update"), HttpMethod.PUT, new HttpEntity<>(taskDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testEndTask() {
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

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");
        ParentTask savedParentTask = parentTaskRepository.save(parentTask);

        Task task = new Task();
        task.setTask("Task1");
        task.setStatus(true);
        task.setPriority(2);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setParentTask(savedParentTask);
        task.setUser(savedUser);
        task.setProject(savedProject);
        Task savedTask = taskRepository.save(task);

        TaskDto taskDto = new TaskDto();
        taskDto.setTask("Task1");
        taskDto.setTaskId(savedTask.getTaskId());
        taskDto.setStatus(false);
        taskDto.setPriority(8);
        taskDto.setStartDate(startDate);
        taskDto.setEndDate(endDate);
        taskDto.setParentId(savedParentTask.getParentId());
        taskDto.setUserId(savedUser.getUserId());
        taskDto.setProjectId(savedProject.getProjectId());

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/end"), HttpMethod.PUT, new HttpEntity<>(taskDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
    
    @Test
    public void testEndTaskNotFound() {
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

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");
        ParentTask savedParentTask = parentTaskRepository.save(parentTask);

        TaskDto taskDto = new TaskDto();
        taskDto.setTask("Task1");
        taskDto.setTaskId(8888);
        taskDto.setStatus(false);
        taskDto.setPriority(8);
        taskDto.setStartDate(startDate);
        taskDto.setEndDate(endDate);
        taskDto.setParentId(savedParentTask.getParentId());
        taskDto.setUserId(savedUser.getUserId());
        taskDto.setProjectId(savedProject.getProjectId());

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/end"), HttpMethod.PUT, new HttpEntity<>(taskDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }
    
    @Test
    public void testEndTaskInvalidEndDate() {
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

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");
        ParentTask savedParentTask = parentTaskRepository.save(parentTask);

        Task task = new Task();
        task.setTask("Task1");
        task.setStatus(true);
        task.setPriority(2);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setParentTask(savedParentTask);
        task.setUser(savedUser);
        task.setProject(savedProject);
        Task savedTask = taskRepository.save(task);

        TaskDto taskDto = new TaskDto();
        taskDto.setTask("Task1");
        taskDto.setTaskId(savedTask.getTaskId());
        taskDto.setStatus(false);
        taskDto.setPriority(8);
        taskDto.setStartDate(startDate);
        taskDto.setEndDate(invalidEndDate);
        taskDto.setParentId(savedParentTask.getParentId());
        taskDto.setUserId(savedUser.getUserId());
        taskDto.setProjectId(savedProject.getProjectId());

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/end"), HttpMethod.PUT, new HttpEntity<>(taskDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }


    @Test
    public void testGetAllTask() throws Exception {
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

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");
        ParentTask savedParentTask = parentTaskRepository.save(parentTask);

        Task task = new Task();
        task.setTask("Task1");
        task.setStatus(true);
        task.setPriority(2);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setParentTask(savedParentTask);
        task.setUser(savedUser);
        task.setProject(savedProject);
        taskRepository.save(task);

        ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl.concat("/list"), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        List<TaskDto> taskDto = convertJsonToTaskDto(response.getBody());
        assertThat(taskDto.size(), equalTo(1));
    }

    @Test
    public void testGetTaskByProject() throws Exception {
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

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");
        ParentTask savedParentTask = parentTaskRepository.save(parentTask);

        Task task = new Task();
        task.setTask("Task1");
        task.setStatus(true);
        task.setPriority(2);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setParentTask(savedParentTask);
        task.setUser(savedUser);
        task.setProject(savedProject);
        taskRepository.save(task);

        ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl.concat("/").concat(savedProject.getProjectId().toString()), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        List<TaskDto> taskDtoByProject = convertJsonToTaskDto(response.getBody());
        assertThat(taskDtoByProject.size(), equalTo(1));
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        baseUrl = null;
        testRestTemplate = null;
        taskRepository.deleteAll();
        projectRepository.deleteAll();
        parentTaskRepository.deleteAll();
        userRepository.deleteAll();
    }

    private List<TaskDto> convertJsonToTaskDto(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, TaskDto.class));
    }
}