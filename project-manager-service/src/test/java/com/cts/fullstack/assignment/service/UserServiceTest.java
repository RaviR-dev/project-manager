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

import com.cts.fullstack.assignment.entities.User;
import com.cts.fullstack.assignment.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	private UserService userService;
	
	@Mock
	private UserRepository mockUserRepository;

	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(mockUserRepository);
    }

	@Test
    public void testAddUser() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        user.setUserId(1);

        when(mockUserRepository.save(user)).thenReturn(user);
        userService.addUser(user);
        verify(mockUserRepository).save(user);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        user.setUserId(1);

        when(mockUserRepository.save(user)).thenReturn(user);
        userService.updateUser(user);
        verify(mockUserRepository).save(user);
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        user.setUserId(1);
        userService.deleteUser(user);
        verify(mockUserRepository).delete(user);
    }

    @Test
    public void testGetAllUser() throws Exception {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmployeeId(12345);
        user.setUserId(1);

        User user2 = new User();
        user2.setLastName("Smith");
        user2.setFirstName("John");
        user2.setEmployeeId(12346);
        user2.setUserId(2);

        List<User> mockUserList = new ArrayList<>();
        mockUserList.add(user);
        mockUserList.add(user2);
        
        when(mockUserRepository.findAll()).thenReturn(mockUserList);
        
        List<User> userList = userService.getAllUsers();
        
        assertThat(mockUserList.size(), equalTo(userList.size()));
    }

}
