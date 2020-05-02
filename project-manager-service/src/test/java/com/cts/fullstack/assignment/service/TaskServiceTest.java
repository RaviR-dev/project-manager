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

import com.cts.fullstack.assignment.entities.ParentTask;
import com.cts.fullstack.assignment.entities.Project;
import com.cts.fullstack.assignment.entities.Task;
import com.cts.fullstack.assignment.entities.User;
import com.cts.fullstack.assignment.exception.DataException;
import com.cts.fullstack.assignment.repository.TaskRepository;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest extends TestCase {

    @Mock
    private TaskRepository taskRepository;
    
    private TaskService taskService;

    private Date startDate;
    
    private Date endDate;
    
    private Date invalidEndDate;

    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
    	taskService = new TaskServiceImpl(taskRepository, null, null, null);
        startDate = new Date();
        Calendar endDateCal = Calendar.getInstance();
        endDateCal.add(Calendar.DATE, 2);
        endDate = endDateCal.getTime();
        Calendar invalidEndDateCal = Calendar.getInstance();
        invalidEndDateCal.add(Calendar.DATE, -2);
        invalidEndDate = invalidEndDateCal.getTime();
    }

    @Test
    public void testAddTask() throws DataException {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        user.setUserId(1);

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(user);

        Task task = new Task();
        task.setTask("Task1");
        task.setStatus(true);
        task.setPriority(2);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setParentTask(parentTask);
        task.setProject(project);
        task.setUser(user);

        taskService.addTask(task);
        verify(taskRepository).save(task);
    }
    
    @Test(expected = DataException.class)
    public void testAddTaskInvalidEndDate() throws DataException {
    	User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        user.setUserId(1);

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(user);

        Task task = new Task();
        task.setTask("Task1");
        task.setStatus(true);
        task.setPriority(2);
        task.setStartDate(startDate);
        task.setEndDate(invalidEndDate);
        task.setParentTask(parentTask);
        task.setProject(project);
        task.setUser(user);

        taskService.addTask(task);
    }

    @Test
    public void testUpdateTask() throws DataException {
    	User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        user.setUserId(1);

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(user);

        Task task = new Task();
        task.setTask("Task1Update");
        task.setStatus(true);
        task.setPriority(2);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setParentTask(parentTask);
        task.setProject(project);
        task.setUser(user);

        taskService.updateTask(task);
        verify(taskRepository).save(task);
    }
    
    @Test(expected = DataException.class)
    public void testUpdateTaskInvalidEndDate() throws DataException {
    	User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        user.setUserId(1);

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(user);

        Task task = new Task();
        task.setTask("Task1");
        task.setStatus(true);
        task.setPriority(2);
        task.setStartDate(startDate);
        task.setEndDate(invalidEndDate);
        task.setParentTask(parentTask);
        task.setProject(project);
        task.setUser(user);

        taskService.updateTask(task);
    }

    @Test
    public void testEndTask() throws DataException {
    	User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        user.setUserId(1);

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(user);

        Task task = new Task();
        task.setTask("Task1");
        task.setStatus(true);
        task.setPriority(2);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setParentTask(parentTask);
        task.setProject(project);
        task.setUser(user);

        taskService.endTask(task);
        verify(taskRepository).save(task);
    }
    
    @Test(expected = DataException.class)
    public void testEndTaskInvalidEndDate() throws DataException {
    	User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        user.setUserId(1);

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(user);

        Task task = new Task();
        task.setTask("Task1");
        task.setStatus(true);
        task.setPriority(2);
        task.setStartDate(startDate);
        task.setEndDate(invalidEndDate);
        task.setParentTask(parentTask);
        task.setProject(project);
        task.setUser(user);

        taskService.endTask(task);
    }


    @Test
    public void testGetAllTask() throws Exception {
    	User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        user.setUserId(1);

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setUser(user);

        Task task = new Task();
        task.setTask("Task1");
        task.setStatus(true);
        task.setPriority(2);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setParentTask(parentTask);
        task.setProject(project);
        task.setUser(user);

        List<Task> mockTaskList = new ArrayList<>();
        mockTaskList.add(task);
        
        when(taskRepository.findAll()).thenReturn(mockTaskList);
        List<Task> taskList = taskService.getAllTasks();
        
        assertThat(mockTaskList.size(), equalTo(taskList.size()));
    }

    @Test
    public void testGetTaskByProject() throws Exception {
    	User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        user.setUserId(1);

        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");

        Project project = new Project();
        project.setProject("Test Project");
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(5);
        project.setProjectId(1);
        project.setUser(user);

        Task task = new Task();
        task.setTask("Task1");
        task.setStatus(true);
        task.setPriority(2);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setParentTask(parentTask);
        task.setProject(project);
        task.setUser(user);
        
        
        Project project2 = new Project();
        project2.setProject("Test Project2");
        project2.setStartDate(startDate);
        project2.setEndDate(endDate);
        project2.setPriority(5);
        project2.setProjectId(2);
        project2.setUser(user);
        
        Task task2 = new Task();
        task2.setTask("Task2");
        task2.setStatus(true);
        task2.setPriority(2);
        task2.setStartDate(startDate);
        task2.setEndDate(endDate);
        task2.setParentTask(parentTask);
        task2.setProject(project2);
        task2.setUser(user);

        List<Task> mockTaskList = new ArrayList<>();
        mockTaskList.add(task);
        mockTaskList.add(task2);
        
        when(taskRepository.findAll()).thenReturn(mockTaskList);
        List<Task> taskList = taskService.getTasksByProjectId(1);
        assertThat(1, equalTo(taskList.size()));
    }

}