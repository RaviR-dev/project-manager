package com.cts.fullstack.assignment.controller;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.fullstack.assignment.ProjectManagerApplication;
import com.cts.fullstack.assignment.dto.ParentTaskDto;
import com.cts.fullstack.assignment.entities.ParentTask;
import com.cts.fullstack.assignment.repository.ParentTaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ProjectManagerApplication.class)
@ActiveProfiles("test")
public class ParentTaskControllerTest extends TestCase {

    @Value("${local.server.port}")
    private Integer port;
    private String baseUrl;
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ParentTaskRepository parentTaskRepository;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        baseUrl = "http://localhost:".concat(port.toString()).concat("/projectmanager/parenttask");
        testRestTemplate = new TestRestTemplate();
    }

    @Test
    public void testGetAllParentTask() throws Exception {
        ParentTask parentTask = new ParentTask();
        parentTask.setParentTask("Test Parent Task");
        parentTaskRepository.save(parentTask);

        ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl.concat("/list"), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        ObjectMapper mapper = new ObjectMapper();
        List<ParentTaskDto> taskDto = mapper.readValue(response.getBody(), TypeFactory.defaultInstance().constructCollectionType(List.class, ParentTaskDto.class));
        assertThat(taskDto.size(), equalTo(1));
    }

    @Test
    public void testAddParentTask() {
        ParentTaskDto parentTaskDto = new ParentTaskDto();
        parentTaskDto.setParentTask("Test Parent Task");
        ResponseEntity<String> response = testRestTemplate.postForEntity(baseUrl.concat("/add"), parentTaskDto, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        baseUrl = null;
        testRestTemplate = null;
        parentTaskRepository.deleteAll();
    }
}