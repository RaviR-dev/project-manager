package com.cts.fullstack.assignment.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.cts.fullstack.assignment.entities.ParentTask;
import com.cts.fullstack.assignment.repository.ParentTaskRepository;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class ParentTaskServiceTest extends TestCase {

    @Mock
    private ParentTaskRepository mockParentTaskRepository;
    
    private ParentTaskService parentServiceImpl;

    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
        parentServiceImpl = new ParentTaskServiceImpl(mockParentTaskRepository);
    }

    @Test
    public void testGetAllParentTask() throws Exception {
        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");

        ParentTask parentTask2 = new ParentTask();
        parentTask2.setParentTask("Test Parent Task");
        
        List<ParentTask> mockParentTaskList = new ArrayList<>();
        mockParentTaskList.add(parentTask);
        mockParentTaskList.add(parentTask2);
        
        when(mockParentTaskRepository.findAll()).thenReturn(mockParentTaskList);
        
        List<ParentTask> parentTaskList = parentServiceImpl.getAllParentTasks();
        assertThat(mockParentTaskList.size(), equalTo(parentTaskList.size()));
    }

    @Test
    public void testAddParentTask() {
    	ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");
        
        when(mockParentTaskRepository.save(parentTask)).thenReturn(parentTask);
        
        parentServiceImpl.addParentTask(parentTask);
        verify(mockParentTaskRepository).save(parentTask);
    }
}