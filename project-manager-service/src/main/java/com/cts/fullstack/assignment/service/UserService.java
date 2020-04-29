package com.cts.fullstack.assignment.service;

import java.util.List;

import com.cts.fullstack.assignment.dto.UserDto;
import com.cts.fullstack.assignment.entities.User;

public interface UserService {
	List<User> getAllUsers();
    User getUserById(Integer userId);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
	User getUserFromUserDto(UserDto userDto);
	UserDto getUserDtoFromUser(User user);
}
