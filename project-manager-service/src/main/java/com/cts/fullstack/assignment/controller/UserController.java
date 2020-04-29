package com.cts.fullstack.assignment.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.fullstack.assignment.dto.UserDto;
import com.cts.fullstack.assignment.entities.User;
import com.cts.fullstack.assignment.service.UserService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/projectmanager/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(path="/list")
	public ResponseEntity<Object> getAllUsers(){
		List<User> userList = userService.getAllUsers();
		List<UserDto> userListDto = userList.stream().map(activeUser -> userService.getUserDtoFromUser(activeUser)).collect(Collectors.toList());
		return new ResponseEntity<>(userListDto, HttpStatus.OK);
	}

	@PostMapping(path="/add")
	public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) {
		User user = userService.getUserFromUserDto(userDto);
		userService.addUser(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping(path="/update")
	public ResponseEntity<Object> updateUser(@RequestBody UserDto userDto){
		User user = userService.getUserFromUserDto(userDto);
		if (user.getUserId() != null && user.getUserId() > 0) {
			userService.updateUser(user);
			return new ResponseEntity<>(HttpStatus.OK);
        } else {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
	}
	
	@PostMapping(path="/delete")
	public ResponseEntity<Object> deleteUser(@RequestBody UserDto userDto){
		User user = userService.getUserFromUserDto(userDto);
		if (user.getUserId() != null && user.getUserId() > 0) {
			userService.deleteUser(user);
			return new ResponseEntity<>(HttpStatus.OK);
        } else {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
	}
}