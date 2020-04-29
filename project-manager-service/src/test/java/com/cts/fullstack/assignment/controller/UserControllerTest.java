package com.cts.fullstack.assignment.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
import com.cts.fullstack.assignment.dto.UserDto;
import com.cts.fullstack.assignment.entities.User;
import com.cts.fullstack.assignment.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ProjectManagerApplication.class)
@ActiveProfiles("test")
public class UserControllerTest extends TestCase{

    @Value("${local.server.port}")
    private Integer port;
    private String baseUrl;
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        baseUrl = "http://localhost:".concat(port.toString()).concat("/projectmanager/user");
        testRestTemplate = new TestRestTemplate();
    }

    @Test
    public void testAddUser() {
        UserDto userDto = new UserDto();
        userDto.setLastName("Doe");
        userDto.setFirstName("John");
        userDto.setEmployeeId(12345);
        ResponseEntity<String> response = testRestTemplate.postForEntity(baseUrl.concat("/add"), userDto, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        User resultUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setUserId(resultUser.getUserId());
        userDto.setLastName("Smith");
        userDto.setFirstName("John");
        userDto.setEmployeeId(12345);

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/update"), HttpMethod.PUT, new HttpEntity<>(userDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
    
    @Test
    public void testUpdateUserNotFound() {
        UserDto userDto = new UserDto();
        userDto.setUserId(11111);
        userDto.setLastName("Smith");
        userDto.setFirstName("John");
        userDto.setEmployeeId(12345);

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/update"), HttpMethod.PUT, new HttpEntity<>(userDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        User resultUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setUserId(resultUser.getUserId());
        userDto.setLastName("Doe");
        userDto.setFirstName("John");
        userDto.setEmployeeId(12345);

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/delete"), HttpMethod.POST, new HttpEntity<>(userDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
    
    @Test
    public void testDeleteUserNotFound() {
        UserDto userDto = new UserDto();
        userDto.setUserId(111111);
        userDto.setLastName("Doe");
        userDto.setFirstName("John");
        userDto.setEmployeeId(12345);

        ResponseEntity<String> response = testRestTemplate.exchange(baseUrl.concat("/delete"), HttpMethod.POST, new HttpEntity<>(userDto), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testGetAllUser() throws Exception {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        userRepository.save(user);

        User user2 = new User();
        user2.setLastName("Smith");
        user2.setFirstName("John");
        user2.setEmployeeId(12346);
        userRepository.save(user2);

        ResponseEntity<String> response = testRestTemplate.getForEntity(baseUrl.concat("/list"), String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        List<UserDto> taskDto = convertJsonToUserDto(response.getBody());
        assertThat(taskDto.size(), equalTo(2));
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        baseUrl = null;
        testRestTemplate = null;
        userRepository.deleteAll();
    }

    private List<UserDto> convertJsonToUserDto(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, UserDto.class));
    }
}